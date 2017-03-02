import java.sql.ResultSet;
import java.util.List;

/**
 * 
 * @author Juan Garfias V�zquez.
 * @version 0.1
 *
 */
public class TwitterApp {

	@SuppressWarnings("static-access")
	public static void main(String args[]) throws Exception {
		TwitterMethods twitterMethods = new TwitterMethods();
		
		DbMethods dbMethods = new DbMethods();
		
		List<Ciudad> result = dbMethods.getGeoLocationCities();
		
		for ( Ciudad ciudad : result ) {
			//sleep 5 seconds
			//Thread.sleep(5000);
			
			twitterMethods.queryTwitterByGeoLocation(ciudad.getLatitud(), ciudad.getLongitud(), ciudad.getRadio(), "", ciudad.getCiudad(), "en");
		}
		
		//twitterMethods.queryTwitterByGeoLocation(34.01940, -118.4108, 15, "", "Los Angeles");
		//twitterMethods.streamTwitter()

	}

}
