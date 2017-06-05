package modelos;

/**
 * 
 * @author LenovoY50
 *
 */
public class Hashtag {
	private long id_tweet;
	private long id_user;
	private String hashtag;
	/**
	 * @return the id_tweet
	 */
	public long getId_tweet() {
		return id_tweet;
	}
	/**
	 * @param id_tweet the id_tweet to set
	 */
	public void setId_tweet(long id_tweet) {
		this.id_tweet = id_tweet;
	}
	/**
	 * @return the id_user
	 */
	public long getId_user() {
		return id_user;
	}
	/**
	 * @param id_user the id_user to set
	 */
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}
	/**
	 * @return the hashtag
	 */
	public String getHashtag() {
		return hashtag;
	}
	/**
	 * @param hashtag the hashtag to set
	 */
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hashtag [id_tweet=" + id_tweet + ", id_user=" + id_user + ", hashtag=" + hashtag + "]";
	}
	
	
	
}
