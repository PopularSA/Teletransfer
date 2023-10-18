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

import pop.teletransfer.dominio.ReporteRecaudacion;
import pop.teletransfer.repositorio.ReporteRecaudacionRepositorio;

@RestController
@RequestMapping("/recaudacion")
public class PrestaClubController {

	@Autowired
	private ReporteRecaudacionRepositorio reporteRecaudacionRepositorio;	
	
	
	@RequestMapping(value = "/{id}", method = GET)
	public ReporteRecaudacion findOne(@PathVariable Long id) {
		return reporteRecaudacionRepositorio.findOne(id);
	}
	
	@RequestMapping(method = GET)
	public List<ReporteRecaudacion> findAll() {
		return reporteRecaudacionRepositorio.findAll();
	}
	
    @RequestMapping(value="/registro",method = RequestMethod.POST, headers={"Accept=*/*", "Content-Type=application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody ReporteRecaudacion reporteRecaudacion){
    	reporteRecaudacionRepositorio.save(reporteRecaudacion);
    }	
	

	
}




