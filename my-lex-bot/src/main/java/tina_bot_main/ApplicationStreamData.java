package tina_bot_main;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import com.amazonaws.services.lexruntime.model.PostContentRequest;
import com.amazonaws.services.lexruntime.model.PostContentResult;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import tina_bot_support.AmazonLexRunTimeFactory;
import tina_bot_support.CredentialFactory;
import tina_bot_support.TinaSpeak;



public class ApplicationStreamData {
	
	public static void main(String[] args) {
		CredentialFactory credentials = new CredentialFactory();
		
		AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

		DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
		DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);  // for play back

		try {
			TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetLine.open(format);
			targetLine.start();
			
			SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
			sourceLine.open(format);
			sourceLine.start();

			int numBytesRead;
			byte[] targetData = new byte[targetLine.getBufferSize()*2];
			
			//byte[] targetData = new byte[targetLine.getBufferSize()/ 5];  //Original code
			
		//	while(true) {
				System.out.println("Recording...");
				numBytesRead = targetLine.read(targetData, 0, targetData.length);

				//if (numBytesRead == -1)	break; //enable if kept in loop to break while loop
				
				System.out.println("Read done....");
				
				//************* Play received bytes from source (mic) *******
				//sourceLine.write(targetData, 0, numBytesRead);

				//***********************************************************
				
				
				//************** If want to write to file ********************
				//	AudioSystem.write(new AudioInputStream(new ByteInputStream(targetData, targetData.length), format, targetData.length), AudioFileFormat.Type.WAVE, new File("./src/main/resources/speeches/RecordAudio1.wav"));
				//************************************************************
				
			
				//**************** Create input stream from received targetData*********
				InputStream is = new AudioInputStream(new ByteInputStream(targetData, targetData.length), format, targetData.length);
				//************************************************************************
				
				
				//************************ Send Request to Lex ************************
				PostContentResult res = AmazonLexRunTimeFactory.create(credentials).postContent(
						new PostContentRequest()
								.withBotAlias(credentials.getBotAliase())
								.withBotName(credentials.getBotName())
								.withUserId(credentials.getUserName())
								.withInputStream(is)
								.withAccept("audio/pcm")
								.withContentType("audio/x-l16")
						);
				System.out.println("Audio - " + res.getMessage());
				//************************************************************************
				
				//***************** Ask Polly to speak received message *****************
					TinaSpeak.tinaSpeak(res.getMessage()); 
				//************************************************************************
			
	//		} //end while loop
			
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
	
	static void returnSum(byte[] td)
	{
		
		for(byte b:td)
			System.out.print(b +" ");
		System.out.println();
	}

}
