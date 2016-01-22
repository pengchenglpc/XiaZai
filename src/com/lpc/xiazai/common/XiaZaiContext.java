package com.lpc.xiazai.common;

import java.util.HashMap;
import java.util.Map;

public class XiaZaiContext {
	private Map<String, Object> contextMap = new HashMap<String, Object>();
	private static XiaZaiContext context = null;
	private  XiaZaiContext(){};
	public static synchronized XiaZaiContext getContext(){
		if(context == null){
			context = new XiaZaiContext();
		}
		return context;
	}
	public void setProperty(String key, Object value){
		synchronized(contextMap){
			contextMap.put(key, value);
		}
	}
	
	public Object getProperty(String key){
		return contextMap.get(key);
	}
}
