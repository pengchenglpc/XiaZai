package com.lpc.xiazai.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URLConnection;

public class HttpDownload extends Download {
	
	public HttpDownload(String id) {
		super(id);
	}

	@Override
	public void download() throws Exception {
                //测试一下
		URLConnection conn = url.openConnection();
		File tmp = createTargetTmpFile();
		this.addData();
		long startByte = this.startDownloadByte();
		conn.setRequestProperty("User-Agent","NetFox"); 
		conn.setRequestProperty("RANGE", "bytes=" + startByte+"-");
		
		InputStream input = conn.getInputStream();
		//System.out.println(conn.getContentType());
		this.startTimer(tmp, conn.getContentLengthLong());
		RandomAccessFile raf = new RandomAccessFile(tmp, "rw");
		raf.seek(startByte);
		
		BufferedInputStream bufferInput = new BufferedInputStream(input);
//		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmp));
		byte[] bytes = new byte[4096];
		int result = -1;
		while((result = bufferInput.read(bytes)) != -1){
			if(this.isInterrupted(tmp)){
				raf.close();
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
