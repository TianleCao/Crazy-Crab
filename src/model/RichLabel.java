package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class RichLabel extends JLabel{
	private int tracking;
	public RichLabel(String text,int tracking){
		super(text);
		this.tracking=tracking;
	}
	private int left_x,left_y,right_x,right_y;
	private Color left_color,right_color;
	public void setLeftShadow(int x,int y,Color color){
		left_x=x;
		left_y=y;
		left_color=color;
	}
	public void setRightShadow(int x,int y,Color color){
		right_x=x;
		right_y=y;
		right_color=color;
	}
	public Dimension getPreferredSize(){
		String text=getText();
		FontMetrics fm=this.getFontMetrics(getFont());
		int w=fm.stringWidth(text);
		w+=(text.length()-1)*tracking;
		w+=left_x+right_x;
		int h=fm.getHeight();
		h+=left_y+right_y;
		return new Dimension(w,h);
	}
	@Override
	public void paintComponent(Graphics g){
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		char[] words=getText().toCharArray();
		FontMetrics fm=this.getFontMetrics(getFont());
		int h=fm.getAscent();
		int x=0;
		for (int i=0;i<words.length;i++){
			char ch=words[i];
			int w=fm.charWidth(ch)+tracking;
			
			g.setColor(left_color);
			g.drawString(""+words[i], x-left_x, h-left_y);
			g.setColor(right_color);
			g.drawString(""+words[i], x+right_x,h+right_y);
			g.setColor(getForeground());
			g.drawString(""+words[i], x, h);
			x+=w;
		}
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
	}
	public static void main(String[] args){
		RichLabel AboutMe=new RichLabel("About Me",-30);
		AboutMe.setLeftShadow(1, 1, Color.WHITE);
		AboutMe.setRightShadow(1, 1, Color.WHITE);
		AboutMe.setForeground(Color.BLACK);
		AboutMe.setFont(AboutMe.getFont().deriveFont(140f));
		JFrame frame=new JFrame("RichLabel");
		frame.getContentPane().add(AboutMe);
		frame.pack();
		frame.setVisible(true);
	}
}
