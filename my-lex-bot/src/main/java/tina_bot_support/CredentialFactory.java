package tina_bot_support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.manywho.sdk.services.configuration.Configuration;

public class CredentialFactory implements Configuration {
	
		private Properties properties = new Properties();

	    private String region="us-west-2";

	    private String accessKey;

	    private String secretKey;
	    

		private String userName;
	    
	    private String botName;
	    
	    private String botAliase;
	    
	    public CredentialFactory()
	    {
	    	try {
				properties.load(new FileInputStream(new File("./src/main/resources/credentials.info")));
				setCredentials();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				exitApplication();
			} catch (IOException e) {
				e.printStackTrace();
				exitApplication();
			}
	    }

	    private void setCredentials()
	    {
	    	accessKey=properties.getProperty("accessKey");
	    	secretKey=properties.getProperty("secretKey");
	    	userName=properties.getProperty("userName");
	    	botName=properties.getProperty("botName");
	    	botAliase=properties.getProperty("botAliase");
	    }
	    
	    private void exitApplication()
	    {
	    	System.out.println("********* Unalbe to load properties file at location src/main/resources/credentials.info.");
	    	System.out.println("********* This file contains information about accessKey, secretKey, etc.... as KV pair.");
	    	System.out.println("********* Exiting...");
	    	System.exit(0);
	    }
	    
	    public String getRegion() {
	        return region;
	    }

	    public String getAccessKey() {
	        return accessKey;
	    }

	    public String getUserName() {
			return userName;
		}

		public String getBotName() {
			return botName;
		}

		public String getBotAliase() {
			return botAliase;
		}

	    public String getSecretKey() {
	        return secretKey;
	    }
	}