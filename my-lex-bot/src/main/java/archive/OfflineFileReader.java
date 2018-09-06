package archive;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.media.sound.WaveFileReader;

public class OfflineFileReader {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {

		WaveFileReader wf = new WaveFileReader();
		//InputStream is = new InputStream
		AudioInputStream as =  wf.getAudioInputStream(new File("E:\\Vaibhav\\Study\\My Product\\OpenGL Project\\Recording.m4a"));
	
	}

}
