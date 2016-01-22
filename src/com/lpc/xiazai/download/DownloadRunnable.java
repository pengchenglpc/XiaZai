package com.lpc.xiazai.download;

import java.io.IOException;

public class DownloadRunnable extends Thread {
	private Download download = null;
	public DownloadRunnable(Download download){
		this.download = download;
	}
	@Override
	public void run() {
		try {
			this.download.download();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
