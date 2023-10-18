package pop.teletransfer.repositorio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pop.teletransfer.dominio.Area;
import pop.teletransfer.dominio.CodigoArea;

@Repository
public class CodigoAreaRepositorioImpl implements CodigoAreaRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<CodigoArea> codigoAreaRowMapper = (rs, rowNum) -> new CodigoArea(rs.getInt("C_CODIGO_AREA_ID"),
			new Area(rs.getInt("C_AREA_ID"), rs.getString("A_NOMBRE"), ""), rs.getString("A_CODIGO"),
			rs.getString("A_DETALLE"));

	@Override
	public List<CodigoArea> findAll() {
		List<CodigoArea> codigoAreas = jdbcTemplate.query(
				"SELECT  CA.*,AR.A_NOMBRE FROM TL_CODIGO_AREA CA INNER JOIN TL_AREA AR ON CA.C_AREA_ID=AR.C_AREA_ID WHERE CA.E_ACTIVO=1 ",
				codigoAreaRowMapper);
		return codigoAreas;
	}

	@Override
	public void save(CodigoArea codigoArea) {
		jdbcTemplate.update("INSERT INTO TL_CODIGO_AREA (C_AREA_ID, A_CODIGO ,A_DETALLE)VALUES(?,?,?)",
				codigoArea.getArea().getIdArea(), codigoArea.getCodigoArea(), codigoArea.getDetalle());
	}

	@Override
	public void update(CodigoArea codigoArea) {
		jdbcTemplate.update("UPDATE  TL_CODIGO_AREA  SET C_AREA_ID=?, A_CODIGO=?,A_DETALLE=? WHERE C_CODIGO_AREA_ID=?",
				codigoArea.getArea().getIdArea(), codigoArea.getCodigoArea(), codigoArea.getDetalle(),
				codigoArea.getIdCodigoArea());
	}

	@Override
	public void delete(CodigoArea codigoArea) {
		jdbcTemplate.update("UPDATE  TL_CODIGO_AREA  SET E_ACTIVO=0 WHERE C_CODIGO_AREA_ID=?",
				codigoArea.getIdCodigoArea());
	}

}
