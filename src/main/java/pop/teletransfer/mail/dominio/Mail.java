package pop.teletransfer.mail.dominio;

import java.util.List;
import org.springframework.stereotype.Component;

import pop.teletransfer.dominio.Receptor;

@Component
public class Mail {
	List<Receptor> receptores;
	String emisor;
	String asunto;
	String contenido;

	public List<Receptor> getReceptores() {
		return receptores;
	}

	public void setReceptores(List<Receptor> receptores) {
		this.receptores = receptores;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

}
