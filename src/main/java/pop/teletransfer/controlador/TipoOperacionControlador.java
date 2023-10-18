package pop.teletransfer.controlador;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pop.teletransfer.dominio.TipoOperacion;
import pop.teletransfer.repositorio.TipoOperacionRepositorio;

@RestController
@RequestMapping("/tipooperacion")
public class TipoOperacionControlador {
	@Autowired
	private TipoOperacionRepositorio tipoOperacionRepositorio;

	public void setTipoOpercionRepository(TipoOperacionRepositorio tipoOperacionRepositorio) {
		this.tipoOperacionRepositorio = tipoOperacionRepositorio;
	}

	@RequestMapping(value = "/{id}", method = GET)
	public TipoOperacion findOne(@PathVariable Long id) {
		return tipoOperacionRepositorio.findOne(id);
	}

	@RequestMapping(method = GET)
	public Resultado findAll() {
		Resultado oResultado = new Resultado();
		oResultado.setData(tipoOperacionRepositorio.findAll());
		return oResultado;
	}

	class Resultado {
		List<TipoOperacion> data;

		public List<TipoOperacion> getData() {
			return data;
		}

		public void setData(List<TipoOperacion> data) {
			this.data = data;
		}
	}
}
