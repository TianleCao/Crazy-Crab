package element;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Prop extends JComponent{
	public int ID;
	private Image img;
	public int x_pos;
	public int y_pos;
	static public int width=30;
	public Prop(int ID,int x,int y){
		this.ID=ID;
		switch(ID){
			case 1:img=new ImageIcon("material\\Double.jpg").getImage();
			break;
			case 2:img=new ImageIcon("material\\FireBall.jpg").getImage();
			break;
			case 3:img=new ImageIcon("material\\Extended.jpg").getImage();
			break;
			case 4:img=new ImageIcon("material\\Hinder.jpg").getImage();
			break;
			default:img=null;
		}
		x_pos=x;
		y_pos=y;
	}
	public void SetProp(int x,int y){
		x_pos=x;
		y_pos=y;
	}
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(img,x_pos,y_pos,width,width, null);	
		setOpaque(false);
		}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(width,width);
	}
}
