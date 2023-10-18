package pop.teletransfer.dominio;

public class Area {
	public Area() {
		super();
	}

	public Area(int idArea, String nombre, String descripcion) {
		super();
		this.idArea = idArea;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	int idArea;
	String nombre;
	String descripcion;

	public int getIdArea() {
		return idArea;
	}

	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
