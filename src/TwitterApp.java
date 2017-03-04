import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
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
		
		File file = new File("C:/streaming/logTwitterStreaming.log");

		
		int i = 0;
		do{

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				i=1;
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			Date date = new Date();

			for ( Ciudad ciudad : result ) {
				//sleep 5 seconds
				
				try {
					twitterMethods.queryTwitterByGeoLocation(ciudad.getLatitud(), ciudad.getLongitud(), ciudad.getRadio(), "", ciudad.getCiudad(), "en");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Excedido el limite de consultas, esperando...");
					bw.write(System.getProperty("line.separator"));
					bw.write("Consultas excedidas : "+date.toLocaleString());
					Thread.sleep(300000);
				}
				
				System.out.println(ciudad.getCiudad());
			}
			
			bw.write(System.getProperty("line.separator"));
			bw.write(date.toLocaleString());
			bw.close();

			
			System.out.println(date.toLocaleString());
			Thread.sleep(60000);
		} while (i==0);

	}

}
