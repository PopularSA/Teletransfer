package pop.teletransfer.repositorio;

import java.util.List;

import pop.teletransfer.dominio.TipoOperacion;

public interface TipoOperacionRepositorio {
	TipoOperacion findOne(Long id);

	TipoOperacion findOne(String nombre);

	List<TipoOperacion> findAll();

	void save(TipoOperacion tipoOperacion);

	void update(TipoOperacion tipoOperacion);

	void delete(TipoOperacion tipoOperacion);
}
