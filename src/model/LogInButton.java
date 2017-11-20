package model;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LogInButton extends JButton{
	public LogInButton(){
	ImageIcon img=new ImageIcon("material\\login_button.png");
	setSize(img.getImage().getWidth(null),img.getImage().getHeight(null));
	setIcon(img);
	setMargin(new Insets(0,0,0,0));
	setIconTextGap(0);
	setBorderPainted(false);
	setBorder(null);
	setText(null);
	setOpaque(false);
	setContentAreaFilled(false); //…Ë÷√∞¥≈•Õ∏√˜
	setRolloverEnabled(true);
	this.setPressedIcon(new ImageIcon("material\\login_button_pressed.png"));
	this.setRolloverIcon(new ImageIcon("material\\login_button_rover.png"));
	}
}
