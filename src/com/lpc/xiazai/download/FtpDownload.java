package com.lpc.xiazai.download;

import sun.net.ftp.FtpClient;

public class FtpDownload extends Download {
	private final String userName = "anonymous";
	private final String password = "XiaZai";
	public FtpDownload(String id) {
		super(id);
	}

	@Override
	public void download() throws Exception {
		System.out.println(url.getProtocol() + "->" + url.getHost() + "->" + url.getPort() + "->" + url.getFile());
	}

}
