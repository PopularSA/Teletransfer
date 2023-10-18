package pop.teletransfer.repositorio;

import java.util.List;

import pop.teletransfer.dominio.Receptor;

public interface ReceptorRepositorio {
	List<Receptor> findAllFase();

	void save(Receptor fondo);

	void delete(Receptor fondo);
}
