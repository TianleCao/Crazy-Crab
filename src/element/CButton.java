package element;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CButton extends JButton{
	int width=20,height=20;
	public CButton(){
		ImageIcon img=new ImageIcon("material\\CButton.png");
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
		//this.setSelectedIcon(new ImageIcon("D:\\Program Files\\eclipse\\Workspace\\Crazy Crab\\SButton.png"));
}
