package com.lpc.xiazai.download;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;

import com.lpc.xiazai.common.XiaZaiContext;
import com.lpc.xiazai.timer.XiaZaiTimerTask;
import com.lpc.xiazai.ui.XiaZaiTableModel;
import com.lpc.xiazai.vo.XiaZaiContextVo;
import com.lpc.xiazai.vo.XiaZaiModelVo;

public abstract class Download {
	//需要下载的资源URL
	protected URL url = null;
		//文件下载存储路径
	protected File target = null;
	private Timer timer = null;
	private JTable table = null;
	private int rowIndex = 0;
	protected String id = null;
	protected Download(String id){
		this.id = id;
	}
	public synchronized void  set(URL url, String target, JTable table){
		this.url = url;
		this.target = new File(target);
		this.table = table;
		if(!this.target.exists()){
			this.target.mkdirs();
		}
	}
	public abstract void download() throws Exception;
	
	protected File createTargetTmpFile() throws IOException{
		File file = new File(target, new File(url.getFile()).getName() + ".tmp");
		if(!file.exists()){
			file.createNewFile();
		}
		XiaZaiContext ctx = XiaZaiContext.getContext();
		synchronized(ctx){
			XiaZaiContextVo contextVo = (XiaZaiContextVo)ctx.getProperty(id);
			contextVo.setTmpPath(file.getParent());
		}
		return file;
		//url.get
	}
	protected void startTimer(File tmp, long totalSize){
		
		TimerTask task = new XiaZaiTimerTask(this.table, tmp, rowIndex, totalSize);
		timer = new Timer();
		timer.scheduleAtFixedRate(task, new Date(), 1000);
	}
	public void addData(){
		XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
		synchronized(model){
			rowIndex = model.getIndexById(id);
			if(rowIndex == -1){
				XiaZaiModelVo modelVo = new XiaZaiModelVo();
				modelVo.setId(id);
				modelVo.setFileName(new File(url.getFile()).getName());
	//			modelVo.setSize(CommonUtil.spaceFormat(totalSize));
				modelVo.setSchedule("0%");
				modelVo.setSpeed("初始化中");
				modelVo.setResidueTime("--");
				model.add(modelVo);
				rowIndex = model.getRowCount() - 1;
			}
		}
	}
	protected void finishTimer(){
		timer.cancel();
		XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
		synchronized(model){
			model.setValueAt("100%", rowIndex , 2);
			model.setValueAt("完成", rowIndex, 3);
			model.setValueAt("完成", rowIndex, 4);
			model.getAt(rowIndex).setStatus(3);
		}
	}
	protected long startDownloadByte() throws NumberFormatException, IOException{
		XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
		XiaZaiModelVo modelVo = model.getAt(rowIndex);
		modelVo.setCurrentThread(Thread.currentThread());
		if(modelVo.getCfgFile() != null){
			File cfgFile = modelVo.getCfgFile();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cfgFile)));
			long size = Long.valueOf(reader.readLine());
			reader.close();
			return size;
		}
		return 0l;
	}
	protected void interrupte() throws Exception{}
	protected boolean isInterrupted(File tmp) throws IOException{
		if(Thread.currentThread().isInterrupted()){
			File file = new File(target, new File(url.getFile()).getName() + ".cfg");
			if(!file.exists()){
				file.createNewFile();
			}
			XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
			model.getAt(rowIndex).setStatus(0);
			model.getAt(rowIndex).setCfgFile(file);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			out.write(tmp.length() + "");
			timer.cancel();
			synchronized(model){
				//model.setValueAt("100%", rowIndex , 2);
				model.setValueAt("暂停", rowIndex, 3);
				model.setValueAt("暂停", rowIndex, 4);
			}
			out.close();
			return true;
		}
		return false;
	}
	protected void removeCfgFile(){
		XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
		synchronized(model){
			model.getAt(rowIndex).getCfgFile().delete();
		}
	}
	
	protected void downloadFile(InputStream input, File tmp, long startByte, long totalSize) throws Exception{
		this.startTimer(tmp, totalSize);
		RandomAccessFile raf = new RandomAccessFile(tmp, "rw");
		raf.seek(startByte);
		
		BufferedInputStream bufferInput = new BufferedInputStream(input);
//		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmp));
		byte[] bytes = new byte[4096];
		int result = -1;
		while((result = bufferInput.read(bytes)) != -1){
			if(this.isInterrupted(tmp)){
				raf.close();
				bufferInput.close();
				this.interrupte();
				return;
			}
			raf.write(bytes, 0, result);
		}
		raf.close();
		bufferInput.close();
		this.finishTimer();
		tmp.renameTo(new File(tmp.getParent(), tmp.getName().replace(".tmp", "")));
		this.removeCfgFile();
	}
}
