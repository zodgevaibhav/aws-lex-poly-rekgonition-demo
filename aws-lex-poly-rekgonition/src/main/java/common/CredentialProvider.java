package common;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

public class CredentialProvider implements AWSCredentialsProvider{

	AWSCredentials credentail = new AWSCredentials() {
		
		@Override
		public String getAWSSecretKey() {
			
			return System.getenv("AWS_SECRET_ACCESS_KEY");
		}
		
		@Override
		public String getAWSAccessKeyId() {
			return System.getenv("AWS_ACCESS_KEY_ID");
		}
	};
	
	@Override
	public AWSCredentials getCredentials() {
		return credentail;
	}

	@Override
	public void refresh() {

		
	}

}
