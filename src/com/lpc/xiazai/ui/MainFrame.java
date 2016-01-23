package com.lpc.xiazai.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.lpc.xiazai.common.XiaZaiContext;
import com.lpc.xiazai.download.Download;
import com.lpc.xiazai.download.DownloadRunnable;
import com.lpc.xiazai.vo.XiaZaiContextVo;
import com.lpc.xiazai.vo.XiaZaiModelVo;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6243701565608778119L;
	private JTable table = null;
	public MainFrame(String title){
		super(title);
		this.setResizable(true);
		
		this.setSize(700, 400);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		JScrollPane scrollPanel = new JScrollPane(getTable());
		panel.add(scrollPanel);
		this.setContentPane(panel);
		this.setJMenuBar(jmenuBar());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				XiaZaiTableModel model = (XiaZaiTableModel) table.getModel();
				int rowCount = model.getRowCount();
				boolean flag = false;
				for(int i = 0; i < rowCount; i++){
					XiaZaiModelVo modelVo = model.getAt(i);
					if(modelVo.getStatus() == 1){
						flag = true;
						break;
					}
				}
				if(flag){
					int option = JOptionPane.showConfirmDialog(MainFrame.this, "您还有正在下载中的任务，确定要退出吗？", "警告", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(option == 0){
						System.exit(0);
					}
				}else{
					System.exit(0);
				}
			}
		});
		this.setVisible(true);
	}
	public JTable getTable(){
		if(table == null){
			table = new JTable(new XiaZaiTableModel());
			table.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						int rowIndex = table.getSelectedRow();
						System.out.println(rowIndex);
						XiaZaiTableModel model = (XiaZaiTableModel) table.getModel();
						XiaZaiModelVo modelVo = model.getAt(rowIndex);
						if(modelVo.getStatus() == 1){
							modelVo.getCurrentThread().interrupt();
						}else if(modelVo.getStatus() == 0){
							modelVo.setStatus(1);
							String id = modelVo.getId();
							XiaZaiContext ctx = XiaZaiContext.getContext();
							XiaZaiContextVo contextVo = (XiaZaiContextVo)ctx.getProperty(id);
							String downloadClazz = contextVo.getDownloadClass();
							try {
								Class clazz = Class.forName(downloadClazz);
								Constructor constructor = clazz.getConstructor(String.class);
								Download download = (Download)constructor.newInstance(id);
								download.set(new URL(contextVo.getUrl()), contextVo.getTmpPath(), table);
								new DownloadRunnable(download).start();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			});
		}
		return table;
	}
	private JMenuBar jmenuBar(){
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("菜单");
		JMenuItem item = new JMenuItem();
		item.setText("新建");
		item.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new NewDownloadDialog(MainFrame.this);
			}
			
		});
		menu.add(item);
		bar.add(menu);
		return bar;
	}
}
