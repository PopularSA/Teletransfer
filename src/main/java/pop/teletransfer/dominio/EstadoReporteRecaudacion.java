package pop.teletransfer.dominio;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class EstadoReporteRecaudacion {

	public EstadoReporteRecaudacion() {

	}

	public EstadoReporteRecaudacion(int idEstadoReporteRecaudacion, int idEstado, int idReporteRecaudacion,
			Date fechaRegistro, int activo) {
		super();
		this.idEstadoReporteRecaudacion = idEstadoReporteRecaudacion;
		this.idEstado = idEstado;
		this.idReporteRecaudacion = idReporteRecaudacion;
		this.fechaRegistro = fechaRegistro;
		this.activo = activo;
	}

	public int getIdEstadoReporteRecaudacion() {
		return idEstadoReporteRecaudacion;
	}

	public void setIdEstadoReporteRecaudacion(int idEstadoReporteRecaudacion) {
		this.idEstadoReporteRecaudacion = idEstadoReporteRecaudacion;
	}

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public int getIdReporteRecaudacion() {
		return idReporteRecaudacion;
	}

	public void setIdReporteRecaudacion(int idReporteRecaudacion) {
		this.idReporteRecaudacion = idReporteRecaudacion;
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

	int idEstadoReporteRecaudacion;
	int idEstado;
	int idReporteRecaudacion;
	Date fechaRegistro;
	int activo;

}
