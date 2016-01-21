package com.lpc.xiazai.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class HttpDownload extends Download {
	
	@Override
	public void download() throws IOException {
		URLConnection conn = url.openConnection();
		
		InputStream input = conn.getInputStream();
		//System.out.println(conn.getContentType());
		File tmp = createTargetTmpFile();
		BufferedInputStream bufferInput = new BufferedInputStream(input);
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmp));
		byte[] bytes = new byte[4096];
		int result = -1;
		while((result = bufferInput.read(bytes)) != -1){
			out.write(bytes, 0, result);
		}
		out.flush();
		out.close();
		bufferInput.close();
		tmp.renameTo(new File(target, new File(url.getFile()).getName()));
		
	}
	private void startTimer(){
		
	}
	
}
