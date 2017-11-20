package element;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;


public class Brick extends JComponent{
	public int x_pos=0,y_pos=0;
	public static int width=60;
	public static int height=30;
	public int tool=0;
	public int count=0;
	int display=1;
	boolean cracked=false;
	public int speed=0;
	public Brick(double x,double y){
		x_pos=(int)x;
		y_pos=(int)y;
		int t=(int)(Math.random()*25);//随机产生物品
		if(t<=1){
			this.tool=1;//加球速
		}
		if(t>1&&t<=2){
			this.tool=2;//可穿透
		}
		if(t>2&&t<=3){
			this.tool=3;//加板长
		}
		if(t>3&&t<=4){
			this.tool=4;//加垫子
		}
		//设置砖块硬度
		t=(int)(Math.random()*25);
		if ((t>18)&&(t<25)) display=(int)((t-17)/2);		
	}
	
	public void SetDisplay(int t){
		display=t;
	}
	public boolean IsDisplay(){
		if (display>0) return true;
		else return false;
	}
	public void MinusDisplay(){
		display--;
	}
	public Dimension getPreferredSize(){
		return new Dimension(width,height);
	}
	public void SetSpeed(){
		speed=10;
	}
	public void ChangeX_pos(int speed){
		x_pos+=speed;
	}
	public void SetCracked(){
		cracked=true;
	}
	public void SetUnCracked(){
		cracked=false;
	}
	public boolean IsCracked(){
		return cracked;
	}
	@Override
	public void paintComponent(Graphics g){
		Image Img;
		switch(display){
		case 1:Img=new ImageIcon("material\\brick3.png").getImage();
			 g.drawImage(Img,x_pos,y_pos,width,height, null);	
			 break;
		case 2:Img=new ImageIcon("material\\brick2.png").getImage();
		 	 g.drawImage(Img,x_pos,y_pos,width,height, null);	
		 	 break;
		case 3:Img=new ImageIcon("material\\brick1.png").getImage();
		 	 g.drawImage(Img,x_pos,y_pos,width,height, null);	
		 	 break;
		 default:
		}
	}
}
