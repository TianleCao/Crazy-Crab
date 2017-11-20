package element;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class CrackVoice {
	AudioClip m1;
	AudioClip m2;
	AudioClip m3;
	public CrackVoice(){
		try {
			File music = new File("material\\knock.wav"); 
			m1 = Applet.newAudioClip(music.toURL());
			music = new File("material\\back.wav"); 
			m2 = Applet.newAudioClip(music.toURL());
			music = new File("material\\get.wav"); 
			m3 = Applet.newAudioClip(music.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public void knock(){//≤•∑≈“Ù–ß
		m1.play(); 
		}
	public void back(){
		m2.play();
	}
	public void get(){
		m3.play();
	}
}
