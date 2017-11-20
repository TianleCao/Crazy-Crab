package element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;


public class Ball extends JComponent{
	public int x_pos=0,y_pos=0;
	private Image img;
	public static int r=10;
	boolean through=false;
	public Ball (){
	}
	public Ball(double x,double y){
		x_pos=(int)x;
		y_pos=(int)y;
	}
	public void SetPosition(double x,double y){
		x_pos=(int)x;
		y_pos=(int)y;
	}
	public void SetThrough(boolean through){
		this.through=through;
	}
	public boolean GetThough(){
		return through;
	}
	public Dimension getPreferredSize(){
		return new Dimension(r,r);
	}
	@Override
	public void paintComponent(Graphics g){
		if (!through)
			img=new ImageIcon("material\\ball.png").getImage();
		else//´©Í¸Çò
			img=new ImageIcon("material\\fireball.png").getImage();
		g.drawImage(this.img,x_pos,y_pos,r,r,null);	
		setOpaque(false);
		this.setVisible(true);

	}
}
