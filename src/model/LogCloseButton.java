package model;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LogCloseButton extends JButton{
	public LogCloseButton(){
		ImageIcon img=new ImageIcon("material\\login_close.png");
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
		this.setRolloverIcon(new ImageIcon("material\\login_close_rover.png"));
	}

}
