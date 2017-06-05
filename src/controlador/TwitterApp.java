package controlador;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import modelos.Ciudad;
import modelos.Palabras;

/**
 * 
 * @author Juan Garfias V�zquez.
 * @version 0.1
 *
 */
public class TwitterApp {

	@SuppressWarnings("static-access")
	public static void main(String args[]) throws Exception {
		int i = 0;
		do{
			try {
				executeStreaming();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				System.gc(); 
			}
		} while (i==0);
		
	}

	@SuppressWarnings("static-access")
	public static void executeStreaming () throws Exception{

		TwitterMethods twitterMethods = new TwitterMethods();
		DbMethods dbMethods = new DbMethods();
		
		List<Ciudad> result = dbMethods.getGeoLocationCities();
		List<Palabras> palabras = dbMethods.getWordList();
		
		Properties props = new Properties();
		FileInputStream fis = null;
		fis = new FileInputStream("db.properties");
		props.load(fis);

		// load the Driver Class
		Class.forName(props.getProperty("driver"));
		
		// path C:/streaming/logTwitterStreaming.log
		// 		/home/ubuntu/log/logTwitterStreaming.log

		File file = new File(props.getProperty("path"));

		do{

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			Date date = new Date();

			for ( Ciudad ciudad : result ) {
				//sleep 5 seconds
				
				try {
					twitterMethods.queryTwitterByGeoLocation(
							ciudad.getLatitud(),
							ciudad.getLongitud(),
							ciudad.getRadio(),
							"",
							ciudad.getCiudad(),
							"en",
							palabras);
					System.out.println(ciudad.getCiudad());
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Excedido el limite de consultas, esperando...");
					System.out.println(e);
					bw.write(System.getProperty("line.separator"));
					bw.write("Consultas excedidas : "+date.toLocaleString());
					Thread.sleep(300000);
				}
			}
			
			bw.write(System.getProperty("line.separator"));
			bw.write(date.toLocaleString());
			bw.close();

			System.out.println(date.toLocaleString());
			Thread.sleep(60000);
		} while (true);
	}

	
}
