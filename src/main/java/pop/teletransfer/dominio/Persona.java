package pop.teletransfer.dominio;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Persona {
	public Persona() {
	}

	public Persona(int idPersona, String nombre, String aPaterno, String aMaterno, String documento, Date fechaRegistro,
			int activo) {
		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.aPaterno = aPaterno;
		this.aMaterno = aMaterno;
		this.documento = documento;
		this.fechaRegistro = fechaRegistro;
		this.activo = activo;
	}

	int idPersona;
	String nombre;
	String aPaterno;
	String aMaterno;
	String documento;
	Date fechaRegistro;

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getaPaterno() {
		return aPaterno;
	}

	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}

	public String getaMaterno() {
		return aMaterno;
	}

	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	int activo;
}
