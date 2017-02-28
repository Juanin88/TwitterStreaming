
public class Ciudad {

	private int id;
	private String ciudad;
	private String estado;
	private double latitud;
	private double longitud;
	private int censo_2010;
	private int censo_estimado_2015;
	private double radio;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ciudad [id=" + id + ", ciudad=" + ciudad + ", estado=" + estado + ", latitud=" + latitud + ", longitud="
				+ longitud + ", censo_2010=" + censo_2010 + ", censo_estimado_2015=" + censo_estimado_2015 + ", radio="
				+ radio + "]";
	}
	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the ciudad
	 */
	public final String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public final void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the estado
	 */
	public final String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public final void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the latitud
	 */
	public final double getLatitud() {
		return latitud;
	}
	/**
	 * @param latitud the latitud to set
	 */
	public final void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	/**
	 * @return the longitud
	 */
	public final double getLongitud() {
		return longitud;
	}
	/**
	 * @param longitud the longitud to set
	 */
	public final void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	/**
	 * @return the censo_2010
	 */
	public final int getCenso_2010() {
		return censo_2010;
	}
	/**
	 * @param censo_2010 the censo_2010 to set
	 */
	public final void setCenso_2010(int censo_2010) {
		this.censo_2010 = censo_2010;
	}
	/**
	 * @return the censo_estimado_2015
	 */
	public final int getCenso_estimado_2015() {
		return censo_estimado_2015;
	}
	/**
	 * @param censo_estimado_2015 the censo_estimado_2015 to set
	 */
	public final void setCenso_estimado_2015(int censo_estimado_2015) {
		this.censo_estimado_2015 = censo_estimado_2015;
	}
	/**
	 * @return the radio
	 */
	public final double getRadio() {
		return radio;
	}
	/**
	 * @param radio the radio to set
	 */
	public final void setRadio(double radio) {
		this.radio = radio;
	}
	
}
