package pop.teletransfer.comun.servicio;

import java.util.List;

import pop.teletransfer.dominio.Operacion;

public interface ComunServicio {
	String ValidaCaractereEspeciales(String frase);
	public void RegistrarOperacion(List<Operacion> listaOperacion)  throws InterruptedException;
}
