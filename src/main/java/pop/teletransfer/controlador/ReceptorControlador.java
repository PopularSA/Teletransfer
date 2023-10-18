package pop.teletransfer.controlador;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pop.teletransfer.dominio.Receptor;
import pop.teletransfer.repositorio.ReceptorRepositorio;

@RestController
@RequestMapping("/receptor")
public class ReceptorControlador {
	@Autowired
	private ReceptorRepositorio receptorRepositorio;

	@RequestMapping(value = "/{id}", method = GET)
	public Resultado findAllRemisores(@PathVariable Long id) { // ESTADO
																// REGISTRADO
																// PRESTACLUB
		Resultado oResultado = new Resultado();
		oResultado.setRecordsTotal(oResultado.getData().size());
		oResultado.setRecordsFiltered(oResultado.getData().size());
		return oResultado;
	}

	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Receptor saveSubRubro(@RequestBody Receptor receptor) {
		receptorRepositorio.save(receptor);
		return receptor;
	}

	@RequestMapping(value = "/actualizacion", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Receptor updateOperacion(@RequestBody Receptor receptor) {
		receptorRepositorio.delete(receptor);
		return receptor;
	}

	class Resultado {
		List<Receptor> data;
		Double sumatoria;
		int recordsTotal;
		int recordsFiltered;

		public List<Receptor> getData() {
			return data;
		}

		public void setData(List<Receptor> data) {
			this.data = data;
		}

		public Double getSumatoria() {
			return sumatoria;
		}

		public void setSumatoria(Double sumatoria) {
			this.sumatoria = sumatoria;
		}

		public int getRecordsTotal() {
			return recordsTotal;
		}

		public void setRecordsTotal(int recordsTotal) {
			this.recordsTotal = recordsTotal;
		}

		public int getRecordsFiltered() {
			return recordsFiltered;
		}

		public void setRecordsFiltered(int recordsFiltered) {
			this.recordsFiltered = recordsFiltered;
		}

	}

}
