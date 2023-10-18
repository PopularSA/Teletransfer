package pop.teletransfer.dominio;

import org.springframework.stereotype.Component;

@Component
public class Receptor {
	int idReceptor;
	String correo;
	int idFase;

	public Receptor(int idReceptor, String correo, int idFase) {
		super();
		this.idReceptor = idReceptor;
		this.correo = correo;
		this.idFase = idFase;
	}

	public Receptor() {

	}

	public int getIdFase() {
		return idFase;
	}

	public void setIdFase(int idFase) {
		this.idFase = idFase;
	}

	public int getIdReceptor() {
		return idReceptor;
	}

	public void setIdReceptor(int idReceptor) {
		this.idReceptor = idReceptor;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
