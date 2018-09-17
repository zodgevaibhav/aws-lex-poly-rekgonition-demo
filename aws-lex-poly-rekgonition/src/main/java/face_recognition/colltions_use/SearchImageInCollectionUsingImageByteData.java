package face_recognition.colltions_use;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.CredentialProvider;

public class SearchImageInCollectionUsingImageByteData {

	public static final String collectionId = "Vaibhav-Zodge";
    public static final String bucket = "vertex-team";
    public static final String localImage = "E:\\Vaibhav\\Study\\AWS\\Rekognition\\vz1.jpg";
      
    public static void main(String[] args) throws Exception {

    	InputStream inputStream = new FileInputStream(new File(localImage));
    	ByteBuffer sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    	
	      AmazonRekognition rekognitionClient = AmazonRekognitionClient.builder()
	    		   .withCredentials(new CredentialProvider())
	    		   .withRegion(Regions.fromName("us-west-2"))
	    		   .build();
        
      ObjectMapper objectMapper = new ObjectMapper();
      
       // Get an image object from S3 bucket.
      Image image=new Image()
    		  .withBytes(sourceImageBytes);
           
      // Search collection for faces similar to the largest face in the image.
      SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest()
              .withCollectionId(collectionId)
              .withImage(image)
              .withFaceMatchThreshold(70F)
              .withMaxFaces(2);
           
       SearchFacesByImageResult searchFacesByImageResult = 
               rekognitionClient.searchFacesByImage(searchFacesByImageRequest);

      List < FaceMatch > faceImageMatches = searchFacesByImageResult.getFaceMatches();
      for (FaceMatch face: faceImageMatches) {
          System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                  .writeValueAsString(face));
         System.out.println();
      }
   }

	
}
