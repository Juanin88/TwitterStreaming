
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbMethods {
	
	public List<Ciudad> getGeoLocationCities() throws ClassNotFoundException, SQLException {

		Connection c = null;

		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/social_network", "postgres",
				"root");
		c.setAutoCommit(true);
		
		String sql = "SELECT id, ciudad, estado, latitud, longitud, censo_2010, censo_estimado_2015, radio  FROM ciudades ORDER BY censo_2010 DESC LIMIT 20";

		//PreparedStatement stmt = null;
		//stmt = c.prepareStatement(sql);
		Statement st = c.createStatement();
	
		//System.out.println(sql.toString());
		ResultSet resultSet = st.executeQuery(sql);

		
		List<Ciudad> ciudades = new ArrayList<Ciudad>();
		
		
		while (resultSet.next())
		{
			Ciudad ciudad = new Ciudad();

			ciudad.setId(resultSet.getInt("id"));
			ciudad.setCiudad(resultSet.getString("ciudad"));
			ciudad.setEstado(resultSet.getString("estado"));
			ciudad.setLatitud(resultSet.getDouble("latitud"));
			ciudad.setLatitud(resultSet.getDouble("latitud"));
			ciudad.setCenso_2010(resultSet.getInt("censo_2010"));
			ciudad.setCenso_estimado_2015(resultSet.getInt("censo_estimado_2015"));
			ciudad.setRadio(resultSet.getDouble("radio"));
		    ciudades.add(ciudad);
		    
		}
		
		//System.out.println(ciudades.toString());
		
		resultSet.close();
		st.close();
		
		return ciudades;
		
	}
}
