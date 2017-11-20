package element;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MButton extends JButton{
	int width=20,height=20;
	int flag=0;
	public MButton(){
		ImageIcon img=new ImageIcon("material\\MButton2.png");
		setSize(width,height);
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
				if (flag==0)
					img=new ImageIcon("D:\\Program Files\\eclipse\\Workspace\\Crazy Crab\\MButton.png");
            	else 
            		img=new ImageIcon("D:\\Program Files\\eclipse\\Workspace\\Crazy Crab\\MButton2.png");
				flag=1-flag;
                setIcon(img);
                setSize(width,height);
            	repaint();  
            }
		});
		//this.setSelectedIcon(new ImageIcon("D:\\Program Files\\eclipse\\Workspace\\Crazy Crab\\SButton.png"));
	}
}
