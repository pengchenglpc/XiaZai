package com.lpc.xiazai.download;

import org.apache.commons.net.ftp.FTPClient;

public class FtpDownload extends Download {
	private final String userName = "anonymous";
	private final String password = "XiaZai";
	public FtpDownload(String id) {
		super(id);
	}

	@Override
	public void download() throws Exception {
		String host = url.getHost();
		int port = url.getPort();
		FTPClient client = new FTPClient();
		client.connect(host, port);
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
