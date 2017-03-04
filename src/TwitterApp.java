import java.util.List;

/**
 * 
 * @author Juan Garfias Vázquez.
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
				twitterMethods.queryTwitterByGeoLocation(ciudad.getLatitud(), ciudad.getLongitud(), ciudad.getRadio(), "", ciudad.getCiudad(), "en");
				System.out.println(ciudad.getCiudad());				
			}
			Thread.sleep(120000);
		} while (i==0);

	}

}
