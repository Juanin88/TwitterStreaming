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
		
		int i = 0;
		do{
			for ( Ciudad ciudad : result ) {
				//sleep 5 seconds
				//System.out.println(ciudad.getCiudad());
				twitterMethods.queryTwitterByGeoLocation(ciudad.getLatitud(), ciudad.getLongitud(), ciudad.getRadio(), "", ciudad.getCiudad(), "en");
			}
			Thread.sleep(120000);
		} while (i==0);
		
		//twitterMethods.queryTwitterByGeoLocation(34.01940, -118.4108, 15, "", "Los Angeles");
		//twitterMethods.streamTwitter()

	}

}
