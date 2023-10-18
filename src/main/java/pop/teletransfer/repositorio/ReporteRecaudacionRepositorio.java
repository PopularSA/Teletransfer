package pop.teletransfer.repositorio;

import java.util.List;

import pop.teletransfer.dominio.ReporteRecaudacion;



public interface ReporteRecaudacionRepositorio {
	
 	ReporteRecaudacion findOne(Long id);

	List<ReporteRecaudacion> findAll();

	void save(ReporteRecaudacion reporteRecaudacion);

	void update(ReporteRecaudacion reporteRecaudacion);

	void delete(Long id);

}
