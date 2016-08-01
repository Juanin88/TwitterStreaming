
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
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
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

public class TwitterTest {

	public static void main(String args[]) throws Exception {
		TwitterTest twitterTest = new TwitterTest();
		// twitterTest.queryTwitter();
		TwitterTest.streamTwitter();
	}

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
		Query query = new Query("pokemon nintendo");
		query.setCount(10);
		query.setGeoCode(new GeoLocation(19.3691511, -99.1406925), 10.0, Query.KILOMETERS);
		QueryResult result = twitter.search(query);

		System.out.println(result.getCount());

		for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			System.out.println(status.toString());
		}
	}

	public static void streamTwitter() throws TwitterException {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		StatusListener listener = new StatusListener() {
			@SuppressWarnings({ "null", "null", "deprecation" })
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

						/*String content = "|User| @" + status.getUser().getScreenName() + "\n" + "|Text| "
								+ status.getText() + "\n" + "|Lang| " + status.getLang() + "\n" + "|GeoLocation| "
								+ status.getGeoLocation() + "\n" + "|Place| " + status.getPlace() + "\n"
								+ "|getInReplyToStatusId| " + status.getInReplyToStatusId() + "\n" + "|getCreatedAt| "
								+ status.getCreatedAt() + "\n";*/

						String sql = "INSERT INTO twitter_stream("
								+ "twitter_user, text_posted, lang, latitude, longitud, place, date_published)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?);";

						PreparedStatement stmt = null;
						stmt = c.prepareStatement(sql);

						stmt.setString(1, status.getUser().getScreenName());

						stmt.setString(2, status.getText().replace("\n", "").toLowerCase());

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
						System.out.println(sql.toString());
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
		twitterStream.sample();
	}

}
