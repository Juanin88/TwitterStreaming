package modelos;

public class Users {
	private long id_user; 
	private String screen_name;
	private String real_name;
	private String description;
	private int friends_count;
	private int followers_count;
	private String location;
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
	 * @return the screen_name
	 */
	public String getScreen_name() {
		return screen_name;
	}
	/**
	 * @param screen_name the screen_name to set
	 */
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	/**
	 * @return the real_name
	 */
	public String getReal_name() {
		return real_name;
	}
	/**
	 * @param real_name the real_name to set
	 */
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the friends_count
	 */
	public int getFriends_count() {
		return friends_count;
	}
	/**
	 * @param friends_count the friends_count to set
	 */
	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}
	/**
	 * @return the followers_count
	 */
	public int getFollowers_count() {
		return followers_count;
	}
	/**
	 * @param followers_count the followers_count to set
	 */
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [id_user=" + id_user + ", screen_name=" + screen_name + ", real_name=" + real_name
				+ ", description=" + description + ", friends_count=" + friends_count + ", followers_count="
				+ followers_count + ", location=" + location + "]";
	}
	
	
}
