package pop.teletransfer.dominio;

public class Parametro {

	public Parametro() {
		super();
	}

	public Parametro(int idParametro, int idPadre, int idHijo, String clave, String valor) {
		super();
		this.idParametro = idParametro;
		this.idPadre = idPadre;
		this.idHijo = idHijo;
		this.clave = clave;
		this.valor = valor;
	}

	int idParametro;
	int idPadre;
	int idHijo;
	String clave;
	String valor;

	public int getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(int idParametro) {
		this.idParametro = idParametro;
	}

	public int getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	public int getIdHijo() {
		return idHijo;
	}

	public void setIdHijo(int idHijo) {
		this.idHijo = idHijo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
