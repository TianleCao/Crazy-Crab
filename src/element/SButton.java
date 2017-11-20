package element;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SButton extends JButton{
	int width=20,height=20;
	int flag=0;//标记按键状态
	public SButton(){
		ImageIcon img=new ImageIcon("material\\SButton2.png");
		setSize(width,height);
		setIcon(img);
		setMargin(new Insets(0,0,0,0));
		setIconTextGap(0);
		setBorderPainted(false);
		setBorder(null);
		setText(null);
		setOpaque(false);
		setContentAreaFilled(false); //设置按钮透明
		addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseClicked(MouseEvent e) { 
            	ImageIcon img;
				if (flag==0)
					img=new ImageIcon("material\\SButton.png");
            	else 
            		img=new ImageIcon("material\\SButton2.png");
				flag=1-flag;
                setIcon(img);
                setSize(width,height);
            	repaint();  
            }
		});
		//this.setSelectedIcon(new ImageIcon("material\\SButton.png"));
	}
}
