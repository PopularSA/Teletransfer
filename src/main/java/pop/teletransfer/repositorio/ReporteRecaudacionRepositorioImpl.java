package pop.teletransfer.repositorio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pop.teletransfer.dominio.ReporteRecaudacion;

@Repository
public class ReporteRecaudacionRepositorioImpl implements ReporteRecaudacionRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<ReporteRecaudacion> reporteRecaudacionRowMapper = (rs, rowNum) -> new ReporteRecaudacion(
			rs.getInt("C_REPORTERECAUDACION_ID"), rs.getString("F_FECHAREGISTRO"), 0, rs.getString("E_ESTADO"));

	@Override
	public ReporteRecaudacion findOne(Long id) {
		ReporteRecaudacion reporteRecaudacion = jdbcTemplate.queryForObject(
				"SELECT * FROM TL_REPORTERECAUDACION WHERE C_REPORTERECAUDACION_ID=?", new Long[] { id },
				reporteRecaudacionRowMapper);
		return reporteRecaudacion;
	}

	@Override
	public List<ReporteRecaudacion> findAll() {
		List<ReporteRecaudacion> reportesRecaudacion = jdbcTemplate.query(
				"SELECT C_REPORTERECAUDACION_ID,TO_CHAR(F_FECHAREGISTRO,'dd/mm/YYYY') AS F_FECHAREGISTRO,CASE C_ESTADOREPORTE_ID WHEN 0 THEN 'ULTIMO ENVIO' WHEN 1 THEN 'PROCESADO' END AS E_ESTADO FROM TL_REPORTERECAUDACION WHERE E_ACTIVO=1 ",
				reporteRecaudacionRowMapper);
		return reportesRecaudacion;
	}

	@Override
	public void save(ReporteRecaudacion reporteRecaudacion) {

	}

	@Override
	public void update(ReporteRecaudacion reporteRecaudacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
