package face_recognition.colltions_use;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.S3Object;

import common.CredentialProvider;

public class AddFaceToCollection {
	public static final String collectionId = "MyCollection";
	   public static final String bucket = "vertex-team";
	   public static final String photo = "prashanji.jpg"; //Face ID - 95c9aa98-01d9-4297-89d2-f699f57618de
	   													   //Faace ID - 5ebdd02f-1199-4748-bf58-2272d7b58164
	   

	   public static void main(String[] args) throws Exception {

	       AmazonRekognition rekognitionClient = AmazonRekognitionClient.builder()
	    		   .withCredentials(new CredentialProvider())
	    		   .withRegion(Regions.fromName("us-west-2"))
	    		   .build();

	         
	      Image image=new Image()  //To use local image data refer SearchImageInCollectionUsingImageByteData class
	              .withS3Object(new S3Object()
	                      .withBucket(bucket)
	                      .withName(photo));
	      
	      
	      
	      IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
	              .withImage(image)
	              .withCollectionId("Vaibhav-Zodge")
	              .withExternalImageId(photo)
	              .withDetectionAttributes("ALL");
	      
	      IndexFacesResult indexFacesResult=rekognitionClient.indexFaces(indexFacesRequest);
	      
	     
	      System.out.println(photo + " added");
	      List < FaceRecord > faceRecords = indexFacesResult.getFaceRecords();
	      for (FaceRecord faceRecord: faceRecords) {
	         System.out.println("Face detected: Faceid is " +
	            faceRecord.getFace().getFaceId());
	      }
	   } 
}
