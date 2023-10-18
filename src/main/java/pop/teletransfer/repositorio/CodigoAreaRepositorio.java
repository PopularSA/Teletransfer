package pop.teletransfer.repositorio;

import java.util.List;
import pop.teletransfer.dominio.CodigoArea;
import pop.teletransfer.dominio.Receptor;

public interface CodigoAreaRepositorio {
	List<CodigoArea> findAll();

	public void save(CodigoArea codigoArea);

	public void update(CodigoArea codigoArea);

	public void delete(CodigoArea codigoArea);

}
