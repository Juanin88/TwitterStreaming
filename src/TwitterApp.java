import java.sql.ResultSet;
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
		
		for ( Ciudad ciudad : result ) {
			System.out.println("Pasada -------------------------> "+ciudad.getCiudad());
			//System.out.println(ciudad.getCiudad());
			/*System.out.println(ciudad.getEstado());
			System.out.println(ciudad.getLatitud());
			System.out.println(ciudad.getLongitud());
			System.out.println(ciudad.getRadio());*/
			
			//sleep 5 seconds
			Thread.sleep(5000);
			
			twitterMethods.queryTwitterByGeoLocation(ciudad.getLatitud(), ciudad.getLongitud(), ciudad.getRadio(), "", ciudad.getCiudad());
		}
		
		//System.out.println(result.toString());

		/*
		for (int i = 0; i < loop; i++) {
			System.out.println("Pasada -------------------------> "+i);
			twitterMethods.queryTwitter();	
		}*/
		//
		
		
		//twitterMethods.streamTwitter()

	}
	
	


}
