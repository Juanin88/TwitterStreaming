import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import modelos.Hashtag;
import modelos.Tweets;
import modelos.Users;

public class TwitterMethods {

	public static void queryTwitterByGeoLocation(double latitude, double longitude, double radius, String queryString, String ciudad, String lang) throws TwitterException, IOException, ClassNotFoundException, SQLException {
		ConfigurationBuilder cb = new ConfigurationBuilder();

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		// Recibe el string para filtrar la busqueda.
		Query query = new Query(queryString);

		// Recibe latitud y longitud para buscar por cierta zona geografica.
		query.setGeoCode(new GeoLocation(latitude, longitude), radius, Query.KILOMETERS);
		
		// Filtramos para idioma inglés.
		query.setLang(lang);

		QueryResult result = twitter.search(query);

		Tweets tweet = new Tweets();
		Users user = new Users();
		Hashtag hashtag = new Hashtag();
		
		Connection c = null;

		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/social_network", "postgres","root");
		c.setAutoCommit(false);

		//PreparedStatement stmt = null;
		Statement st = c.createStatement();
		
		DbMethods dbMethods = new DbMethods();
		
		for (Status status : result.getTweets()) {
		
				if (status.getRetweetCount() == 0) {
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					@SuppressWarnings("unused")
					String date = sdf.format(status.getCreatedAt());					
					
					tweet.setCiudad(ciudad);
					tweet.setId_tweet(status.getId());
					tweet.setId_user(status.getUser().getId());
					tweet.setTweet(status.getText());
					
					java.sql.Timestamp sqlDate = new java.sql.Timestamp( status.getCreatedAt().getTime() );
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
}
