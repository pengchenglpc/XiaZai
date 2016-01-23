package com.lpc.xiazai.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lpc.xiazai.common.CommonUtil;
import com.lpc.xiazai.common.XiaZaiContext;
import com.lpc.xiazai.download.Download;
import com.lpc.xiazai.download.DownloadRunnable;
import com.lpc.xiazai.download.HttpDownload;
import com.lpc.xiazai.vo.XiaZaiContextVo;

public class NewDownloadDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5929064814185251908L;
	private MainFrame owner;

	public NewDownloadDialog(MainFrame owner){
		this(owner, "新建下载任务");
	}
	public NewDownloadDialog(MainFrame owner, String title){
		super(owner, title, true);
		this.owner = owner;
		initDialog();
	}
	public void initDialog(){
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.setLayout(new FlowLayout());
		this.setSize(350, 150);
		setLocationRelativeTo(null);
		JLabel urlLabel = new JLabel("下载链接：");
		JTextField urlText = new JTextField(20);
//		urlText.setSize(100, 10);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel urlPanel = new JPanel();
		urlPanel.setLayout(new FlowLayout());
		urlPanel.add(urlLabel);
		urlPanel.add(urlText);
		mainPanel.add(urlPanel);
		JPanel addressPanel = new JPanel();
		addressPanel.setLayout(new FlowLayout());
		JLabel addressLabel = new JLabel("下载链接：");
		JTextField addressText = new JTextField(13);
		JButton addressButton = new JButton("选择文件夹");
		addressButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		        jfc.showDialog(new JLabel(), "选择");  
		        File file=jfc.getSelectedFile();
		        if(file != null)
		        	addressText.setText(file.getPath());
		          
			}
			
		});
		addressPanel.add(addressLabel);
		addressPanel.add(addressText);
		addressPanel.add(addressButton);
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		JButton okButton = new JButton("确定");
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(urlText.getText().length() < 1){
					JOptionPane.showMessageDialog(NewDownloadDialog.this, "请填写下载链接", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(addressText.getText().length() < 1){
					JOptionPane.showMessageDialog(NewDownloadDialog.this, "请填写或选择存储地址", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				URL url = null;
				try {
					url = new URL(urlText.getText());
				} catch (MalformedURLException e1) {
					JOptionPane.showMessageDialog(NewDownloadDialog.this, "请填写正确的下载链接", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				String protocol = url.getProtocol();
				String id = CommonUtil.id();
				Download download = null;
				if("http".equalsIgnoreCase(protocol)){
					download = new HttpDownload(id);
				}
				System.out.println(download.getClass().getName());
				XiaZaiContext ctx = XiaZaiContext.getContext();
				XiaZaiContextVo contextVo = new XiaZaiContextVo();
				
				contextVo.setId(id);
				contextVo.setDownloadClass(download.getClass().getName());
				contextVo.setUrl(url.toString());
				ctx.setProperty(id, contextVo);
				
				download.set(url, addressText.getText(), owner.getTable());
				new DownloadRunnable(download).start();
				NewDownloadDialog.this.dispose();
			}
			
		});
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				NewDownloadDialog.this.dispose();
			}
			
		});
		btnPanel.add(okButton);
		btnPanel.add(cancelButton);
		mainPanel.add(addressPanel);
		mainPanel.add(btnPanel);
		this.setContentPane(mainPanel);
		
		this.setVisible(true);
	}
}
