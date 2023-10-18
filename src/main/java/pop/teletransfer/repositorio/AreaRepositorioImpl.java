package pop.teletransfer.repositorio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pop.teletransfer.dominio.Area;

@Repository
public class AreaRepositorioImpl implements AreaRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Area> areaRowMapper = (rs, rowNum) -> new Area(rs.getInt("C_AREA_ID"), rs.getString("A_NOMBRE"),
			rs.getString("A_DESCRIPCION"));

	@Override
	public List<Area> findAll() {
		List<Area> areas = jdbcTemplate.query("SELECT * FROM TL_AREA WHERE E_ACTIVO=1 ", areaRowMapper);
		return areas;
	}

}
