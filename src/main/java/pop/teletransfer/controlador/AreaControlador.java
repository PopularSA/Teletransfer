package pop.teletransfer.controlador;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pop.teletransfer.dominio.Area;
import pop.teletransfer.repositorio.AreaRepositorio;

@RestController
@RequestMapping("/area")
public class AreaControlador {

	@Autowired
	private AreaRepositorio areaRepositorio;

	public void setFondoRepository(AreaRepositorio areaRepositorio) {
		this.areaRepositorio = areaRepositorio;
	}
	
	@RequestMapping(method = GET)
	public Resultado findAll() {
		Resultado oResultado = new Resultado();
		oResultado.setData(areaRepositorio.findAll());
		return oResultado;
	}
	
	class Resultado {
		List<Area> data;

		public List<Area> getData() {
			return data;
		}

		public void setData(List<Area> data) {
			this.data = data;
		}
	}
}
