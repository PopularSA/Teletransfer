package pop.teletransfer.controlador;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pop.teletransfer.dominio.CodigoArea;
import pop.teletransfer.dominio.Receptor;
import pop.teletransfer.repositorio.CodigoAreaRepositorio;

@RestController
@RequestMapping("/codigoArea")
public class CodigoAreaControlador {

	@Autowired
	private CodigoAreaRepositorio codigoAreaRepositorio;

	public void setFondoRepository(CodigoAreaRepositorio codigoAreaRepositorio) {
		this.codigoAreaRepositorio = codigoAreaRepositorio;
	}

	@RequestMapping(method = GET)
	public Resultado findAll() {
		Resultado oResultado = new Resultado();
		oResultado.setData(codigoAreaRepositorio.findAll());
		return oResultado;
	}

	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CodigoArea saveCodigoArea(@RequestBody CodigoArea codigoArea) {
		codigoAreaRepositorio.save(codigoArea);
		return codigoArea;
	}

	@RequestMapping(value = "/actualizacion", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CodigoArea updateCodigoArea(@RequestBody CodigoArea codigoArea) {
		codigoAreaRepositorio.update(codigoArea);
		return codigoArea;
	}

	@RequestMapping(value = "/eliminacion", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CodigoArea deleteCodigoArea(@RequestBody CodigoArea codigoArea) {
		codigoAreaRepositorio.delete(codigoArea);
		return codigoArea;
	}

	class Resultado {
		List<CodigoArea> data;

		public List<CodigoArea> getData() {
			return data;
		}

		public void setData(List<CodigoArea> data) {
			this.data = data;
		}
	}

}
