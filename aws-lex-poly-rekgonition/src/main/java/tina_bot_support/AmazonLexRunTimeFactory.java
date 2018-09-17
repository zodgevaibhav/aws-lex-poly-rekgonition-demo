package tina_bot_support;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;

public class AmazonLexRunTimeFactory {

	 public static AmazonLexRuntime create(CredentialFactory configuration) {
	        AWSCredentials credentials = new BasicAWSCredentials(
	                configuration.getAccessKey(),
	                configuration.getSecretKey()
	        );

	        return AmazonLexRuntimeClientBuilder.standard()
	                .withRegion(Regions.fromName(configuration.getRegion()))
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .build();
	    }
	
}
