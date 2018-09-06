package tina_bot_support;


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
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class TinaSpeak {

	private final AmazonPollyClient tina;
	private final Voice voice;

	public TinaSpeak(Region region) {

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
		
		tina = new AmazonPollyClient(credentials, new ClientConfiguration());
				
		DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

		DescribeVoicesResult describeVoicesResult = tina.describeVoices(describeVoicesRequest);
		voice = describeVoicesResult.getVoices().get(32); //sally voice is at array index 32
	}

	public InputStream synthesize(String text, OutputFormat format) throws IOException {
		SynthesizeSpeechRequest synthReq = 
		new SynthesizeSpeechRequest().withText(text).withVoiceId(voice.getId())
				.withOutputFormat(format);
		SynthesizeSpeechResult synthRes = tina.synthesizeSpeech(synthReq);

		return synthRes.getAudioStream();
	}

	public static void tinaSpeak(String stringToSpeak) {
		//create the test class
		TinaSpeak helloWorld = new TinaSpeak(Region.getRegion(Regions.US_EAST_1));
		//get the audio stream
		InputStream speechStream;
		try {
			speechStream = helloWorld.synthesize(stringToSpeak, OutputFormat.Mp3);

		//create an MP3 player
		AdvancedPlayer player;

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
		
		
		// play it!
		player.play();

		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
} 