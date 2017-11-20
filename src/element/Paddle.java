package element;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Paddle extends JComponent{
	public int x_pos;
	public int y_pos;
	public static int width=120;
	public static int height=55;
	public Paddle(double x,double y ){
		x_pos=(int)x;
		y_pos=(int)y;
	}
	public void SetPaddle(int x,int y){
		x_pos=x;
		y_pos=y;
	}
	@Override
	public void paintComponent(Graphics g){
		Image Img=new ImageIcon("material\\crab.png").getImage();
		g.drawImage(Img,x_pos,y_pos,width,height, null);	
		setOpaque(false);
		}
	public Dimension getPreferredSize(){
		return new Dimension(width,height);
	}
}
