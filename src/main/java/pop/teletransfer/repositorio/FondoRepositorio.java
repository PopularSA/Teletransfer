package pop.teletransfer.repositorio;

import java.util.List;
import pop.teletransfer.dominio.Fondo;


public interface FondoRepositorio {
	Fondo findOne(Long id);

	Fondo findOne(String nombre);

	List<Fondo> findAll();

	void save(Fondo fondo);

	void update(Fondo fondo);

	void delete(Fondo fondo);
}
