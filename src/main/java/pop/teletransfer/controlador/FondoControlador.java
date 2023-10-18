package pop.teletransfer.controlador;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pop.teletransfer.dominio.Fondo;
import pop.teletransfer.repositorio.FondoRepositorio;

@RestController
@RequestMapping("/fondo")
public class FondoControlador {
	@Autowired
	private FondoRepositorio fondoRepositorio;

	public void setFondoRepository(FondoRepositorio fondoRepositorio) {
		this.fondoRepositorio = fondoRepositorio;
	}

	@RequestMapping(value = "/{id}", method = GET)
	public Fondo findOne(@PathVariable Long id) {
		return fondoRepositorio.findOne(id);
	}

	@RequestMapping(method = GET)
	public Resultado findAll() {
		Resultado oResultado = new Resultado();
		oResultado.setData(fondoRepositorio.findAll());
		return oResultado;
	}

	class Resultado {
		List<Fondo> data;

		public List<Fondo> getData() {
			return data;
		}

		public void setData(List<Fondo> data) {
			this.data = data;
		}
	}
}
