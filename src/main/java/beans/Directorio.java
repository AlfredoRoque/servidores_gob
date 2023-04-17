package beans;

import java.io.Serializable;

public class Directorio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Dependencia secretarias;
	private Servidores servidor;
	public Dependencia getSecretarias() {
		return secretarias;
	}
	public void setSecretarias(Dependencia secretarias) {
		this.secretarias = secretarias;
	}
	public Servidores getServidor() {
		return servidor;
	}
	public void setServidor(Servidores servidor) {
		this.servidor = servidor;
	}

}
