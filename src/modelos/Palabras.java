package modelos;

public class Palabras {
	private int id_twitter_filtro;
	private String palabra;
	private int id;
	/**
	 * @return the id_twitter_filtro
	 */
	public int getId_twitter_filtro() {
		return id_twitter_filtro;
	}
	/**
	 * @param id_twitter_filtro the id_twitter_filtro to set
	 */
	public void setId_twitter_filtro(int id_twitter_filtro) {
		this.id_twitter_filtro = id_twitter_filtro;
	}
	/**
	 * @return the palabra
	 */
	public String getPalabra() {
		return palabra;
	}
	/**
	 * @param palabra the palabra to set
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Palabras [id_twitter_filtro=" + id_twitter_filtro + ", palabra=" + palabra + ", id=" + id + "]";
	}
	
	
}
