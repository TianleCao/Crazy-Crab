package element;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Matress extends JComponent{
	int x_pos=0,y_pos=535;
	int width=480,height=10;
	public Matress(){}
	@Override 
	public Dimension  getPreferredSize(){
		return new Dimension(width,height);
	}
	@Override 
	public void paintComponent(Graphics g){
		Image Img=new ImageIcon("material\\matress.png").getImage();
		g.drawImage(Img,x_pos,y_pos,width,height, null);	
		setOpaque(false);
		}
}
