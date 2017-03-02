import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import modelos.Tweets;
import modelos.Users;

public class TwitterMethods {

	public static void queryTwitter() throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		// Twitter twitter = tf.getInstance();
		/*
		 * Twitter twitter = tf.getSingleton(); Status status =
		 * twitter.updateStatus("Prueba"); System.out.println(
		 * "Successfully updated the status to [" + status.getText() + "].");
		 */

		// Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query("");
		//query.setCount(100);
		query.setGeoCode(new GeoLocation(19.3691511, -99.1406925), 5.0, Query.KILOMETERS);
		QueryResult result = twitter.search(query);

		System.out.println("Tweets consultados ====================> "+ result.getCount());

		for (Status status : result.getTweets()) {
			//System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			//System.out.println(status.toString());
			System.out.println();
			System.out.println(status.getId());
			System.out.println(status.getText());
			System.out.println(status.getUser().getScreenName());
			System.out.println(status.getUser().getFriendsCount());
			
			HashtagEntity[] hashtagEntity = status.getHashtagEntities().clone();
			
			//System.out.println( hashtagEntity.length );
			
			for ( HashtagEntity o: hashtagEntity ){
				System.out.println(o.getText());
			}
		}
	}

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

		//System.out.println("Tweets consultados ====================> "+ result.getCount() + " - "+ ciudad);
		String content = "Tweets consultados ====================> "+ result.getCount() + " - "+ ciudad;
		
		File file = new File("C:/streaming/filename.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(System.getProperty("line.separator"));
		bw.write(content);
		Tweets tweet = new Tweets();
		Users user = new Users();
		
		Connection c = null;

		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/social_network", "postgres","root");
		c.setAutoCommit(false);

		//PreparedStatement stmt = null;
		Statement st = c.createStatement();
		
		DbMethods dbMethods = new DbMethods();
		
		for (Status status : result.getTweets()) {

			bw.write(System.getProperty("line.separator"));
		
				if (status.getRetweetCount() == 0) {
					bw.write("==========================================================================");
					
					// Datos del Tweet.
					bw.write(System.getProperty("line.separator"));
					bw.write("TweetID: " + (long) status.getId());
					bw.write(System.getProperty("line.separator"));
					bw.write("Tweet: " + status.getText());
					bw.write(System.getProperty("line.separator"));
					bw.write("Retweet Count: " +  status.getRetweetCount() );
					bw.write(System.getProperty("line.separator"));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = sdf.format(status.getCreatedAt());
					//System.out.println(date); 
					bw.write("Created at: " +  date );
					
					
					tweet.setCiudad(ciudad);
					tweet.setId_tweet(status.getId());
					tweet.setId_user(status.getUser().getId());
					tweet.setTweet(status.getText());
					tweet.setCreated(date);
					
					System.out.println(tweet.toString());
					
					//bw.write(System.getProperty("line.separator"));
					//bw.write(status.getSource());
					
					
					// Datos del Usuario.
					bw.write(System.getProperty("line.separator"));
					bw.write("UserId: " + (int) status.getUser().getId()  );
					bw.write(System.getProperty("line.separator"));
					bw.write("Screen Name: " + status.getUser().getScreenName());
					bw.write(System.getProperty("line.separator"));
					bw.write("Name: " + status.getUser().getName());
					bw.write(System.getProperty("line.separator"));
					bw.write("Description: " + status.getUser().getDescription() );
					bw.write(System.getProperty("line.separator"));
					bw.write("FriendsCount: " + (int) status.getUser().getFriendsCount());
					bw.write(System.getProperty("line.separator"));
					bw.write("Followers Count: " + (int) status.getUser().getFollowersCount() );
					bw.write(System.getProperty("line.separator"));
					bw.write("Location: " + status.getUser().getLocation() );

					user.setId_user(status.getUser().getId());
					user.setScreen_name(status.getUser().getScreenName());
					user.setReal_name(status.getUser().getName());
					user.setDescription(status.getUser().getDescription());
					user.setFriends_count(status.getUser().getFriendsCount());
					user.setFollowers_count(status.getUser().getFollowersCount());
					user.setLocation(status.getUser().getLocation());
					
					dbMethods.insertaUsuario(user, c);
					

					
					
					
					System.out.println(user.toString());
//					System.out.println(status.getId());
//					System.out.println(status.getText());
//					System.out.println(status.getUser().getScreenName());
//					System.out.println(status.getUser().getFriendsCount());
					// Datos del Hashtag
					if (status.getHashtagEntities().length > 0) {
						HashtagEntity[] hashtagEntity = status.getHashtagEntities().clone();
						bw.write(System.getProperty("line.separator"));
						bw.write("HHHHHAAAAASSSSSHHHHHHTTTTTAAAAAAGGGGGSSSSSS!!!!!! ================================");
						for ( HashtagEntity o: hashtagEntity ){
							System.out.println(o.getText());
							bw.write(System.getProperty("line.separator"));
							bw.write(o.getText());
						}
					}
				}
		}
		bw.close();
		
		//resultSet.close();
		c.commit();
		st.close();
	}
	
	public void SearchTweets() throws TwitterException{
	    // The factory instance is re-useable and thread safe.
	    Twitter twitter = TwitterFactory.getSingleton();
	    
	    GeoLocation location = new GeoLocation(19.419444,-99.145556);
	    
	    
		double radius = 150;
		
		Query query = new Query();
		query.query("kush");
		//query.query("acapulco");
		//query.query("cancun");
		query.setGeoCode(location, radius, Query.MILES);
		query.count(100);
		query.until("2017-01-31");
		query.resultType(ResultType.recent);
		
	    QueryResult result = twitter.search(query);
	    
	    int cuenta = 0;
	    
	    for (Status status : result.getTweets()) {
	        System.out.println(cuenta + " @" + status.getUser().getScreenName() + ":" + status.getText() +
	        		" fecha: " + status.getCreatedAt().toString() );
	        cuenta++;
	    }
	}
	

	public void streamTwitter() throws TwitterException {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		
		
		FilterQuery tweetFilterQuery = new FilterQuery(); // See 
		
				tweetFilterQuery.track(new String[]{"nintendo", "ps4"}); // OR on keywords
		tweetFilterQuery.locations(new double[][]{new double[]{-126.562500,30.448674},
		                new double[]{-61.171875,44.087585
		                }}); // See https://dev.twitter.com/docs/streaming-apis/parameters#locations for proper location doc. 
		//Note that not all tweets have location metadata set.
		tweetFilterQuery.language(new String[]{"en"}); // Note that language does not work properly on Norwegian tweets 
		

		StatusListener listener = new StatusListener() {
			@SuppressWarnings({ "deprecation" })
			@Override
			public void onStatus(Status status) {

				Connection c = null;
				// Statement stmt = null;
				try {
					Class.forName("org.postgresql.Driver");
					c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/social_network", "postgres",
							"root");
					c.setAutoCommit(true);

					try {

						String content = "|User| @" + status.getUser().getScreenName() + "\n" + "|Text| "
								+ status.getText() + "\n" + "|Lang| " + status.getLang() + "\n" + "|GeoLocation| "
								+ status.getGeoLocation() + "\n" + "|Place| " + status.getPlace() + "\n"
								+ "|getInReplyToStatusId| " + status.getInReplyToStatusId() + "\n" + "|getCreatedAt| "
								+ status.getCreatedAt() + "\n";

						System.out.println(content);
						
						String sql = "INSERT INTO twitter_stream("
								+ "twitter_user, text_posted, lang, latitude, longitud, place, date_published)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?);";

						PreparedStatement stmt = null;
						stmt = c.prepareStatement(sql);

						stmt.setString(1, status.getUser().getScreenName());

						stmt.setString(2, status.getText().replace("\n", "").toLowerCase());
						
						String lenguaje = status.getLang();
						System.out.println(lenguaje + " es igual a en");
						String idioma = new String("en");
						if ( lenguaje.equals(idioma) ){
							System.out.println("es ingles");
							Utiles.obtienePalabras(status.getText().replace("\n", "").toLowerCase());
						}
						stmt.setString(3, status.getLang());

						if (status.getGeoLocation() == null) {
							stmt.setLong(4, 0);
						} else {
							stmt.setLong(4, (long) status.getGeoLocation().getLatitude());
						}

						if (status.getGeoLocation() == null) {
							stmt.setLong(5, 0);
						} else {
							stmt.setLong(5, (long) status.getGeoLocation().getLongitude());
						}
						
						if (status.getPlace() == null) {
							stmt.setString(6, null);
						} else {
							stmt.setString(6, status.getPlace().getCountry());
						}
						try {
							stmt.setTimestamp(7,
									java.sql.Timestamp.valueOf("2016-0" + status.getCreatedAt().getMonth() + "-"
											+ status.getCreatedAt().getDate() + " " + status.getCreatedAt().getHours()
											+ ":" + status.getCreatedAt().getMinutes() + ":"
											+ status.getCreatedAt().getSeconds()));
						} catch (Exception e) {
							System.out.println(e);

						}
						//System.out.println(sql.toString());
						stmt.executeUpdate();

						// if( status.getLang() == "en" || status.getLang() ==
						// "es"){
						File file = new File("C:/streaming/filename.txt");

						// if file doesnt exists, then create it
						if (!file.exists()) {
							file.createNewFile();
						}
/*
						FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(System.getProperty("line.separator"));
						bw.write(content);
						bw.close();
*/
						// }

						// System.out.println("Done");

					} catch (IOException e) {
						e.printStackTrace();
					}

					//c.commit();
					c.close();
				} catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
					System.exit(0);
				}

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// System.out.println("Got a status deletion notice id:" +
				// statusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// System.out.println("Got track limitation notice:" +
				// numberOfLimitedStatuses);
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// System.out.println("Got scrub_geo event userId:" + userId + "
				// upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}

		};

		twitterStream.addListener(listener);
		twitterStream.filter(tweetFilterQuery);

		//twitterStream.sample();
	}
	
}
