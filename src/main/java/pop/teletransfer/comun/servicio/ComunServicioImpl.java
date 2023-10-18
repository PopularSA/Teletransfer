package pop.teletransfer.comun.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import pop.teletransfer.dominio.Operacion;
import pop.teletransfer.repositorio.OperacionRepositorio;

@Component
@EnableAsync
public class ComunServicioImpl implements ComunServicio {

	@Autowired
	private OperacionRepositorio operacionRepositorio;

	@Override
	public String ValidaCaractereEspeciales(String frase) {
		// NO DEBEN HABER PUNTOS , APOSTROFES ,Ñs , TILDES
		frase = frase.toUpperCase();
		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
		String ascii = "AAAEEEIIIOOOUUUNAAAEEEIIIOOOUUUNCC";
		for (int i = 0; i < original.length(); i++) {
			frase = frase.replace(original.charAt(i), ascii.charAt(i));
		}
		frase = frase.replaceAll("[^a-zA-Z0-9 ,]", "");
		return frase;
	}

	@Async
	public void RegistrarOperacion(List<Operacion> listaOperacion) throws InterruptedException {
		System.out.println("inicio async");
		for (Operacion operacion : listaOperacion) {
			//operacionRepositorio.save(operacion);
		}
		System.out.println("fin async");
	}

}
