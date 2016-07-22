
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TwitterTest {

	 public static void main(String args[]) throws Exception{
		 TwitterTest twitterTest = new TwitterTest();
		// twitterTest.queryTwitter();
		 TwitterTest.streamTwitter();
	 }
	 
	 
	 public static void queryTwitter () throws TwitterException {
		 ConfigurationBuilder cb = new ConfigurationBuilder();

		 TwitterFactory tf = new TwitterFactory(cb.build());
		 Twitter twitter = tf.getInstance();
		 
//		 Twitter twitter = tf.getInstance();
		 /*
		  Twitter twitter = tf.getSingleton();
		    Status status = twitter.updateStatus("Prueba");
		    System.out.println("Successfully updated the status to [" + status.getText() + "].");
	 */
	 
	 // Twitter twitter = TwitterFactory.getSingleton();
	    Query query = new Query("pokemon nintendo");
	    query.setCount(10);
	    query.setGeoCode(new GeoLocation(19.3691511,-99.1406925), 10.0,
	    		Query.KILOMETERS);
	    QueryResult result = twitter.search(query);
	    
	    System.out.println( result.getCount());
	    
	    for (Status status : result.getTweets()) {
	        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	        System.out.println(status.toString());
	    }
	 }
	 
	 
	 public static void streamTwitter() throws TwitterException{
	        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	        StatusListener listener = new StatusListener() {
	            
	        	
	        	
	        	
	        	
	        	@Override
	            public void onStatus(Status status) {
	                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
	               
	                try {

		    			String content = "|twitt| " +status.getText();

		    			File file = new File("C:/streaming/filename.txt");

		    			// if file doesnt exists, then create it
		    			if (!file.exists()) {
		    				file.createNewFile();
		    			}

		    			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		    			BufferedWriter bw = new BufferedWriter(fw);
		    			
		    			bw.write(System.getProperty("line.separator"));
		    			bw.write(content);
		    			bw.close();

		    			System.out.println("Done");

		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		            
	                
	                
	            }
	        	
	            @Override
	            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	            }

	            @Override
	            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	                //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	            }

	            @Override
	            public void onScrubGeo(long userId, long upToStatusId) {
	               // System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	            }

	            @Override
	            public void onStallWarning(StallWarning warning) {
	                //System.out.println("Got stall warning:" + warning);
	            }

	            @Override
	            public void onException(Exception ex) {
	                ex.printStackTrace();
	            }
	            
	           
	            
	        };
	        
	        twitterStream.addListener(listener);
	        twitterStream.sample();
		}
	 
}
