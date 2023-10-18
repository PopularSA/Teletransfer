package pop.teletransfer.repositorio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pop.teletransfer.dominio.Parametro;

@Repository
public class ParametroRepositorioImpl implements ParametroRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Parametro> areaRowMapper = (rs, rowNum) -> new Parametro(rs.getInt("C_PARAMETRO_ID"),
			rs.getInt("C_PADRE_ID"), rs.getInt("C_HIJO_ID"), rs.getString("CLAVE"), rs.getString("VALOR"));

	@Override
	public List<Parametro> findAll() {
		List<Parametro> parametros = jdbcTemplate.query("SELECT * FROM TL_PARAMETRO WHERE E_ACTIVO=1", areaRowMapper);
		return parametros;
	}

}
