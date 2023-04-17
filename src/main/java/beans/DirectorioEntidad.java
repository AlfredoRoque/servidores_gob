package beans;

import java.io.Serializable;
import java.util.List;

public class DirectorioEntidad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idEntidad;
	private String nombre;
	private String puesto;
	private String mision;
	private Entidades entidad;
	private List<DirectorioEntidad> directorioList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(int idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getMision() {
		return mision;
	}
	public void setMision(String mision) {
		this.mision = mision;
	}
	public Entidades getEntidad() {
		return entidad;
	}
	public void setEntidad(Entidades entidad) {
		this.entidad = entidad;
	}
	public List<DirectorioEntidad> getDirectorioList() {
		return directorioList;
	}
	public void setDirectorioList(List<DirectorioEntidad> directorioList) {
		this.directorioList = directorioList;
	}
	
	

}
