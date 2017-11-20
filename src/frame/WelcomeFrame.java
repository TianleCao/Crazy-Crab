package frame;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import helper.JDBCAccesser;
import helper.JDBCAccesser.Person;
import model.CrabPanel;
import model.ExitButton;
import model.InfiniteProgressPanel;
import model.RichLabel;
import model.CrabLabel;
import model.StartButton;
import model.WatermarkTextField;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class WelcomeFrame{
	final JFrame frame=new JFrame("Crazy Crab");
	final CrabPanel panel=new CrabPanel();
	CrabLabel label;
	StartButton start;
	ExitButton exit;
	RichLabel nickname;
	RichLabel AboutMe;
	RichLabel timetotal;
	RichLabel RegDate;
	RichLabel myfriends;
	WatermarkTextField tf;
	RichLabel MenuLabel;
	ArrayList<Person> person=new ArrayList<Person>(5);
	ArrayList<RichLabel> labels=new ArrayList<RichLabel>(5);
	InfiniteProgressPanel glasspane = new InfiniteProgressPanel();
	public Task task;
	String username;
	int usertime;
	Date date;
	class Task extends SwingWorker<Void,Void>{

		@Override
		protected Void doInBackground() throws Exception {
			label=new CrabLabel();
			label.setLocation(0,0);
			panel.add(label);
			
			start=new StartButton();
			start.setToolTipText("");
			start.setLocation(212,244);
			panel.add(start);
			
			exit=new ExitButton();
			exit.setLocation(212, 309);
			panel.add(exit);
			
			nickname=new RichLabel("NickName:    "+username,0);
			nickname.setLocation(400, 50);
			nickname.setFont(new Font("Î¢ÈíÑÅºÚ", Font.ITALIC,10));
			nickname.setLeftShadow(1,1,Color.WHITE);
			nickname.setRightShadow(1,1,Color.WHITE);
			nickname.setForeground(Color.BLACK);
			nickname.setFont(nickname.getFont().deriveFont(15f));
			nickname.setSize(nickname.getPreferredSize());
			
			AboutMe=new RichLabel("About Me",0);
			AboutMe.setLocation(415, 0);
			AboutMe.setFont(new Font("Î¢ÈíÑÅºÚ", Font.ITALIC|Font.BOLD,10));
			AboutMe.setLeftShadow(2,2,Color.BLACK);
			AboutMe.setRightShadow(2,2,Color.BLACK);
			AboutMe.setForeground(Color.GREEN);
			AboutMe.setFont(AboutMe.getFont().deriveFont(30f));
			AboutMe.setSize(AboutMe.getPreferredSize());
			
			timetotal=new RichLabel("TimeTotal:   "+usertime+"min",0);
			timetotal.setLocation(400, 80);
			timetotal.setFont(new Font("Î¢ÈíÑÅºÚ", Font.ITALIC,10));
			timetotal.setLeftShadow(1,1,Color.WHITE);
			timetotal.setRightShadow(1,1,Color.WHITE);
			timetotal.setForeground(Color.BLACK);
			timetotal.setFont(timetotal.getFont().deriveFont(15f));
			timetotal.setSize(timetotal.getPreferredSize());
			
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			RegDate=new RichLabel("Since:          "+sd.format(date),0);
			RegDate.setLocation(400, 110);
			RegDate.setFont(new Font("Î¢ÈíÑÅºÚ", Font.ITALIC,10));
			RegDate.setLeftShadow(1,1,Color.WHITE);
			RegDate.setRightShadow(1,1,Color.WHITE);
			RegDate.setForeground(Color.BLACK);
			RegDate.setFont(RegDate.getFont().deriveFont(15f));
			RegDate.setSize(RegDate.getPreferredSize());
			
			myfriends=new RichLabel("My Friends",0);
			myfriends.setLocation(415,150);
			myfriends.setFont(new Font("Î¢ÈíÑÅºÚ", Font.ITALIC|Font.BOLD,10));
			myfriends.setLeftShadow(2,2,Color.BLACK);
			myfriends.setRightShadow(2,2,Color.BLACK);
			myfriends.setForeground(Color.GREEN);
			myfriends.setFont(myfriends.getFont().deriveFont(30f));
			myfriends.setSize(myfriends.getPreferredSize());
			
			tf=new WatermarkTextField();
			tf.setBounds(381,0,219,450);
			
			MenuLabel=new RichLabel(String.format("%-10s","Rank")+String.format("%-10s","Name")+String.format("%-5s","Time"),0);
			MenuLabel.setLocation(400,200);
			MenuLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.ITALIC|Font.BOLD,15));
			MenuLabel.setLeftShadow(1,1,Color.WHITE);
			MenuLabel.setRightShadow(1,1,Color.WHITE);
			MenuLabel.setForeground(Color.BLACK);
			MenuLabel.setFont(MenuLabel.getFont().deriveFont(15f));
			MenuLabel.setSize(MenuLabel.getPreferredSize());
			
			
			try{
				JDBCAccesser friends=new JDBCAccesser();
				person=friends.GetSorted();
				friends.Close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			Iterator<Person> It=person.iterator();
			int m=0;
			while (It.hasNext()){
				Person user=It.next();
				final RichLabel label0=new RichLabel((m+1)+"             "+String.format("%-12s",user.name)+String.format("%-5d",user.time),0);
				//System.out.println((m+1)+"             "+String.format("%-10s",user.name)+String.format("%-5d",user.time));
				label0.setFont(new Font("Î¢ÈíÑÅºÚ", Font.ITALIC|Font.BOLD,15));
				label0.setLeftShadow(1,1,Color.WHITE);
				label0.setRightShadow(1,1,Color.WHITE);
				if (user.name.equals(username)){
					label0.setForeground(Color.BLUE);
				}
				else 
					label0.setForeground(Color.BLACK);
				label0.setFont(label0.getFont().deriveFont(15f));
				label0.setSize(label0.getPreferredSize());
				label0.setLocation(400,200+(int)((m+1)*label0.getPreferredSize().getHeight()));
				labels.add(label0);
				m++;
			}
			
			start.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new Game(username,usertime,date);
					frame.dispose();
				}
			});
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getX()<tf.getBounds().getMinX()){
						try{
							panel.remove(tf);
							panel.remove(AboutMe);
							panel.remove(nickname);
							panel.remove(timetotal);
							panel.remove(myfriends);
							panel.remove(MenuLabel);
							panel.remove(RegDate);
							for (RichLabel l:labels){
								panel.remove(l);
							}
							panel.validate();
							panel.repaint();
						}
						catch(Exception ex){
					}
					}
					else{
						try{
							panel.add(AboutMe);
							panel.add(nickname);
							panel.add(timetotal);
							panel.add(myfriends);
							panel.add(RegDate);
							panel.add(MenuLabel);
							for (RichLabel l:labels){
								panel.add(l);
							}
							panel.add(tf);
							panel.validate();
							panel.repaint();
						}
						catch(Exception ex){
					}
					}
				}
			});
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			System.out.println("loaded");
			return null;
		}
		@Override
        public void done() {
			glasspane.stop();
		}		
	}
	public  void CreateandShowGUI(){
		frame.getContentPane().add(panel);
		frame.setSize(500, 500);
		Dimension dimension=frame.getSize();
		glasspane.setBounds(100,100,dimension.width,dimension.height);
		frame.setGlassPane(glasspane);
		glasspane.start();//¿ªÊ¼¶¯»­¼ÓÔØÐ§¹û	
		frame.pack();
		frame.setVisible(true);
		task = new Task();
        task.execute();
	}
		public WelcomeFrame(final String username,final int usertime,final Date date) throws IOException{
			
			//frame.getContentPane().add(panel);
			this.username=username;
			this.usertime=usertime;
			this.date=date;		
			this.CreateandShowGUI();
		}
		public WelcomeFrame(String username,int usertime,Date date,long time) throws IOException{
			int nowtime=usertime;
			try {
				JDBCAccesser TimeChanger=new JDBCAccesser();
				TimeChanger.AddUserTime(username, time);
				nowtime=TimeChanger.GetUserTime(username);
				TimeChanger.Close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new WelcomeFrame(username,nowtime,date);
		}
}
