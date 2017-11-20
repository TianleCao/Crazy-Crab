package tester;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import frame.WelcomeFrame;
import helper.JDBCAccesser;
import model.InfiniteProgressPanel;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import java.sql.*;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;

public class LogIn extends JFrame{

	private JPanel contentPane_log;
	private JPasswordField Code;
	private JTextField Name;
	private JLabel lblNewLabel_1;
	private JButton LogInto;
	private JButton Exit1;
	private JTabbedPane tabbedPane;
	private JPanel contentPane_reg;
	private JLabel label;
	private JLabel label_1;
	private JPasswordField CodeSet_1;
	private JButton RegisterIn;
	private JButton Exit2;
	private JTextField NameSet;
	private JPasswordField CodeSet_2;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public LogIn() {
		setTitle("Welcome to Crazy Crab!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 429, 300);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 3;
		gbc_tabbedPane.gridy = 1;
		contentPane_log = new JPanel();
		contentPane_log.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(tabbedPane);
		GridBagLayout gbl_contentPane_log = new GridBagLayout();
		gbl_contentPane_log.columnWidths = new int[]{0, 73, 73, 73, 73, 73, 73, 0};
		gbl_contentPane_log.rowHeights = new int[]{20, 34, 22, 39, 0, 0, 0, 0};
		gbl_contentPane_log.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane_log.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane_log.setLayout(gbl_contentPane_log);
		tabbedPane.addTab("登录", contentPane_log);
		
		JLabel lblNewLabel = new JLabel("\u6635\u79F0");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		contentPane_log.add(lblNewLabel, gbc_lblNewLabel);
		
		Name = new JTextField();
		GridBagConstraints gbc_Name = new GridBagConstraints();
		gbc_Name.gridwidth = 3;
		gbc_Name.insets = new Insets(0, 0, 5, 5);
		gbc_Name.fill = GridBagConstraints.HORIZONTAL;
		gbc_Name.gridx = 2;
		gbc_Name.gridy = 1;
		contentPane_log.add(Name, gbc_Name);
		Name.setColumns(10);
		
		lblNewLabel_1 = new JLabel("\u5BC6\u7801");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		contentPane_log.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		Code = new JPasswordField();
		GridBagConstraints gbc_Code = new GridBagConstraints();
		gbc_Code.gridwidth = 3;
		gbc_Code.insets = new Insets(0, 0, 5, 5);
		gbc_Code.fill = GridBagConstraints.HORIZONTAL;
		gbc_Code.gridx = 2;
		gbc_Code.gridy = 3;
		contentPane_log.add(Code, gbc_Code);
		
		lblNewLabel_3 = new JLabel("\u8FD8\u6CA1\u6709\u8D26\u53F7\uFF1F\u8D76\u5FEB\u70B9\u51FB\u6CE8\u518C\u5427~");
		lblNewLabel_3.setForeground(Color.RED);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 4;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 6;
		contentPane_log.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		LogInto = new JButton("\u786E\u5B9A");
		LogInto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//校验用户密码
				try{
					String CodeTemp=new String(Code.getPassword());
					JDBCAccesser UserInfo=new JDBCAccesser();
					try{
						String Code=UserInfo.GetUserCode(Name.getText());
						if (!Code.equals(CodeTemp)){
							JOptionPane.showMessageDialog(null,"请重新输入","密码错误",JOptionPane.INFORMATION_MESSAGE);
							UserInfo.Close();
						}
						else {
							new WelcomeFrame(Name.getText(),UserInfo.GetUserTime(Name.getText()),UserInfo.GetRegDate(Name.getText()));
							UserInfo.Close();
							dispose();
						}
					}
					catch(Exception e){
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"请先注册","用户名不存在",JOptionPane.INFORMATION_MESSAGE);
					}		
				}
				catch(Exception ex){
					ex.printStackTrace();
			}	
			}
		});
		GridBagConstraints gbc_LogInto = new GridBagConstraints();
		gbc_LogInto.insets = new Insets(0, 0, 0, 5);
		gbc_LogInto.gridx = 4;
		gbc_LogInto.gridy = 6;
		contentPane_log.add(LogInto, gbc_LogInto);
		
		Exit1 = new JButton("\u9000\u51FA");
		Exit1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_Exit1 = new GridBagConstraints();
		gbc_Exit1.insets = new Insets(0, 0, 0, 5);
		gbc_Exit1.gridx = 5;
		gbc_Exit1.gridy = 6;
		contentPane_log.add(Exit1, gbc_Exit1);
		
		contentPane_reg = new JPanel();
		contentPane_reg.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("注册", null, contentPane_reg, null);
		GridBagLayout gbl_contentPane_reg = new GridBagLayout();
		gbl_contentPane_reg.columnWidths = new int[]{62, 58, 48, 58, 57, 59, 0};
		gbl_contentPane_reg.rowHeights = new int[]{16, 0, 35, 23, 21, 21, 38, 36, 23, 0};
		gbl_contentPane_reg.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane_reg.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane_reg.setLayout(gbl_contentPane_reg);
		
		lblNewLabel_2 = new JLabel("\u6635\u79F0");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 2;
		contentPane_reg.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("楷体", Font.BOLD, 13));
		textArea.setBackground(UIManager.getColor("Button.background"));
		textArea.setEditable(false);
		textArea.setForeground(Color.RED);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 5;
		gbc_textArea.gridy = 2;
		contentPane_reg.add(textArea, gbc_textArea);
		
		NameSet = new JTextField();
		NameSet.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try{
					JDBCAccesser NameCheck=new JDBCAccesser();
					String NameInput=NameSet.getText();
					try{
						NameCheck.GetUserCode(NameInput);
						textArea.setForeground(Color.RED);
						textArea.setText("×");
					}
					catch(Exception NameNotFoundEx){
						textArea.setFont(new Font("楷体", Font.BOLD, 20));
						textArea.setForeground(Color.GREEN);
						textArea.setText("√");
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_NameSet = new GridBagConstraints();
		gbc_NameSet.fill = GridBagConstraints.HORIZONTAL;
		gbc_NameSet.insets = new Insets(0, 0, 5, 5);
		gbc_NameSet.gridwidth = 3;
		gbc_NameSet.gridx = 2;
		gbc_NameSet.gridy = 2;
		contentPane_reg.add(NameSet, gbc_NameSet);
		NameSet.setColumns(10);

		
		label = new JLabel("\u5BC6\u7801");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 4;
		contentPane_reg.add(label, gbc_label);
		
		CodeSet_2 = new JPasswordField();
		GridBagConstraints gbc_CodeSet_2 = new GridBagConstraints();
		gbc_CodeSet_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_CodeSet_2.insets = new Insets(0, 0, 5, 5);
		gbc_CodeSet_2.gridwidth = 3;
		gbc_CodeSet_2.gridx = 2;
		gbc_CodeSet_2.gridy = 4;
		contentPane_reg.add(CodeSet_2, gbc_CodeSet_2);
		
		label_1 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 6;
		contentPane_reg.add(label_1, gbc_label_1);
		
		CodeSet_1 = new JPasswordField();
		GridBagConstraints gbc_CodeSet_1 = new GridBagConstraints();
		gbc_CodeSet_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_CodeSet_1.insets = new Insets(0, 0, 5, 5);
		gbc_CodeSet_1.gridwidth = 3;
		gbc_CodeSet_1.gridx = 2;
		gbc_CodeSet_1.gridy = 6;
		contentPane_reg.add(CodeSet_1, gbc_CodeSet_1);
		
		RegisterIn = new JButton("\u786E\u5B9A");
		RegisterIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//为数据库新建条目
				try{
					JDBCAccesser UserRegister=new JDBCAccesser();
					String Nickname=NameSet.getText();
					String code1=CodeSet_1.getText();
					String code2=CodeSet_2.getText();
					if (code1.equals(code2))
					{
						UserRegister.AddUser(Nickname, code1,new Date());
						UserRegister.Close();
					}
					else 
						JOptionPane.showMessageDialog(null,"请重新设置","两次密码不一致",JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception ex){
					ex.printStackTrace();
			}
			}
		});
		GridBagConstraints gbc_RegisterIn = new GridBagConstraints();
		gbc_RegisterIn.insets = new Insets(0, 0, 5, 5);
		gbc_RegisterIn.gridx = 4;
		gbc_RegisterIn.gridy = 7;
		contentPane_reg.add(RegisterIn, gbc_RegisterIn);
		
		Exit2 = new JButton("\u9000\u51FA");
		Exit2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_Exit2 = new GridBagConstraints();
		gbc_Exit2.insets = new Insets(0, 0, 5, 0);
		gbc_Exit2.gridx = 5;
		gbc_Exit2.gridy = 7;
		contentPane_reg.add(Exit2, gbc_Exit2);
	}
}
