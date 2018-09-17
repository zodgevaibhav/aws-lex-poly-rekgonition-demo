package face_recognition.colltions_use;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;

import common.CredentialProvider;

public class CreateColletion {

	public static void main(String[] args) throws Exception {


	       
	       AmazonRekognition rekognitionClient = AmazonRekognitionClient.builder()
	    		   .withCredentials(new CredentialProvider())
	    		   .withRegion(Regions.fromName("us-west-2"))
	    		   .build();

	      
	      String collectionId = "Vaibhav-Zodge"; //CollectionArn : aws:rekognition:us-west-2:378940023356:collection/Vaibhav-Zodge
	            System.out.println("Creating collection: " +
	         collectionId );
	            
	        CreateCollectionRequest request = new CreateCollectionRequest()
	                    .withCollectionId(collectionId);
	           
	      CreateCollectionResult createCollectionResult = rekognitionClient.createCollection(request); 
	      System.out.println("CollectionArn : " +
	         createCollectionResult.getCollectionArn());
	      System.out.println("Status code : " +
	         createCollectionResult.getStatusCode().toString());

	   } 
	
}
