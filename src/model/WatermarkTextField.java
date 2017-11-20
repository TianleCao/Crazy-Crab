package model;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.io.File;

public class WatermarkTextField extends JTextField{
	BufferedImage img;
	TexturePaint texture;
	public WatermarkTextField()throws IOException{
		super();
		img=ImageIO.read(new File("material\\greentextfield.png"));
		Rectangle rect=new Rectangle(0,0,img.getWidth(),img.getHeight());
		texture=new TexturePaint(img,rect);
		setOpaque(false);
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2=(Graphics2D)g;
		g2.setPaint(texture);
		float trans=(float)(0.7);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,trans));
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
	public static void main(String[] args) throws IOException{
		JFrame frame=new JFrame("Watermark TextField");
		WatermarkTextField tf=new WatermarkTextField();
		tf.setText("hello");
		frame.add(tf);
		frame.pack();
		frame.show();
	}

}
