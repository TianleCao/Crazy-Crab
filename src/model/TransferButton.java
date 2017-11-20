package model;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TransferButton extends JButton{
	private boolean login=true;
	public boolean isLogIn(){
		return login;
	}
	public TransferButton(){
		ImageIcon img=new ImageIcon("material\\LOGIN.png");
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
            public void mouseClicked(MouseEvent e) {  
            	ImageIcon img;
				if (login)
					img=new ImageIcon("material\\SIGNUP.png");
            	else 
            		img=new ImageIcon("material\\LOGIN.png");
				;
				login=!login;
                setIcon(img);
            	repaint();  
            }
		});
	}
}
