package face_recognition;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.AgeRange;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.CredentialProvider;

public class DetectFaces {

	public static void main(String[] args) throws Exception {

	      String photo = "prashanji.jpg";
	      String bucket = "vertex-team";

	      AmazonRekognition rekognitionClient = AmazonRekognitionClient.builder()
	    		   .withCredentials(new CredentialProvider())
	    		   .withRegion(Regions.fromName("us-west-2"))
	    		   .build();



	      DetectFacesRequest request = new DetectFacesRequest()
	         .withImage(new Image()
	            .withS3Object(new S3Object()
	               .withName(photo)
	               .withBucket(bucket)))
	         .withAttributes(Attribute.ALL);
	      // Replace Attribute.ALL with Attribute.DEFAULT to get default values.

	      try {
	         DetectFacesResult result = rekognitionClient.detectFaces(request);
	         List < FaceDetail > faceDetails = result.getFaceDetails();

	         for (FaceDetail face: faceDetails) {
	            if (request.getAttributes().contains("ALL")) {
	               AgeRange ageRange = face.getAgeRange();
	               System.out.println("The detected face is estimated to be between "
	                  + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
	                  + " years old.");
	               System.out.println("Here's the complete set of attributes:");
	            } else { // non-default attributes have null values.
	               System.out.println("Here's the default set of attributes:");
	            }

	            ObjectMapper objectMapper = new ObjectMapper();
	            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
	         }

	      } catch (AmazonRekognitionException e) {
	         e.printStackTrace();
	      }

	   }
	
}
