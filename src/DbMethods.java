
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Users;

public class DbMethods {
	
	public List<Ciudad> getGeoLocationCities() throws ClassNotFoundException, SQLException {

		Connection c = null;

		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/social_network", "postgres",
				"root");
		c.setAutoCommit(true);
		
		String sql = "SELECT id, ciudad, estado, latitud, longitud, censo_2010, censo_estimado_2015, radio  FROM ciudades ORDER BY censo_2010 DESC LIMIT 1";

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
			ciudad.setLongitud(resultSet.getDouble("longitud"));
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
	
	public void insertaUsuario (Users user, Connection c ) throws SQLException {
		
		String sql = "INSERT INTO twitter_user(id_user, screen_name, real_name, description, friends_count, followers_count, location)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement st = null;
		st = c.prepareStatement(sql);
		st.setLong(1, user.getId_user());
		st.setString(2,user.getScreen_name());
		st.setString(3,user.getReal_name());
		st.setString(4,user.getDescription());
		st.setInt(5,user.getFriends_count());
		st.setInt(6,user.getFollowers_count());
		st.setString(7,user.getLocation());

		System.out.println(st.toString());
		try {
			st.executeUpdate();
		} catch(Exception ex) {
			System.out.println(ex);
			   // Your exception handling code goes between these 
			   // curly braces, similar to the exception clause 
			   // in a PL/SQL block.
	}
		
		
	}
	
	
	
}
