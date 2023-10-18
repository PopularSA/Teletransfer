package pop.teletransfer.repositorio;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pop.teletransfer.dominio.Fondo;

@Repository
public class FondoRepositorioImpl implements FondoRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Fondo> fondoRowMapper = (rs, rowNum) -> new Fondo(rs.getInt("C_FONDO_ID"),
			rs.getString("A_NOMBRE"), new Date(0), 2);

	@Override
	public Fondo findOne(Long id) {
		Fondo operacion = jdbcTemplate.queryForObject(
				"SELECT * FROM TL_FONDO WHERE E_ACTIVO=1 AND C_FONDO_ID=? ORDER BY A_NOMBRE ", new Long[] { id },
				fondoRowMapper);
		return operacion;
	}

	@Override
	public List<Fondo> findAll() {
		List<Fondo> fondo = jdbcTemplate.query("SELECT * FROM TL_FONDO WHERE E_ACTIVO=1 ORDER BY A_NOMBRE ",
				fondoRowMapper);
		return fondo;
	}

	@Override
	public Fondo findOne(String nombre) {
		Fondo operacion=null;
			operacion = jdbcTemplate.queryForObject(
					"SELECT * FROM TL_FONDO WHERE E_ACTIVO=1 AND A_NOMBRE=? ORDER BY A_NOMBRE ",
					new String[] { nombre }, fondoRowMapper);
		return operacion;
	}

	@Override
	public void save(Fondo fondo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Fondo fondo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Fondo fondo) {
		// TODO Auto-generated method stub

	}

}
