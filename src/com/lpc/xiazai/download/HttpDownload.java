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
		URLConnection conn = url.openConnection();
		File tmp = createTargetTmpFile();
		this.addData();
		long startByte = this.startDownloadByte();
		conn.setRequestProperty("User-Agent","NetFox"); 
		conn.setRequestProperty("RANGE", "bytes=" + startByte+"-");
		
		InputStream input = conn.getInputStream();
		//System.out.println(conn.getContentType());
		this.downloadFile(input, tmp, startByte, conn.getContentLengthLong());
	}
	
	
}
