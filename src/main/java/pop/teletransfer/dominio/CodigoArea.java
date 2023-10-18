package pop.teletransfer.dominio;

public class CodigoArea {
	public CodigoArea() {
		super();
	}

	

	public CodigoArea(int idCodigoArea, Area area, String codigoArea, String detalle) {
		super();
		this.idCodigoArea = idCodigoArea;
		this.area = area;
		this.codigoArea = codigoArea;
		this.detalle = detalle;
	}



	int idCodigoArea;
	Area area;
	String codigoArea;
	String detalle;

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public int getIdCodigoArea() {
		return idCodigoArea;
	}

	public void setIdCodigoArea(int idCodigoArea) {
		this.idCodigoArea = idCodigoArea;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

}
