package frame;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import element.BButton;
import element.BGMusic;
import element.Ball;
import element.Brick;
import element.CButton;
import element.CrackVoice;
import element.GamePanel;
import element.MButton;
import element.Paddle;
import element.Prop;
import element.SButton;
import frame.WelcomeFrame.Task;
import helper.FaceRecognizer;
import model.InfiniteProgressPanel;
import tester.FaceApp;
import tester.ThreadTester;
import element.Matress;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Game extends JFrame implements Runnable{
	double dxb = -5, dyb = -6;//定义球速度
	static int delay= 100; // 刷新间隔
	static int xnum=7;		//砖块列数
	static int ynum=7;		//砖块行数
	static int h=545;		//窗体实际高度
	Thread t = new Thread(this);
	Thread f;
	public  Brick brick[][]; // 砖块
	public  Ball ball; // 球
	public  Paddle paddle; // 板块
	GamePanel panel; // 画板
	ArrayList<Prop> tools;
	ArrayList<Matress> mat;
	SButton sbutton;
	MButton mbutton;
	BButton bbutton;
	CButton cbutton;
	boolean isstop=false;	//是否暂停
	boolean ismusic=true;	//是否打开音乐
	BGMusic music;
	CrackVoice crackvoice;
	FaceRecognizer face;
	boolean clockchange=false;
	boolean exit=false;
	int[][] map=new int[][]{{1,0,0,0,1,1,1},{0,1,0,0,0,1,1},{0,1,1,0,0,1,1},{0,1,1,1,0,1,1},{0,1,1,0,0,1,1},{0,1,0,0,0,1,1},{1,0,0,0,1,1,1}};
	int sum=27; //砖块总数
	long time1;
	long time2;
	String username;
	int usertime;
	Date date;
	InfiniteProgressPanel glasspane = new InfiniteProgressPanel();
	public Task task;
	class Task extends SwingWorker<Void,Void>{

		@Override
		protected Void doInBackground() throws Exception {
			GameInit();
			return null;
		}
		@Override
        public void done() {
			glasspane.stop();
		}	
	}
	public Game(String username,int usertime,Date date){
		this.username=username;
		this.usertime=usertime;
		this.date=date;
		this.setSize(480,580);
		Dimension dimension=this.getSize();
		glasspane.setBounds(100,100,dimension.width,dimension.height);
		this.setGlassPane(glasspane);
		glasspane.start();//开始动画加载效果	
		this.pack();
		this.setVisible(true);
		task = new Task();
        task.execute();
	}
	public void UIinit(){
		tools=new ArrayList<Prop>();
		mat=new ArrayList<Matress>();
		sbutton=new SButton();
		mbutton=new MButton();
		bbutton=new BButton();
		cbutton=new CButton();
		crackvoice=new CrackVoice();
		exit=false;
		clockchange=false;
		isstop=false;
		panel=new GamePanel();
		this.setContentPane(panel);
		this.setTitle("Crazy Crab");
		setSize(480,580);
		dxb=-5;
		dyb=-6;
		//paddle=new Paddle(getWidth()/2-paddle.width/2,getHeight()-Paddle.height);//将木板置于界面底端中心
		Paddle.width=120;
		Paddle.height=55;
		paddle=new Paddle(360,h-Paddle.height);
		brick=new Brick[xnum][ynum];
		for (int j = 0; j < ynum; j++) {//创建砖块
			for (int i = 0; i < xnum; i++) {
				brick[i][j]=new Brick(i*60+20,j *30+20);
				brick[i][j].setSize(getPreferredSize());
				if (map[i][j]==0) brick[i][j].SetDisplay(0);
				if ((i==0&&(j==0))||(i==0&&j==4)||(i==6&&j==0)||(i==6&&j==4)) brick[i][j].SetSpeed();
				getContentPane().add(brick[i][j]);
			}
		}
		panel.requestFocus();
		ball=new Ball(paddle.x_pos+Paddle.width/2-Ball.r,paddle.y_pos - Ball.r-10);
		//ball.setLocation(0, 0);
		ball.setSize(getPreferredSize());
		getContentPane().add(ball);
		paddle.setSize(getPreferredSize());
		getContentPane().add(paddle);
		//设置按键
		sbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			isstop=!isstop;
			//sbutton.setFocusable(false);
			Game.this.requestFocus();
		}
		});
		sbutton.setLocation(365, 0);
		sbutton.setSize(20,20);
		getContentPane().add(sbutton);
		mbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ismusic)
					music.MusicStop();
				else
					music.MusicStart();
				ismusic=!ismusic;
				Game.this.requestFocus();
			}
		});
		mbutton.setLocation(390, 0);
		mbutton.setSize(20,20);
		getContentPane().add(mbutton);	
		cbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				music.MusicChange();
				Game.this.requestFocus();
			}
		});
		cbutton.setLocation(415, 0);
		cbutton.setSize(20,20);
		bbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit=true;
				face.stop();
				music.MusicStop();
				Game.this.dispose();
				time2=System.currentTimeMillis();
				try {
					new WelcomeFrame(username,usertime,date,time2-time1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		getContentPane().add(bbutton);
		bbutton.setLocation(440, 0);
		bbutton.setSize(20,20);
		getContentPane().add(cbutton);
		this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int speed = 10;
                // A 和 左箭头 可以向左移动
                if (!isstop){
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    paddle.SetPaddle(paddle.x_pos-speed,paddle.y_pos);
                    repaint();
                }
                // D 和 右箭头 可以向右移动
                if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    paddle.SetPaddle(paddle.x_pos+speed,paddle.y_pos);
                    repaint();
                }
            }
            }
        });
		Game.this.setFocusable(true);
		this.validate();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		repaint();
		this.pack();
		this.setVisible(true);
	}
	public void GameInit(){
		UIinit();
		face=new FaceRecognizer();
		f=new Thread(face);
		f.start();
		t.start();
		music=new BGMusic();
		music.MusicStart();
		glasspane.stop();
		time1=System.currentTimeMillis();
	}
	public boolean isComplete(){
		 for (int j = 0; j < brick[0].length; j++) { // 判断与砖块的碰撞,砖块的运动
			for (int i = 0; i < brick.length; i++) {
				if (brick[i][j].IsDisplay()) return false;
			}
		 }
		 return true;
	}
	public boolean isHalfComplete(){
		int count=0;
		 for (int j = 0; j < brick[0].length; j++) { // 判断与砖块的碰撞,砖块的运动
			for (int i = 0; i < brick.length; i++) {
				if (brick[i][j].IsDisplay()) count++;
			}
		 }
		 if (count<(int)(sum/2)) return true;
		 else return false;
	}
	public void run(){
		while (!exit){
		if ((ball.x_pos)<=2 || (ball.x_pos +1.5*Ball.r >= getWidth()-8)) { // 超出左右边界
			dxb = -dxb;//边框反弹
		}
		if (ball.y_pos <=2) { // 超出上边界
			dyb = -dyb;
		}
		if (ball.y_pos+Ball.r >=h) {//如果大于底边
			Iterator<Matress> e=mat.iterator();
			if (e.hasNext()){
				getContentPane().remove(e.next());
				e.remove();
				this.validate();
				dyb = -dyb;//坐垫上反弹
			}
			else
			if (ball.x_pos+ Ball.r < paddle.x_pos|| ball.x_pos > paddle.x_pos + Paddle.width) { // 没有落在板上面
				int h=JOptionPane.showConfirmDialog(null, "再来一遍？","You die", JOptionPane.YES_NO_OPTION);//弹出是否重来选择框
				if (h==0){
					exit=true;
					if(clockchange) face.ClockChange();
					UIinit();
				}
				else {
					exit=true;
					face.stop();
					music.MusicStop();
					Game.this.dispose();
					time2=System.currentTimeMillis();
					try {
						new WelcomeFrame(username,usertime,date,time2-time1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} 
		}
			else{
				if (((ball.x_pos+ball.r/2)>=paddle.x_pos )&&((ball.x_pos+ball.r/2) < (paddle.x_pos + Paddle.width))) {
					if ((((int)(ball.y_pos/10)*10+Ball.r)==paddle.y_pos)&&(dyb>0)){//上下碰撞
						dyb = -dyb;//木板上反弹
						crackvoice.knock();
					}
				}
				if (((ball.y_pos+ball.r/2)>=paddle.y_pos )&&((ball.y_pos+ball.r/2) < (paddle.y_pos + Paddle.width))) {
					if ((((int)(ball.x_pos/10)*10+Ball.r)==paddle.x_pos)&&(((int)(ball.x_pos/10+1)*10+Ball.r/2)<=(paddle.x_pos+paddle.width))){//左右碰撞
						dxb=-dxb;
						crackvoice.knock();
					}
				}
			}

		for (int j = 0; j < brick[0].length; j++) { // 判断与砖块的碰撞,砖块的运动
			for (int i = 0; i < brick.length; i++) {
				if ((brick[i][j].IsDisplay())&&(!brick[i][j].IsCracked())) {//上下碰撞
					if((ball.x_pos+Ball.r/2<=brick[i][j].x_pos+Brick.width)&&(ball.x_pos+Ball.r/2>=brick[i][j].x_pos)){
						if(((int)(ball.y_pos/10)*10+Ball.r==brick[i][j].y_pos)||((int)(ball.y_pos/10+1)*10==brick[i][j].y_pos+Brick.height)){
							brick[i][j].count++;
							if(!ball.GetThough())//如果不是穿透球
								brick[i][j].MinusDisplay();
							else brick[i][j].SetDisplay(0);
							if ((brick[i][j].tool!=0)&&(!brick[i][j].IsDisplay())){
								Prop prop=new Prop(brick[i][j].tool,brick[i][j].x_pos,brick[i][j].y_pos+Brick.height);
								prop.setSize(getPreferredSize());
								getContentPane().add(prop);
								tools.add(prop);
								this.validate();
							}
							if(!ball.GetThough()){//如果不是穿透球
							dyb=-dyb;
							if (!brick[i][j].IsDisplay())//如果是铁块
								crackvoice.knock();
							else 
								crackvoice.back();
							}
						}
					}
				}
				if ((brick[i][j].IsDisplay())&&(!brick[i][j].IsCracked())) {//侧面碰撞
				if((ball.y_pos+Ball.r/2>=brick[i][j].y_pos)&&(ball.y_pos<=brick[i][j].y_pos+Brick.height)){
					if(((int)(ball.x_pos/10)*10+Ball.r==brick[i][j].x_pos+Brick.width)||((int)(ball.x_pos/10+1)*10+Ball.r==brick[i][j].x_pos)){
						brick[i][j].count++;
						if(!ball.GetThough())//如果不是穿透球
							brick[i][j].MinusDisplay();
						else brick[i][j].SetDisplay(0);
						if ((brick[i][j].tool!=0)&&(!brick[i][j].IsDisplay())){
							Prop prop=new Prop(brick[i][j].tool,brick[i][j].x_pos,brick[i][j].y_pos+Brick.height);
							prop.setSize(getPreferredSize());
							getContentPane().add(prop);
							tools.add(prop);
							this.validate();
						}
						if(!ball.GetThough()){//如果不是穿透球
						dxb=-dxb;
						if (!brick[i][j].IsDisplay())//如果是铁块
							crackvoice.knock();
						else 
							crackvoice.back();
						}
					}
				}
				}
				if (brick[i][j].count!=0){
					brick[i][j].SetCracked();
					brick[i][j].count=0;
				}
				else brick[i][j].SetUnCracked();
				if (brick[i][j].IsDisplay()){//砖块运动
					if (brick[i][j].speed!=0){//如果运动
						if ((brick[i][j].x_pos+brick[i][j].speed>0)&&(brick[i][j].x_pos+brick[i][j].speed<420))
							brick[i][j].ChangeX_pos(brick[i][j].speed);
						else {
							brick[i][j].speed=-brick[i][j].speed;
							brick[i][j].ChangeX_pos(brick[i][j].speed);
						}
					}
				}
			}
		}
		if (!isstop) {//如果不处于暂停状态
		ball.x_pos+= dxb;//球的移动
		ball.y_pos+= dyb;
		//平板移动
		if (!clockchange){
			double angle=face.getAngle();
			if (angle>=85) angle=85;
			else if (angle<=61) angle=61;
			//double pos=face.getAngle()<=90?360-(90-face.getAngle())*12:360;
			paddle.SetPaddle((int)(15*(angle-61)),paddle.y_pos);
		}
		else{
			double angle=face.getAngle();
			if (angle>=85) angle=85;
			else if (angle<=70) angle=70;
			//double pos=face.getAngle()>=90?(face.getAngle()-90)*12:0;
			//paddle.SetPaddle(face.getAngle()*12,paddle.y_pos);
			paddle.SetPaddle((int)((360-(angle-70)*24)),paddle.y_pos);
		}
		Iterator<Prop> e=tools.iterator();//检查道具情况
		while (e.hasNext()){
			Prop prop=e.next();
			prop.SetProp(prop.x_pos,prop.y_pos+10);
			if (prop.y_pos>h) {
				getContentPane().remove(prop);
				e.remove();
			}
			else{
				if (((prop.x_pos)>=paddle.x_pos )&&((prop.x_pos) < (paddle.x_pos + Paddle.width))) {
					if ((prop.y_pos+10+Prop.width/2)>=paddle.y_pos){//得到道具
						crackvoice.get();
						switch(prop.ID){
						case 1:if ((Math.abs(dxb)<=15)&&(Math.abs(dyb)<=15)) dxb=1.5*dxb;dyb=1.5*dyb;
						break;
						case 2:ball.SetThrough(true);
						break;
						case 3:Paddle.height=(int)(1.2*Paddle.height);Paddle.width=(int)(1.2*Paddle.width);
						   	   paddle.SetPaddle(paddle.x_pos,h-Paddle.height);
						break;
						default:Matress matr=new Matress();
						matr.setSize(getPreferredSize());
						getContentPane().add(matr);
						mat.add(matr);
						this.validate();
					}
						getContentPane().remove(prop);
						e.remove();
				}
			}
			}
		}
		}
		repaint();
		if (this.isHalfComplete()&&(!clockchange)){
			JOptionPane.showMessageDialog(null,"请改变方向继续活动颈部","请改变方向",JOptionPane.INFORMATION_MESSAGE);
			paddle.SetPaddle(0,paddle.y_pos);
			clockchange=true;
			face.ClockChange();
		}
		if (this.isComplete()){
			JOptionPane.showMessageDialog(null,"You Win","游戏结束",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			exit=true;
			time2=System.currentTimeMillis();
			try {
				new WelcomeFrame(username,usertime,date,time2-time1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			Thread.sleep(delay);
		} catch (InterruptedException ex) {
		}
		}
	}
	/*public static void main(String args[]) {
		new Game();
	}*/
}
