package pop.teletransfer.repositorio;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pop.teletransfer.dominio.TipoOperacion;

@Repository
public class TipoOperacionRepositorioImpl implements TipoOperacionRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<TipoOperacion> tipoOperacionRowMapper = (rs,
			rowNum) -> new TipoOperacion(rs.getInt("C_TIPOOPERACION_ID"), rs.getString("A_NOMBRE"), new Date(0), 2);

	@Override
	public TipoOperacion findOne(Long id) {
		TipoOperacion tipoOperacion = jdbcTemplate.queryForObject(
				"SELECT * FROM TL_TIPOOPERACION WHERE E_ACTIVO=1 AND C_TIPOOPERACION_ID=? ORDER BY A_NOMBRE ", new Long[] { id },
				tipoOperacionRowMapper);
		return tipoOperacion;
	}

	@Override
	public List<TipoOperacion> findAll() {
		List<TipoOperacion> tipoOperacion = jdbcTemplate.query("SELECT * FROM TL_TIPOOPERACION WHERE E_ACTIVO=1 ORDER BY A_NOMBRE ",
				tipoOperacionRowMapper);
		return tipoOperacion;
	}

	@Override
	public void save(TipoOperacion tipoOperacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(TipoOperacion tipoOperacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(TipoOperacion tipoOperacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoOperacion findOne(String nombre) {
		TipoOperacion tipoOperacion = jdbcTemplate.queryForObject(
				"SELECT * FROM TL_TIPOOPERACION WHERE E_ACTIVO=1 AND A_NOMBRE=? ORDER BY A_NOMBRE ", new String[] { nombre },
				tipoOperacionRowMapper);
		return tipoOperacion;
	}

}
