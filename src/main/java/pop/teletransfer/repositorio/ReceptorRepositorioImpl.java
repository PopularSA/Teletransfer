package pop.teletransfer.repositorio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pop.teletransfer.dominio.Receptor;

@Repository
public class ReceptorRepositorioImpl implements ReceptorRepositorio {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Receptor> receptorRowMapper = (rs, rowNum) -> new Receptor(rs.getInt("C_RECEPTOR_ID"),
			rs.getString("A_CORREO"), 0);

	@Override
	public List<Receptor> findAllFase() {
		List<Receptor> receptor = (List<Receptor>) jdbcTemplate.query(
				"SELECT * FROM TL_RECEPTOR WHERE E_ACTIVO=1 ORDER BY A_CORREO ",
				receptorRowMapper);
		return receptor;
	}

	@Override
	public void save(Receptor receptor) {
		jdbcTemplate.update("INSERT INTO TL_RECEPTOR (A_CORREO,C_FASE_ID)VALUES(?,?)", receptor.getCorreo(),
				receptor.getIdFase());
	}

	@Override
	public void delete(Receptor receptor) {
		jdbcTemplate.update("UPDATE TL_RECEPTOR SET E_ACTIVO=0 WHERE A_CORREO =? AND C_FASE_ID=?", receptor.getCorreo(),
				receptor.getIdFase());
	}

}
