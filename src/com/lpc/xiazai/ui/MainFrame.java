package com.lpc.xiazai.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
		JPanel panel = new JPanel();
		JScrollPane scrollPanel = new JScrollPane(getTable());
		panel.add(scrollPanel);
		this.setContentPane(panel);
		this.setJMenuBar(jmenuBar());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.out.println("你正在关闭窗体");
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
						XiaZaiTableModel model = (XiaZaiTableModel) table.getModel();
						XiaZaiModelVo modelVo = model.getAt(rowIndex);
						if(modelVo.getStatus() == 1){
							modelVo.getCurrentThread().interrupt();
						}else{
							modelVo.getCurrentThread().start();
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
