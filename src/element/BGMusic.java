package element;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

public class BGMusic {
	AudioClip m1;
	AudioClip m2;
	String s0="material\\1.wav";//文件名
	String s1="material\\2.wav";
	boolean flag=false;
	boolean stop=false;
	public BGMusic(){//播放音乐
		try { 
			File music = new File(s0); 
			m1 = Applet.newAudioClip(music.toURL());
			music = new File(s1); 
			m2 = Applet.newAudioClip(music.toURL());
			} 
		catch(Exception e) { 
			e.printStackTrace(); 
			} 
		}
	public void MusicStart(){
		if (!flag) m1.loop();
		else m2.loop();
		stop=false;
	}
	public void MusicChange(){
		if (!stop){
		if (!flag){
			m1.stop();
			m2.loop();
		}
		else {
			m2.stop();
			m1.loop();
		}
		flag=!flag;
		}
	}
	public void MusicStop(){
		if (!flag)
			m1.stop();
		else m2.stop();
		stop=true;
		}	
	}
