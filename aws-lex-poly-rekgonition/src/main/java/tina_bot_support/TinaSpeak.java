package tina_bot_support;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SpeechMarkType;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;
import com.amazonaws.services.polly.model.VoiceId;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class TinaSpeak {

	private final AmazonPollyClient tina;
	private final Voice voice;

	public TinaSpeak(Region region) {

		//********* Create AWS Credentials *****************************
		AWSCredentials credentials = new AWSCredentials() {
		CredentialFactory appConfig = new CredentialFactory();
			public String getAWSSecretKey() {
				return appConfig.getSecretKey();
			}
			public String getAWSAccessKeyId() {
				// TODO Auto-generated method stub
				return appConfig.getAccessKey();
			}
		};
		//****************************************************************
		
		//***************** Create polly client **********************************
		tina = new AmazonPollyClient(credentials, new ClientConfiguration());
		//**********************************************************************
		
		//************* Get Available voices *******************************************************
		DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
		DescribeVoicesResult describeVoicesResult = tina.describeVoices(describeVoicesRequest);
		voice = describeVoicesResult.getVoices().get(32); //sally voice is at array index 32
		//******************************************************************************************
	}

	/**
	 * This function synthesize text and return the audio stream
	 * @param text (to get read)
	 * @param format (required output format)
	 * @return Audio Input Stram
	 * @throws IOException
	 * 
	 */
	public InputStream synthesizeForAudio(String text, OutputFormat format) throws IOException {
		SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()
				.withText(text)
				.withVoiceId(voice.getId())
				.withOutputFormat(format);
		
		SynthesizeSpeechResult synthRes = tina.synthesizeSpeech(synthReq);
		return synthRes.getAudioStream();
	}
	
	public InputStream synthesizeForSpeechMarks(String text, OutputFormat format) throws IOException {
		SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()
                .withOutputFormat(format)
                .withSpeechMarkTypes(SpeechMarkType.Word)//SpeechMarkType.Viseme, SpeechMarkType.Word)
                .withVoiceId(voice.getId())
                .withTextType("ssml")
                .withText(text);
		SynthesizeSpeechResult synthRes = tina.synthesizeSpeech(synthReq);
		return synthRes.getAudioStream();
	}

	public static void tinaSpeak(String stringToSpeak) {
		//create the test class
		TinaSpeak tinaSpeak = new TinaSpeak(Region.getRegion(Regions.US_EAST_1));
		//get the audio stream
		InputStream speechStream;
		try {
			speechStream = tinaSpeak.synthesizeForAudio(stringToSpeak, OutputFormat.Mp3);

		AdvancedPlayer player; //Create player object
				player = new AdvancedPlayer(speechStream,
					javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

		player.setPlayBackListener(new PlaybackListener() {
			@Override
			public void playbackStarted(PlaybackEvent evt) {
				System.out.println("Playback started");
			}
			
			@Override
			public void playbackFinished(PlaybackEvent evt) {
				System.out.println("Playback finished");
			}
		});
		
		player.play();  // Play the audio

		} catch (JavaLayerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void tinaGetSpeechMarks(String stringToSpeak) {

		TinaSpeak helloWorld = new TinaSpeak(Region.getRegion(Regions.US_EAST_1));

		InputStream speechStream;
		try {
			speechStream = helloWorld.synthesizeForSpeechMarks(stringToSpeak, OutputFormat.Json);
	
				FileOutputStream outputStream = new FileOutputStream(new File("./src/main/resources/speechMarks.txt"));
	            byte[] buffer = new byte[2 * 1024];
	            int readBytes;
	 
	                while ((readBytes = speechStream.read(buffer)) > 0) {
	                    outputStream.write(buffer, 0, readBytes);
	                }
	                outputStream.close();
	                System.out.println("*************** Speech mark successfull. Can be found in ./src/main/resources/speechMarks.txt file");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		//TinaSpeak.tinaSpeak("My name is tina, build by Vertex team");
		//TinaSpeak.tinaGetSpeechMarks("My name is tina, build by Vertex team");
		//TinaSpeak.tinaGetSpeechMarks("<speak><prosody volume=\"+20dB\">Mary had <break time=\"300ms\"/>a little <mark name=\"animal\"/>lamb</prosody></speak>");
	}
} 