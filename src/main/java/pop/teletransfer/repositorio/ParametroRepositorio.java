package pop.teletransfer.repositorio;

import java.util.List;
import pop.teletransfer.dominio.Parametro;

public interface ParametroRepositorio {
	List<Parametro> findAll();
}
