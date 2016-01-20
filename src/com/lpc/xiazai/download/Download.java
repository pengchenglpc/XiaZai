package com.lpc.xiazai.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Download implements Runnable {
	//需要下载的资源URL
	private URL url = null;
	//文件下载存储路径
	private File target = null;
	public synchronized void  set(URL url, String target){
		this.url = url;
		this.target = new File(target);
		if(!this.target.exists()){
			this.target.mkdirs();
		}
	}
	@Override
	public void run() {
		try {
			this.download();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void download() throws IOException{
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
	private File createTargetTmpFile() throws IOException{
		File file = new File(target, new File(url.getFile()).getName() + ".tmp");
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
		//url.get
	}

}
