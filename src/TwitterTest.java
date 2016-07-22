
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
public class TwitterTest {

	 public static void main(String args[]) throws Exception{
		 ConfigurationBuilder cb = new ConfigurationBuilder();

		 TwitterFactory tf = new TwitterFactory(cb.build());
//		 Twitter twitter = tf.getInstance();
		 
		  Twitter twitter = tf.getSingleton();
		    Status status = twitter.updateStatus("Prueba");
		    System.out.println("Successfully updated the status to [" + status.getText() + "].");
	 }
}
