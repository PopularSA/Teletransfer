package pop.teletransfer.dominio;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Fondo {

	public Fondo() {

	}

	public Fondo(int idFondo, String nombre, Date fechaRegistro, int activo) {
		super();
		this.idFondo = idFondo;
		this.nombre = nombre;
		this.fechaRegistro = fechaRegistro;
		this.activo = activo;
	}

	int idFondo;
	String nombre;
	Date fechaRegistro;
	int activo;

	public int getIdFondo() {
		return idFondo;
	}

	public void setIdFondo(int idFondo) {
		this.idFondo = idFondo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

}
