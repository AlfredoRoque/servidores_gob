package beans;

import java.io.Serializable;
import java.util.List;

public class Servidores implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Dependencia secretaria;
	private Integer idServidor;
	private Integer idDependencia;
	private String nombre;
	private String puesto;
	private String mision;
	private List<Servidores> listaServidores;
	private List<Integer> numeros;
	
	public Integer getIdServidor() {
		return idServidor;
	}
	public void setIdServidor(Integer idServidor) {
		this.idServidor = idServidor;
	}
	public Integer getIdDependencia() {
		return idDependencia;
	}
	public void setIdDependencia(Integer idDependencia) {
		this.idDependencia = idDependencia;
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
	public Dependencia getSecretaria() {
		return secretaria;
	}
	public void setSecretaria(Dependencia secretaria) {
		this.secretaria = secretaria;
	}
	public List<Servidores> getListaServidores() {
		return listaServidores;
	}
	public void setListaServidores(List<Servidores> listaServidores) {
		this.listaServidores = listaServidores;
	}
	public List<Integer> getNumeros() {
		return numeros;
	}
	public void setNumeros(List<Integer> numeros) {
		this.numeros = numeros;
	}

}
