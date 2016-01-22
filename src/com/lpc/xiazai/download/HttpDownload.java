package com.lpc.xiazai.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URLConnection;

public class HttpDownload extends Download {
	
	@Override
	public void download() throws Exception {
		URLConnection conn = url.openConnection();
		File tmp = createTargetTmpFile();
		this.addData();
		long startByte = this.startDownloadByte();
		conn.setRequestProperty("RANGE", "bytes=" + startByte);
		
		InputStream input = conn.getInputStream();
		//System.out.println(conn.getContentType());
		this.startTimer(tmp, conn.getContentLengthLong());
		RandomAccessFile raf = new RandomAccessFile(tmp, "rwd");
		raf.seek(startByte);
		
		BufferedInputStream bufferInput = new BufferedInputStream(input);
//		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmp));
		byte[] bytes = new byte[4096];
		int result = -1;
		while((result = bufferInput.read(bytes)) != -1){
			if(this.isInterrupted(tmp)){
				return;
			}
			raf.write(bytes, 0, result);
		}
		raf.close();
		bufferInput.close();
		this.stopTimer();
		tmp.renameTo(new File(target, new File(url.getFile()).getName()));
	}
	
	
}
