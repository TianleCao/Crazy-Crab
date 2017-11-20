package element;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BButton extends JButton{
	int width=20,height=20;
	public BButton(){
	ImageIcon img=new ImageIcon("material\\Back.png");
	setSize(width,height);
	setIcon(img);
	setMargin(new Insets(0,0,0,0));
	setIconTextGap(0);
	setBorderPainted(false);
	setBorder(null);
	setText(null);
	setOpaque(false);
	setContentAreaFilled(false); //…Ë÷√∞¥≈•Õ∏√˜
	}
}
