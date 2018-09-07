package tina_bot_main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.amazonaws.services.lexruntime.model.PostContentRequest;
import com.amazonaws.services.lexruntime.model.PostContentResult;

import tina_bot_support.AmazonLexRunTimeFactory;
import tina_bot_support.CredentialFactory;
import tina_bot_support.TinaSpeak;

public class ApplicationFiledAudioText {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		CredentialFactory credentials = new CredentialFactory();
	
	//************************************ Text input and Text output *****************************************
		/*PostTextResult result = AmazonLexRunTimeFactory.create(new ApplicationConfiguration()).postText(
                new PostTextRequest()
                        .withInputText("How are you") //welcome intent
						.withBotAlias(credentials.getBotAliase())
						.withBotName(credentials.getBotName())
						.withUserId(credentials.getUserName())                     
        );
		
		System.out.println("Text - "+result.getMessage());*/

	//************************************ Audio input and Audio output *****************************************
		
		//*********************** Sound Recording
			//JavaSoundRecorder.recordVoice();  //say Hi or how are you
			//FileInputStream as = new FileInputStream(new File("./src/main/resources/speeches/RecordAudio.wav"));
		
		//*********************** Recorded files
			FileInputStream as = new FileInputStream(new File("./src/main/resources/speeches/hello.wav"));
			//FileInputStream as = new FileInputStream(new File("./src/main/resources/speeches/howAreYou.wav"));
		
		//Post audio to amazon lex
		PostContentResult res = AmazonLexRunTimeFactory.create(credentials).postContent(
				new PostContentRequest()
						.withBotAlias(credentials.getBotAliase())
						.withBotName(credentials.getBotName())
						.withUserId(credentials.getUserName())
						.withInputStream(as)	
						.withAccept("audio/pcm")
						.withContentType("audio/x-l16")
				);
		//Print response message
		System.out.println("Audio - " + res.getMessage());

		//Play response message		
		TinaSpeak.tinaSpeak(res.getMessage()); 
	
	}	
}
