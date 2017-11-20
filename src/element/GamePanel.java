package element;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class GamePanel extends JPanel{
	private Image Img;
	public GamePanel (){
		this.Img=new ImageIcon("material\\gamepanel.jpg").getImage();
		Dimension size=new Dimension(Img.getWidth(null),Img.getHeight(null));
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setLayout(null);
	}
	public void paintComponent(Graphics g){
		g.drawImage(Img, 0, 0,null);
	}
}
