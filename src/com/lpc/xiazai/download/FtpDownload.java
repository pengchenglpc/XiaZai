package com.lpc.xiazai.download;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpDownload extends Download {
	private final String userName = "hadoop";
	private final String password = "hadoop";
	private FTPClient client = null;
	public FtpDownload(String id) {
		super(id);
	}
	protected void interrupte() throws Exception{
		if(client != null){
			client.disconnect();
		}
	}
	@Override
	public void download() throws Exception {
		String host = url.getHost();
		int port = url.getPort();
		String remoteFile = url.getFile();
		client = new FTPClient();
		client.connect(host, port);
		boolean isLogin = client.login(userName, password);
		client.enterLocalPassiveMode();
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		System.out.println(isLogin);
		FTPFile[] files = client.listFiles(remoteFile);
		long remoteSize = files[0].getSize();
		File tmp = createTargetTmpFile();
		this.addData();
		long startByte = this.startDownloadByte();
		client.setRestartOffset(startByte);
		System.out.println("isConnected-->" + client.isConnected());
		InputStream input = client.retrieveFileStream(remoteFile);
		this.downloadFile(input, tmp, startByte, remoteSize);
		client.disconnect();
//		FtpClient fc = FtpClient.create();
//		fc = fc.connect(new InetSocketAddress(host, port));
//		fc = fc.login(userName, password.toCharArray());
////		this.addData();
////		InputStream in = fc.getFileStream(url.getFile());
//		//fc = fc.siteCmd("size " + url.getFile());
//		System.out.println(fc.getSize(url.getFile()));
////		fc.setRestartOffset(this.startDownloadByte());
////		fc.get
	}

}
