package model;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent; 

public class StartButton extends JButton{
	public StartButton(){
		ImageIcon img=new ImageIcon("material\\button2.png");
		setSize(img.getImage().getWidth(null),img.getImage().getHeight(null));
		setIcon(img);
		setMargin(new Insets(0,0,0,0));
		setIconTextGap(0);
		setBorderPainted(false);
		setBorder(null);
		setText(null);
		setOpaque(false);
		setContentAreaFilled(false); //…Ë÷√∞¥≈•Õ∏√˜
		addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseEntered(MouseEvent e) {  
            	ImageIcon img=new ImageIcon("material\\button.png");
                setIcon(img);
                setSize(img.getImage().getWidth(null),img.getImage().getHeight(null));
            	repaint();  
            }  
            @Override  
            public void mouseExited(MouseEvent e) {  
            	ImageIcon img=new ImageIcon("material\\button2.png");
                setIcon(img);
                setSize(img.getImage().getWidth(null),img.getImage().getHeight(null));
            	repaint();  
            }  
		});
	}
}
