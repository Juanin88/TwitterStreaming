package modelos;

import java.sql.Timestamp;
import java.util.Date;

public class Tweets {
	 /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tweets [id_tweet=" + id_tweet + ", tweet=" + tweet + ", id_user=" + id_user + ", created=" + created
				+ ", ciudad=" + ciudad + "]";
	}
	private long id_tweet;
	 private String tweet;
	 private long id_user;
	 private Timestamp created;
	 private String ciudad;
	/**
	 * @return the id_tweet
	 */
	public long getId_tweet() {
		return id_tweet;
	}
	/**
	 * @param l the id_tweet to set
	 */
	public void setId_tweet(long l) {
		this.id_tweet = l;
	}
	/**
	 * @return the tweet
	 */
	public String getTweet() {
		return tweet;
	}
	/**
	 * @param tweet the tweet to set
	 */
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	/**
	 * @return the id_user
	 */
	public long getId_user() {
		return id_user;
	}
	/**
	 * @param l the id_user to set
	 */
	public void setId_user(long l) {
		this.id_user = l;
	}
	/**
	 * @return the created
	 */
	public Timestamp getCreated() {
		return created;
	}
	/**
	 * @param sqlDate the created to set
	 */
	public void setCreated(Timestamp sqlDate) {
		this.created = sqlDate;
	}
	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	 
	 
}
