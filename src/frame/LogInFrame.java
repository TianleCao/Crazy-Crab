package frame;

import javax.swing.JFrame;
import com.sun.awt.AWTUtilities;

import helper.JDBCAccesser;
import model.InfiniteProgressPanel;
import model.LogCloseButton;
import model.LogInButton;
import model.SignUpButton;
import model.TransferButton;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
public class LogInFrame extends JFrame{
	private ImageIcon leftIcon = new ImageIcon("material\\login_left.png");
    private ImageIcon rightIcon = new ImageIcon("material\\login_right.png");
    private ImageIcon leftTopIcon = new ImageIcon("material\\login_left_top.png");
	//主界面组件
	private TransferButton transferbutton=new TransferButton();
	private JLabel logoLabel=new JLabel(new ImageIcon("material\\login_logo.png"));
	private LogCloseButton btnClose =new LogCloseButton();
	private JPanel inputPane = new JPanel();
	private final JLabel lblSignature = new JLabel("@Powered By Arenas");
	//登录界面组件
	private final JLabel lblUserName = new JLabel("User Name");
	private final JTextField Name = new JTextField();
	private final JLabel lblPassWord = new JLabel("PassWord");
	private final JPasswordField Code = new JPasswordField();
    private final LogInButton btnLogin=new LogInButton();
	//注册界面组件
	private final JTextArea textArea = new JTextArea();
	private final JLabel lblUserName1 = new JLabel("User Name");
	private final JLabel lblPassWord1 = new JLabel("PassWord");
	private final JLabel lblPassWord2 = new JLabel("PassWord");
	private final JTextField NameSet= new JTextField();
	private final JPasswordField CodeSet_1 = new JPasswordField();
	private final JPasswordField CodeSet_2 = new JPasswordField();
    private final SignUpButton btnSign=new SignUpButton();
    //界面大小
  	private int width = btnLogin.getWidth();
  	private int height = btnLogin.getHeight() +75+leftIcon.getIconHeight() + logoLabel.getHeight();
  	
	private MouseAdapter moveWindowListener = new MouseAdapter() {

        private Point lastPoint = null;

        @Override
        public void mousePressed(MouseEvent e) {
            lastPoint = e.getLocationOnScreen();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point = e.getLocationOnScreen();
            int offsetX = point.x - lastPoint.x;
            int offsetY = point.y - lastPoint.y;
            Rectangle bounds = LogInFrame.this.getBounds();
            bounds.x += offsetX;
            bounds.y += offsetY;
            LogInFrame.this.setBounds(bounds);
            lastPoint = point;
        }
	};
	public LogInFrame(){
		init();
	}
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setUndecorated(true);
	    AWTUtilities.setWindowOpaque(this, false);
	    inputPane.setBackground(UIManager.getColor("Button.background"));
	    inputPane.setSize(this.getSize());
	    final JPanel centerPane = new JPanel(new BorderLayout());
	    centerPane.add(btnLogin, BorderLayout.SOUTH);
	    this.setContentPane(centerPane);
	    this.setSize(width, height);
	    
	    JPanel topPane = new JPanel(new BorderLayout());
	    logoLabel.setOpaque(false);
	    logoLabel.setSize(getPreferredSize());
	    
	    topPane.setOpaque(false);
	    topPane.add(new JLabel(this.leftTopIcon), BorderLayout.WEST);
	    topPane.add(btnClose, BorderLayout.EAST);
	    topPane.add(logoLabel, BorderLayout.CENTER);
	    centerPane.add(new JLabel(this.leftIcon), BorderLayout.WEST);
	    centerPane.add(new JLabel(this.rightIcon), BorderLayout.EAST);
	    centerPane.add(topPane, BorderLayout.NORTH);
	    centerPane.add(inputPane, BorderLayout.CENTER);
	    
	    inputPane.setBorder(new EmptyBorder(0, 0, 0, 0));
	    inputPane.setLayout(null);
	    
	    //配置登录界面组件
		Name.setBounds(93, 50, 144, 28);
	    lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		
	    lblUserName.setForeground(new Color(0, 128, 0));
	    lblUserName.setFont(new Font("微软雅黑", Font.BOLD, 12));
	    lblUserName.setBounds(10, 56, 73, 15);
	    
	    lblPassWord.setForeground(new Color(0, 128, 0));
	    lblPassWord.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblPassWord.setFont(new Font("微软雅黑", Font.BOLD, 12));
	    lblPassWord.setBounds(10, 121, 73, 15);
	    
	    Code.setBounds(93, 115, 144, 28);
	    inputPane.add(Name);
	    inputPane.add(lblUserName);
	    inputPane.add(lblPassWord);
	    inputPane.add(Code);
	    
	    //配置注册界面组件
		textArea.setFont(new Font("楷体", Font.BOLD, 13));
		textArea.setBackground(UIManager.getColor("Button.background"));
		textArea.setEditable(false);
		textArea.setForeground(Color.RED);
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(210,54,20,20);
		
		NameSet.setBounds(93, 50, 144, 28);
		
	    lblUserName1.setForeground(new Color(0, 128, 0));
	    lblUserName1.setFont(new Font("微软雅黑", Font.BOLD, 12));
	    lblUserName1.setBounds(10, 56, 73, 15);
	    
	    lblPassWord1.setForeground(new Color(0, 128, 0));
	    lblPassWord1.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblPassWord1.setFont(new Font("微软雅黑", Font.BOLD, 12));
	    lblPassWord1.setBounds(10, 101, 73, 15);
	    
	    lblPassWord2.setForeground(new Color(0, 128, 0));
	    lblPassWord2.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblPassWord2.setFont(new Font("微软雅黑", Font.BOLD, 12));
	    lblPassWord2.setBounds(10, 146, 73, 15);
	    
	    CodeSet_1.setBounds(93, 95, 144, 28);
	    
	    CodeSet_2.setBounds(93, 140, 144, 28);
	    
	    transferbutton.setBounds(68, 10, 134, 40);
	    inputPane.add(transferbutton);
	    
	    lblSignature.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 12));
	    lblSignature.setForeground(new Color(0, 128, 0));
	    lblSignature.setBounds(103, 198, 134, 15);
	    
	    inputPane.add(lblSignature);
	    inputPane.addMouseListener(moveWindowListener);
	    inputPane.addMouseMotionListener(moveWindowListener);
	    logoLabel.addMouseListener(moveWindowListener);
	    logoLabel.addMouseMotionListener(moveWindowListener);
	    //设置事件响应函数
	    //按下注册/登录切换键，重新设置面板
	    transferbutton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (!transferbutton.isLogIn()){
	    			inputPane.remove(NameSet);
	    			inputPane.remove(lblUserName1);
	    			inputPane.remove(lblPassWord1);
	    			inputPane.remove(lblPassWord2);
	    			inputPane.remove(CodeSet_1);
	    			inputPane.remove(CodeSet_2);
	    			inputPane.remove(textArea);
	    			inputPane.add(Name);
	    			inputPane.add(lblUserName);
	    			inputPane.add(Code);
	    			inputPane.add(lblPassWord);
	    			inputPane.validate();
	    			centerPane.remove(btnSign);
	    			centerPane.add(btnLogin, BorderLayout.SOUTH);
	    			centerPane.validate();
	    			repaint();
	    		}
	    		else{
	    			inputPane.add(NameSet);
	    			inputPane.add(lblUserName1);
	    			inputPane.add(lblPassWord1);
	    			inputPane.add(lblPassWord2);
	    			inputPane.add(CodeSet_1);
	    			inputPane.add(CodeSet_2);
	    			inputPane.add(textArea);
	    			inputPane.remove(Name);
	    			inputPane.remove(lblUserName);
	    			inputPane.remove(Code);
	    			inputPane.remove(lblPassWord);
	    			inputPane.validate();
	    			centerPane.remove(btnLogin);
	    			centerPane.add(btnSign, BorderLayout.SOUTH);
	    			centerPane.validate();
	    			repaint();
	    		}
	    	}
	    });
	    //输入完毕新的用户名，判断是否存在
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
		//按下登录键
		btnLogin.addActionListener(new ActionListener() {
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
		//按下注册键
		btnSign.addActionListener(new ActionListener() {
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
						JOptionPane.showMessageDialog(null,"注册成功","恭喜",JOptionPane.INFORMATION_MESSAGE);
					}
					else 
						JOptionPane.showMessageDialog(null,"请重新设置","两次密码不一致",JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception ex){
					ex.printStackTrace();
			}
			}
		});
		//按下关闭键
        btnClose.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
	    this.setVisible(true);
	}
	public static void main(String args[]){
		LogInFrame log=new LogInFrame();
	}
}
