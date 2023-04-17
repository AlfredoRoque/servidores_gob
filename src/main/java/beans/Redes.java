package beans;

import java.io.Serializable;

public class Redes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Dependencia dependencia;
	private String twiter;
	private String facebook;
	private String youtube;
	private String instagram;
	
	
	public String getTwiter() {
		return twiter;
	}
	public void setTwiter(String twiter) {
		this.twiter = twiter;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getYoutube() {
		return youtube;
	}
	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	public Dependencia getDependencia() {
		return dependencia;
	}
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}
	
}
