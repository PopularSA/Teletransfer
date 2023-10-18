package pop.teletransfer.repositorio;

import java.util.List;

import pop.teletransfer.dominio.Operacion;

public interface OperacionRepositorio {

	Operacion findOne(Long id);

	List<Operacion> findAll();

	void save(Operacion operacion);

	void update(Operacion operacion);

	void updateObs(Operacion operacion);

	void delete(Operacion operacion);

	void updateOperacionEstado(Operacion operacion);

	void updateOperacionEstadoRevisado(Operacion operacion);

	void saveOperaciones(Operacion operacion);

	void updateOperacionRechazo(Operacion operacion);

	List<Operacion> findAllEnviados();

	List<Operacion> findAllRevisados();

	List<Operacion> findAllReporte(Long id);

	List<Operacion> findAllFondo(Long id);

	void clearOperation();

}
