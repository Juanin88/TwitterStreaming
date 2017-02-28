import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utiles {

	public static void obtienePalabras(String cadena) throws ClassNotFoundException, SQLException{
		 
		Connection c = null;
		// Statement stmt = null;

		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/social_network", "postgres",
				"root");
		c.setAutoCommit(true);

		String sql = "INSERT INTO palabra_twitter("
				+ "palabra)"
				+ " VALUES (?);";
	
		String[] palabra = cadena.split(" ");
		
		PreparedStatement stmt = null;
		stmt = c.prepareStatement(sql);
		
		for (String elemento: palabra){
			//System.out.println(elemento);
			if (elemento.length()>3){
			stmt.setString(1, elemento );
			
			stmt.executeUpdate();
			}
			
			
		}
		
		//System.out.println(sql.toString());
		

		c.close();
		
	}
}
