package model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CrabPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image Img;
	public CrabPanel (){
		this.Img=new ImageIcon("material\\pangxie.jpg").getImage();
		Dimension size=new Dimension(Img.getWidth(null),Img.getHeight(null));
		//Dimension size=new Dimension(800,600);
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
