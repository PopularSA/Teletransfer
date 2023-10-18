package pop.teletransfer.dominio;

import org.springframework.stereotype.Component;

@Component
public class ReporteRecaudacion {

	int idReporteRecaudacion;
	String fechaRegistro;
	int activo;
	String estado;
	
	public ReporteRecaudacion() {

	}
	
	public ReporteRecaudacion(int idReporteRecaudacion, String fechaRegistro, int activo, String estado) {
		super();
		this.idReporteRecaudacion = idReporteRecaudacion;
		this.fechaRegistro = fechaRegistro;
		this.activo = activo;
		this.estado = estado;
	}

	public int getIdReporteRecaudacion() {
		return idReporteRecaudacion;
	}

	public void setIdReporteRecaudacion(int idReporteRecaudacion) {
		this.idReporteRecaudacion = idReporteRecaudacion;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	

}
