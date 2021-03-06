package com.lpc.xiazai.timer;

import java.io.File;
import java.text.DecimalFormat;
import java.util.TimerTask;

import javax.swing.JTable;

import com.lpc.xiazai.common.CommonUtil;
import com.lpc.xiazai.ui.XiaZaiTableModel;

public class XiaZaiTimerTask extends TimerTask {
	private JTable table;
	private File file;
	private long lastSize = 0l;
	private long totalSize = 0l;
	private int rowIndex = 0;
	public XiaZaiTimerTask(JTable table, File file, int rowIndex, long totalSize){
		this.table = table;
		this.file = file;
		lastSize = file.length();
		this.totalSize = totalSize;
		this.rowIndex = rowIndex;
	}
	@Override
	public void run() {
		XiaZaiTableModel model = (XiaZaiTableModel)this.table.getModel();
		long currentSize = file.length();
		long speed = currentSize - lastSize;
		double schedule = ((currentSize * 1.0d) / totalSize);
		DecimalFormat df = new DecimalFormat("#.##%");
		String scheduleStr = df.format(schedule);
		String speedStr = CommonUtil.spaceFormat(speed) + "/s";
		lastSize = currentSize;
		synchronized(model){
			model.setValueAt(CommonUtil.spaceFormat(totalSize), rowIndex, 1);
			model.setValueAt(scheduleStr, rowIndex, 2);
			model.setValueAt(speedStr, rowIndex, 3);
			if(speed > 0)
				model.setValueAt(CommonUtil.timeFormat(((totalSize - currentSize) / speed)), rowIndex, 4);
			else
				model.setValueAt("超过1天", rowIndex, 4);
		}
		
	}

}
