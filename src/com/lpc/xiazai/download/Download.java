package com.lpc.xiazai.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;

public abstract class Download {
	//需要下载的资源URL
	protected URL url = null;
		//文件下载存储路径
	protected File target = null;
	protected Timer timer = null;
	protected TimerTask task = null;
	protected JTable table = null;
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
	protected void startTimer(){
		timer = new Timer();
		timer.scheduleAtFixedRate(task, new Date(), 1000);
	}
	protected void stopTimer(){
		timer.cancel();
	}
}
