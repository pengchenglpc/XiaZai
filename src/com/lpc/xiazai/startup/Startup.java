package com.lpc.xiazai.startup;

import java.net.MalformedURLException;

import com.lpc.xiazai.ui.MainFrame;

public class Startup {

	public static void main(String[] args) throws MalformedURLException {
//		DownloadRunnable down = new DownloadRunnable();
//		down.set(new URL("http://download.java.net/jdk/jdk-api-localizations/jdk-api-zh-cn/publish/1.6.0/chm/JDK_API_1_6_zh_CN.CHM"), "F:\\work\\logs");
//		Long time = System.currentTimeMillis();
//		down.run();
//		System.out.println(System.currentTimeMillis() - time);
//		System.out.println(new File("").getAbsolutePath());
		new MainFrame("下载吧");
	}

}
