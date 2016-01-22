package com.lpc.xiazai.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.lpc.xiazai.vo.XiaZaiModelVo;

public class XiaZaiTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -455083001822805300L;
	private List<XiaZaiModelVo> dataList = new ArrayList<XiaZaiModelVo>();
	private String[] columnName = {"文件名", "文件大小", "下载进度", "下载速度", "剩余时间"};
	public XiaZaiTableModel(){
		super();
	}
	public XiaZaiTableModel(List<XiaZaiModelVo> datas){
		this.dataList = datas;
	}
	@Override
	public int getRowCount() {
		return dataList.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		XiaZaiModelVo model = dataList.get(rowIndex);
		if(columnIndex == 0){
			return model.getFileName();
		}else if(columnIndex == 1){
			return model.getSize();
		}else if(columnIndex == 2){
			return model.getSchedule();
		}else if(columnIndex == 3){
			return model.getSpeed();
		}else if(columnIndex == 4){
			return model.getResidueTime();
		}
		return null;
	}
	
	public void add(XiaZaiModelVo model){
		this.dataList.add(model);
		this.fireTableDataChanged();
	}
	public String getColumnName(int column){
		return columnName[column];
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//		super.setValueAt(aValue, rowIndex, columnIndex);
		XiaZaiModelVo model = dataList.get(rowIndex);
		if(columnIndex == 0){
			model.setFileName(aValue.toString());
		}else if(columnIndex == 1){
			model.setSize(aValue.toString());
		}else if(columnIndex == 2){
			model.setSchedule(aValue.toString());
		}else if(columnIndex == 3){
			model.setSpeed(aValue.toString());
		}else if(columnIndex == 4){
			model.setResidueTime(aValue.toString());
		}
		this.fireTableDataChanged();
	}
	public XiaZaiModelVo getAt(int rowIndex){
		return this.dataList.get(rowIndex);
	}
}
