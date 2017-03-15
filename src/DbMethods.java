import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.FiltroPalabra;
import modelos.Hashtag;
import modelos.Palabras;
import modelos.Tweets;
import modelos.Users;

/**
 * 
 * @author Juan Garfias V·zquez
 *
 */
public class DbMethods {
	
	private String pattern;

	public DbMethods() {
		super();
		this.pattern = "[^a-zA-Z0-9 .,:;@#$&—Ò·ÈÌÛ˙¡…Õ”⁄%'¥]+";
	}

	public List<Ciudad> getGeoLocationCities() throws ClassNotFoundException, SQLException {

		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/social_network?user=dev&password=dev&useSSL=false&?useUnicode=true");

		c.setAutoCommit(true);
		
		String sql = "SELECT id, ciudad, estado, latitud, longitud, censo_2010, censo_estimado_2015, radio "
				+ "FROM ciudades ORDER BY censo_2010 DESC LIMIT 10";
		Statement st = c.createStatement();
	
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

		resultSet.close();
		st.close();
		
		return ciudades;
		
	}
	
	public List<Palabras> getWordList() throws ClassNotFoundException, SQLException {

		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/social_network?user=dev&password=dev&useSSL=false&?useUnicode=true");

		c.setAutoCommit(true);
		
		String sql = "SELECT twitter_filtro_palabras.id_twitter_filtro,"
				+ "twitter_filtro_palabras.palabra,"
				+ "twitter_filtro_palabras.id_filtro_palabra"
				+ " FROM social_network.twitter_filtro_palabras where id_twitter_filtro=1 AND activa=1";
		Statement st = c.createStatement();
	
		ResultSet resultSet = st.executeQuery(sql);

		List<Palabras> palabras = new ArrayList<Palabras>();
		
		
		while (resultSet.next())
		{
			Palabras palabra = new Palabras();
			
			palabra.setId_twitter_filtro(resultSet.getInt("id_filtro_palabra"));
			palabra.setPalabra(resultSet.getString("palabra"));
			palabra.setId(resultSet.getInt("id_twitter_filtro"));
			
			palabras.add(palabra);
		}

		resultSet.close();
		st.close();
		
		return palabras;
		
	}
	
	public void insertaUsuario (Users user, Connection c ) throws SQLException {
		
		String sql = "INSERT INTO twitter_user(id_user, screen_name, real_name, description, friends_count, followers_count, location)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement st = null;
		st = c.prepareStatement(sql);
		st.setLong(1, user.getId_user());
		st.setString(2,user.getScreen_name().replaceAll(this.pattern,"").trim());
		st.setString(3, user.getReal_name().replaceAll(this.pattern,"").trim());
		st.setString(4, user.getDescription().replaceAll(this.pattern,"").trim());
		st.setInt(5,user.getFriends_count());
		st.setInt(6,user.getFollowers_count());
		st.setString(7,user.getLocation());
		
		try {
			st.executeUpdate();
		} catch(Exception ex) {
			//System.out.println("User : " + ex);
			//System.out.println(user.toString());
			c.commit();
		}
	}
	

	public void insertaTweet (Tweets tweet, Connection c ) throws SQLException {
		
		String sql = "INSERT INTO twitter_tweets(id_tweet, tweet, id_user, created, ciudad, sentimiento)"
				+ " VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement st = null;
		st = c.prepareStatement(sql);
		st.setLong(1, tweet.getId_tweet());
		st.setString(2,tweet.getTweet().replaceAll(this.pattern,"").trim());
		st.setLong(3,tweet.getId_user());
		st.setTimestamp(4, tweet.getCreated());
		st.setString(5,tweet.getCiudad());
		st.setString(6,tweet.getSentimiento());

		try {
			st.executeUpdate();
		} catch(Exception ex) {
			//System.out.println("Tweet : "+ex);
			//System.out.println(tweet.toString());
			c.commit();
		}
	}
	
	public void insertaHashtag (Hashtag hashtag, Connection c ) throws SQLException {
		
		String sql = "INSERT INTO twitter_hashtags(id_tweet, id_user, hashtag)"
				+ " VALUES (?, ?, ?);";
		PreparedStatement st = null;
		st = c.prepareStatement(sql);
		st.setLong(1, hashtag.getId_tweet());
		st.setLong(2,hashtag.getId_user());
		st.setString(3,hashtag.getHashtag());

		try {
			st.executeUpdate();
		} catch(Exception ex) {
			//System.out.println("Hashtag : "+ex);
			c.commit();
		}
	}
	
	public void insertaFiltroPalabra (FiltroPalabra filtroPalabra, Connection c ) throws SQLException {
		
		String sql = "INSERT INTO `social_network`.`twitter_tweets_filtro_palabra` (`id_filtro_palabra`,`id_tweet`)"
				+ "VALUES(?, ?);";
		PreparedStatement st = null;
		st = c.prepareStatement(sql);
		st.setInt(1, filtroPalabra.getId_filtro_palabra());
		st.setLong(2,filtroPalabra.getId_tweet());
		
		try {
			st.executeUpdate();
		} catch(Exception ex) {
			//System.out.println("Tweet : "+ex);
			//System.out.println(tweet.toString());
			c.commit();
		}
	}
}
