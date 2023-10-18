package pop.teletransfer.dominio;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Estado {
	public Estado() {
	}

	public Estado(int idEstado, String nombre, Date fechaRegistro, int activo) {
		super();
		this.idEstado = idEstado;
		this.nombre = nombre;
		this.fechaRegistro = fechaRegistro;
		this.activo = activo;
	}

	int idEstado;
	String nombre;
	Date fechaRegistro;
	int activo;

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
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
