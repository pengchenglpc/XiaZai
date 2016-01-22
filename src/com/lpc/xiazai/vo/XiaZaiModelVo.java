package com.lpc.xiazai.vo;

import java.io.File;

public class XiaZaiModelVo {
	private String fileName;
	private String size;
	private String schedule;
	private String speed;
	private String residueTime;
	private File cfgFile;
	private Thread currentThread;
	private int status = 1;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getResidueTime() {
		return residueTime;
	}
	public void setResidueTime(String residueTime) {
		this.residueTime = residueTime;
	}
	public File getCfgFile() {
		return cfgFile;
	}
	public void setCfgFile(File cfgFile) {
		this.cfgFile = cfgFile;
	}
	public Thread getCurrentThread() {
		return currentThread;
	}
	public void setCurrentThread(Thread currentThread) {
		this.currentThread = currentThread;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
