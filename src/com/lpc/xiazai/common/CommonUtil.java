package com.lpc.xiazai.common;

public class CommonUtil {
	private static final long dayMillis = 86400l;
	private static final long hourMillis = 3600l;
	private static final long minuteMillis = 60l;
	public static String timeFormat(long second){
		if(second > dayMillis){
			return "大于1天";
		}
		if(second > hourMillis){
			String str = (second / hourMillis) + "";
			return str + "小时" + CommonUtil.timeFormat(second % hourMillis);
		}
		if(second > minuteMillis){
			String str = (second / minuteMillis) + "";
			return str + "分" + CommonUtil.timeFormat(second % minuteMillis);
		}
		return second + "秒";
	}
}
