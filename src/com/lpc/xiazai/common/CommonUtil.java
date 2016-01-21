package com.lpc.xiazai.common;

import java.text.DecimalFormat;

public class CommonUtil {
	private static final long dayMillis = 86400l;
	private static final long hourMillis = 3600l;
	private static final long minuteMillis = 60l;
	public static String timeFormat(long second){
		if(second >= dayMillis){
			return "大于1天";
		}
		if(second >= hourMillis){
			String str = (second / hourMillis) + "";
			return str + "小时" + CommonUtil.timeFormat(second % hourMillis);
		}
		if(second >= minuteMillis){
			String str = (second / minuteMillis) + "";
			return str + "分" + CommonUtil.timeFormat(second % minuteMillis);
		}
		return second + "秒";
	}
	
	private static final long GB_SPACE = 1073741824l;
	private static final long TB_SPACE = 1099511627776l;
	private static final long MB_SPACE = 1048576l;
	private static final long KB_SPACE = 1024l;
	public static String spaceFormat(long space){
		DecimalFormat df = new DecimalFormat("#.##");
		if(space >= TB_SPACE){
			return df.format(((space * 1.0d) / TB_SPACE)) + "T";
		}
		if(space >= GB_SPACE){
			return df.format(((space * 1.0d) / GB_SPACE)) + "G";
		}
		if(space >= MB_SPACE){
			return df.format(((space * 1.0d) / MB_SPACE)) + "M";
		}
		if(space >= KB_SPACE){
			return df.format(((space * 1.0d) / KB_SPACE)) + "K";
		}
		return space + "B";
	}
}
