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
	double dxb = -5, dyb = -6;//�������ٶ�
	static int delay= 100; // ˢ�¼��
	static int xnum=7;		//ש������
	static int ynum=7;		//ש������
	static int h=545;		//����ʵ�ʸ߶�
	Thread t = new Thread(this);
	Thread f;
	public  Brick brick[][]; // ש��
	public  Ball ball; // ��
	public  Paddle paddle; // ���
	GamePanel panel; // ����
	ArrayList<Prop> tools;
	ArrayList<Matress> mat;
	SButton sbutton;
	MButton mbutton;
	BButton bbutton;
	CButton cbutton;
	boolean isstop=false;	//�Ƿ���ͣ
	boolean ismusic=true;	//�Ƿ������
	BGMusic music;
	CrackVoice crackvoice;
	FaceRecognizer face;
	boolean clockchange=false;
	boolean exit=false;
	int[][] map=new int[][]{{1,0,0,0,1,1,1},{0,1,0,0,0,1,1},{0,1,1,0,0,1,1},{0,1,1,1,0,1,1},{0,1,1,0,0,1,1},{0,1,0,0,0,1,1},{1,0,0,0,1,1,1}};
	int sum=27; //ש������
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
		glasspane.start();//��ʼ��������Ч��	
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
		//paddle=new Paddle(getWidth()/2-paddle.width/2,getHeight()-Paddle.height);//��ľ�����ڽ���׶�����
		Paddle.width=120;
		Paddle.height=55;
		paddle=new Paddle(360,h-Paddle.height);
		brick=new Brick[xnum][ynum];
		for (int j = 0; j < ynum; j++) {//����ש��
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
		//���ð���
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
                // A �� ���ͷ ���������ƶ�
                if (!isstop){
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    paddle.SetPaddle(paddle.x_pos-speed,paddle.y_pos);
                    repaint();
                }
                // D �� �Ҽ�ͷ ���������ƶ�
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
		 for (int j = 0; j < brick[0].length; j++) { // �ж���ש�����ײ,ש����˶�
			for (int i = 0; i < brick.length; i++) {
				if (brick[i][j].IsDisplay()) return false;
			}
		 }
		 return true;
	}
	public boolean isHalfComplete(){
		int count=0;
		 for (int j = 0; j < brick[0].length; j++) { // �ж���ש�����ײ,ש����˶�
			for (int i = 0; i < brick.length; i++) {
				if (brick[i][j].IsDisplay()) count++;
			}
		 }
		 if (count<(int)(sum/2)) return true;
		 else return false;
	}
	public void run(){
		while (!exit){
		if ((ball.x_pos)<=2 || (ball.x_pos +1.5*Ball.r >= getWidth()-8)) { // �������ұ߽�
			dxb = -dxb;//�߿򷴵�
		}
		if (ball.y_pos <=2) { // �����ϱ߽�
			dyb = -dyb;
		}
		if (ball.y_pos+Ball.r >=h) {//������ڵױ�
			Iterator<Matress> e=mat.iterator();
			if (e.hasNext()){
				getContentPane().remove(e.next());
				e.remove();
				this.validate();
				dyb = -dyb;//�����Ϸ���
			}
			else
			if (ball.x_pos+ Ball.r < paddle.x_pos|| ball.x_pos > paddle.x_pos + Paddle.width) { // û�����ڰ�����
				int h=JOptionPane.showConfirmDialog(null, "����һ�飿","You die", JOptionPane.YES_NO_OPTION);//�����Ƿ�����ѡ���
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
					if ((((int)(ball.y_pos/10)*10+Ball.r)==paddle.y_pos)&&(dyb>0)){//������ײ
						dyb = -dyb;//ľ���Ϸ���
						crackvoice.knock();
					}
				}
				if (((ball.y_pos+ball.r/2)>=paddle.y_pos )&&((ball.y_pos+ball.r/2) < (paddle.y_pos + Paddle.width))) {
					if ((((int)(ball.x_pos/10)*10+Ball.r)==paddle.x_pos)&&(((int)(ball.x_pos/10+1)*10+Ball.r/2)<=(paddle.x_pos+paddle.width))){//������ײ
						dxb=-dxb;
						crackvoice.knock();
					}
				}
			}

		for (int j = 0; j < brick[0].length; j++) { // �ж���ש�����ײ,ש����˶�
			for (int i = 0; i < brick.length; i++) {
				if ((brick[i][j].IsDisplay())&&(!brick[i][j].IsCracked())) {//������ײ
					if((ball.x_pos+Ball.r/2<=brick[i][j].x_pos+Brick.width)&&(ball.x_pos+Ball.r/2>=brick[i][j].x_pos)){
						if(((int)(ball.y_pos/10)*10+Ball.r==brick[i][j].y_pos)||((int)(ball.y_pos/10+1)*10==brick[i][j].y_pos+Brick.height)){
							brick[i][j].count++;
							if(!ball.GetThough())//������Ǵ�͸��
								brick[i][j].MinusDisplay();
							else brick[i][j].SetDisplay(0);
							if ((brick[i][j].tool!=0)&&(!brick[i][j].IsDisplay())){
								Prop prop=new Prop(brick[i][j].tool,brick[i][j].x_pos,brick[i][j].y_pos+Brick.height);
								prop.setSize(getPreferredSize());
								getContentPane().add(prop);
								tools.add(prop);
								this.validate();
							}
							if(!ball.GetThough()){//������Ǵ�͸��
							dyb=-dyb;
							if (!brick[i][j].IsDisplay())//���������
								crackvoice.knock();
							else 
								crackvoice.back();
							}
						}
					}
				}
				if ((brick[i][j].IsDisplay())&&(!brick[i][j].IsCracked())) {//������ײ
				if((ball.y_pos+Ball.r/2>=brick[i][j].y_pos)&&(ball.y_pos<=brick[i][j].y_pos+Brick.height)){
					if(((int)(ball.x_pos/10)*10+Ball.r==brick[i][j].x_pos+Brick.width)||((int)(ball.x_pos/10+1)*10+Ball.r==brick[i][j].x_pos)){
						brick[i][j].count++;
						if(!ball.GetThough())//������Ǵ�͸��
							brick[i][j].MinusDisplay();
						else brick[i][j].SetDisplay(0);
						if ((brick[i][j].tool!=0)&&(!brick[i][j].IsDisplay())){
							Prop prop=new Prop(brick[i][j].tool,brick[i][j].x_pos,brick[i][j].y_pos+Brick.height);
							prop.setSize(getPreferredSize());
							getContentPane().add(prop);
							tools.add(prop);
							this.validate();
						}
						if(!ball.GetThough()){//������Ǵ�͸��
						dxb=-dxb;
						if (!brick[i][j].IsDisplay())//���������
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
				if (brick[i][j].IsDisplay()){//ש���˶�
					if (brick[i][j].speed!=0){//����˶�
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
		if (!isstop) {//�����������ͣ״̬
		ball.x_pos+= dxb;//����ƶ�
		ball.y_pos+= dyb;
		//ƽ���ƶ�
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
		Iterator<Prop> e=tools.iterator();//���������
		while (e.hasNext()){
			Prop prop=e.next();
			prop.SetProp(prop.x_pos,prop.y_pos+10);
			if (prop.y_pos>h) {
				getContentPane().remove(prop);
				e.remove();
			}
			else{
				if (((prop.x_pos)>=paddle.x_pos )&&((prop.x_pos) < (paddle.x_pos + Paddle.width))) {
					if ((prop.y_pos+10+Prop.width/2)>=paddle.y_pos){//�õ�����
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
			JOptionPane.showMessageDialog(null,"��ı䷽����������","��ı䷽��",JOptionPane.INFORMATION_MESSAGE);
			paddle.SetPaddle(0,paddle.y_pos);
			clockchange=true;
			face.ClockChange();
		}
		if (this.isComplete()){
			JOptionPane.showMessageDialog(null,"You Win","��Ϸ����",JOptionPane.INFORMATION_MESSAGE);
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
