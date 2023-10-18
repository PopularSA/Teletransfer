package pop.teletransfer.dominio;

import org.springframework.stereotype.Component;

@Component
public class Operacion {
	int idOperacion;
	String codigo;
	Double monto;
	String fechaRegistro;
	Fondo fondo;
	TipoOperacion tipoOperacion;
	Persona persona;
	int activo;
	String escritura;
	int estadoEnvio;
	String observacion;
	int faseInicial;
	int faseNueva;
	String dni;
	String cliente;

	public Operacion(int idOperacion, String codigo, Double monto, String fechaRegistro, int activo, String escritura,
			Fondo fondo, TipoOperacion tipoOperacion, Persona persona, int estadoEnvio, String observacion, String dni,
			String cliente) {
		super();
		this.idOperacion = idOperacion;
		this.codigo = codigo;
		this.monto = monto;
		this.fechaRegistro = fechaRegistro;
		this.fondo = fondo;
		this.tipoOperacion = tipoOperacion;
		this.persona = persona;
		this.activo = activo;
		this.escritura = escritura;
		this.estadoEnvio = estadoEnvio;
		this.observacion = observacion;

		this.dni = dni;
		this.cliente = cliente;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public int getFaseInicial() {
		return faseInicial;
	}

	public void setFaseInicial(int faseInicial) {
		this.faseInicial = faseInicial;
	}

	public int getFaseNueva() {
		return faseNueva;
	}

	public void setFaseNueva(int faseNueva) {
		this.faseNueva = faseNueva;
	}

	public Operacion() {
	}

	public int getEstadoEnvio() {
		return estadoEnvio;
	}

	public void setEstadoEnvio(int estadoEnvio) {
		this.estadoEnvio = estadoEnvio;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Fondo getFondo() {
		return fondo;
	}

	public void setFondo(Fondo fondo) {
		this.fondo = fondo;
	}

	public int getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getEscritura() {
		return escritura;
	}

	public void setEscritura(String escritura) {
		this.escritura = escritura;
	}

}
