package com.lpc.xiazai.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;

import com.lpc.xiazai.common.CommonUtil;
import com.lpc.xiazai.timer.XiaZaiTimerTask;
import com.lpc.xiazai.ui.XiaZaiTableModel;
import com.lpc.xiazai.vo.XiaZaiModelVo;

public abstract class Download {
	//需要下载的资源URL
	protected URL url = null;
		//文件下载存储路径
	protected File target = null;
	private Timer timer = null;
	private JTable table = null;
	private int rowIndex = 0;
	public synchronized void  set(URL url, String target, JTable table){
		this.url = url;
		this.target = new File(target);
		this.table = table;
		if(!this.target.exists()){
			this.target.mkdirs();
		}
	}
	public abstract void download() throws IOException;
	
	protected File createTargetTmpFile() throws IOException{
		File file = new File(target, new File(url.getFile()).getName() + ".tmp");
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
		//url.get
	}
	protected void startTimer(File tmp, long totalSize){
		XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
		synchronized(model){
			XiaZaiModelVo modelVo = new XiaZaiModelVo();
			modelVo.setFileName(new File(url.getFile()).getName());
			modelVo.setSize(CommonUtil.spaceFormat(totalSize));
			modelVo.setSchedule("0%");
			modelVo.setSpeed("初始化中");
			modelVo.setResidueTime("--");
			model.add(modelVo);
			rowIndex = model.getRowCount();
		}
		TimerTask task = new XiaZaiTimerTask(this.table, tmp, rowIndex - 1, totalSize);
		timer = new Timer();
		timer.scheduleAtFixedRate(task, new Date(), 1000);
	}
	protected void stopTimer(){
		timer.cancel();
		XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
		synchronized(model){
			model.setValueAt("100%", rowIndex - 1, 2);
			model.setValueAt("--", rowIndex - 1, 3);
			model.setValueAt("--", rowIndex - 1, 4);
		}
	}
}
