import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Stanford.StanfordSentimentAnalyzer;
import modelos.FiltroPalabra;
import modelos.Hashtag;
import modelos.Palabras;
import modelos.Tweets;
import modelos.Users;

public class TwitterMethods {
	
	private static String pattern;
	
	public TwitterMethods() {
		super();
		this.setPattern("[^a-zA-Z0-9 .,:;@#$&—Ò·ÈÌÛ˙¡…Õ”⁄%'¥]+");
	}
	
	public static void queryTwitterByGeoLocation(double latitude, double longitude, double radius, String queryString, String ciudad, String lang, List<Palabras> palabras) throws TwitterException, IOException, ClassNotFoundException, SQLException {
		ConfigurationBuilder cb = new ConfigurationBuilder();

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		// Recibe el string para filtrar la busqueda.
		Query query = new Query(queryString);

		// Recibe latitud y longitud para buscar por cierta zona geografica.
		query.setGeoCode(new GeoLocation(latitude, longitude), radius, Query.KILOMETERS);
		
		// Filtramos para idioma inglÈs.
		query.setLang(lang);

		QueryResult result = twitter.search(query);

		Tweets tweet = new Tweets();
		Users user = new Users();
		Hashtag hashtag = new Hashtag();
		
		Properties props = new Properties();
		FileInputStream fis = null;
		fis = new FileInputStream("db.properties");
		props.load(fis);

		// load the Driver Class
		Class.forName(props.getProperty("driver"));

		// create the connection now
		Connection c = DriverManager.getConnection(props.getProperty("url"),
				props.getProperty("username"),
				props.getProperty("password"));
		
		c.setAutoCommit(false);

		//PreparedStatement stmt = null;
		Statement st = c.createStatement();
		
		DbMethods dbMethods = new DbMethods();
		
		for (Status status : result.getTweets()) {
		
			if (status.getRetweetCount() == 0) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				@SuppressWarnings("unused")
				String date = sdf.format(status.getCreatedAt());					
				java.sql.Timestamp sqlDate = new java.sql.Timestamp( status.getCreatedAt().getTime() );
				
				tweet.setCiudad(ciudad);
				tweet.setId_tweet(status.getId());
				tweet.setId_user(status.getUser().getId());
				tweet.setTweet(status.getText());
				tweet.setSentimiento(StanfordSentimentAnalyzer.getSentiment(status.getText().replaceAll( getPattern()  , "").trim() ));
				//tweet.setSentimiento("");
				tweet.setCreated( sqlDate );
				
				// Datos del Usuario.
				user.setId_user(status.getUser().getId());
				user.setScreen_name(status.getUser().getScreenName());
				user.setReal_name(status.getUser().getName());
				user.setDescription(status.getUser().getDescription());
				user.setFriends_count(status.getUser().getFriendsCount());
				user.setFollowers_count(status.getUser().getFollowersCount());
				user.setLocation(status.getUser().getLocation());

				dbMethods.insertaUsuario(user, c);
				dbMethods.insertaTweet(tweet, c);

				String s = status.getText().toLowerCase();
				for (Palabras palabra : palabras){
					int validaString = s.indexOf(palabra.getPalabra());
					if(validaString>0){
						FiltroPalabra filtroPalabra = new FiltroPalabra();
						filtroPalabra.setId_filtro_palabra(palabra.getId_twitter_filtro());
						filtroPalabra.setId_tweet(status.getId());
						dbMethods.insertaFiltroPalabra(filtroPalabra, c);
					}
				}

				// Datos del Hashtag
				if (status.getHashtagEntities().length > 0) {
					HashtagEntity[] hashtagEntity = status.getHashtagEntities().clone();
					for ( HashtagEntity o: hashtagEntity ){
						//System.out.println(o.getText());
						hashtag.setId_tweet(status.getId());
						hashtag.setId_user(status.getUser().getId());
						hashtag.setHashtag(o.getText());
						
						dbMethods.insertaHashtag(hashtag, c);
					}
				}
			}
		}
		c.commit();
		st.close();
	}

	public static String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		TwitterMethods.pattern = pattern;
	}
}
