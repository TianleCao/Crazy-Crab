package tester;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import model.InfiniteProgressPanel;

public class ProgressTester {
	public static void main(String[] args){
		JFrame frame=new JFrame();
		frame.setSize(500, 500);
		InfiniteProgressPanel glassPane=new InfiniteProgressPanel("Welcome");
		Dimension dimension =frame.getSize();
		glassPane.setBounds(100, 100,(dimension.width), (dimension.height));
		//glassPane.setBounds(100,100,50,50);
		frame.setGlassPane(glassPane);
		glassPane.start();//开始动画加载效果	
			frame.setVisible(true);
	}
}
