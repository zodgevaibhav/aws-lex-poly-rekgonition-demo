package face_recognition;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;

import common.CredentialProvider;
import tina_bot_support.CredentialFactory;

public class CompareFaces {

	 public static void main(String[] args) throws Exception{
	       Float similarityThreshold = 70F;
	       String sourceImage = "E:\\Vaibhav\\Study\\AWS\\Rekognition\\prashanji.jpg";
	       String targetImage = "E:\\Vaibhav\\Study\\AWS\\Rekognition\\prashanji.jpg";
	       ByteBuffer sourceImageBytes=null;
	       ByteBuffer targetImageBytes=null;
	       
	       AmazonRekognition rekognitionClient = AmazonRekognitionClient.builder()
	    		   .withCredentials(new CredentialProvider())
	    		   .withRegion(Regions.fromName("us-west-2"))
	    		   .build();

	       //Load source and target images and create input parameters
	       try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
	          sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	       }
	       catch(Exception e)
	       {
	           System.out.println("Failed to load source image " + sourceImage);
	           System.exit(1);
	       }
	       try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
	           targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	       }
	       catch(Exception e)
	       {
	           System.out.println("Failed to load target images: " + targetImage);
	           System.exit(1);
	       }

	       Image source=new Image()
	            .withBytes(sourceImageBytes);
	       Image target=new Image()
	            .withBytes(targetImageBytes);

	       CompareFacesRequest request = new CompareFacesRequest()
	               .withSourceImage(source)
	               .withTargetImage(target)
	               .withSimilarityThreshold(similarityThreshold);

	       // Call operation
	       CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);


	       // Display results
	       List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
	       for (CompareFacesMatch match: faceDetails){
	         ComparedFace face= match.getFace();
	         BoundingBox position = face.getBoundingBox();
	         System.out.println("Face at " + position.getLeft().toString()
	               + " " + position.getTop()
	               + " matches with " + face.getConfidence().toString()
	               + "% confidence.");

	       }
	       List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

	       System.out.println("There was " + uncompared.size()
	            + " face(s) that did not match");
	       System.out.println("Source image rotation: " + compareFacesResult.getSourceImageOrientationCorrection());
	       System.out.println("target image rotation: " + compareFacesResult.getTargetImageOrientationCorrection());
	   }
	
	
}
