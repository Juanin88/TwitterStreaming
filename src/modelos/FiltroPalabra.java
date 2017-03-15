package modelos;

public class FiltroPalabra {
	private int id_filtro_palabra;
	private long id_tweet;
	/**
	 * @return the id_filtro_palabra
	 */
	public int getId_filtro_palabra() {
		return id_filtro_palabra;
	}
	/**
	 * @param id_filtro_palabra the id_filtro_palabra to set
	 */
	public void setId_filtro_palabra(int id_filtro_palabra) {
		this.id_filtro_palabra = id_filtro_palabra;
	}
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FiltroPalabra [id_filtro_palabra=" + id_filtro_palabra + ", id_tweet=" + id_tweet + "]";
	}

	
	
}
