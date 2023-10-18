package pop.teletransfer.dominio;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class TipoOperacion {
	int idTipoOperacion;
	String nombre;
	Date fechaRegistro;

	public TipoOperacion() {

	}

	public TipoOperacion(int idTipoOperacion, String nombre, Date fechaRegistro, int activo) {
		super();
		this.idTipoOperacion = idTipoOperacion;
		this.nombre = nombre;
		this.fechaRegistro = fechaRegistro;
		this.activo = activo;
	}

	public int getIdTipoOperacion() {
		return idTipoOperacion;
	}

	public void setIdTipoOperacion(int idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
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

	int activo;
}
