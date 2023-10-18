package pop.teletransfer.repositorio;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pop.teletransfer.dominio.Fondo;
import pop.teletransfer.dominio.Operacion;
import pop.teletransfer.dominio.Persona;
import pop.teletransfer.dominio.TipoOperacion;

@Repository
public class OperacionRepositorioImpl implements OperacionRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Operacion> operacionRowMapper = (rs, rowNum) -> new Operacion(rs.getInt("C_OPERACION_ID"),
			rs.getString("A_CODIGO"), rs.getDouble("N_MONTO"), rs.getString("F_FECHAREGISTRO"), rs.getInt("E_ACTIVO"),
			rs.getString("F_ESCRITURA"), new Fondo(rs.getInt("C_FONDO_ID"), rs.getString("FONDO"), new Date(0), 2),
			new TipoOperacion(rs.getInt("C_TIPOOPERACION_ID"), rs.getString("TIPOOPERACION"), new Date(0), 2),
			new Persona(rs.getInt("C_PERSONA_ID"), rs.getString("A_NOMBRES"), rs.getString("A_APATERNO"),
					rs.getString("A_AMATERNO"), rs.getString("A_DOCUMENTO"), new Date(0), 2),
			0, rs.getString("A_OBSERVACION"), rs.getString("A_DNI"), rs.getString("A_CLIENTE"));

	@Override
	public Operacion findOne(Long id) {
		Operacion operacion = jdbcTemplate.queryForObject(
				"SELECT O.C_OPERACION_ID,O.A_CODIGO,O.N_MONTO,O.F_FECHAREGISTRO,O.E_ACTIVO,O.F_ESCRITURA,F.C_FONDO_ID,F.A_NOMBRE AS FONDO,OT.C_TIPOOPERACION_ID,OT.A_NOMBRE AS TIPOOPERACION,P.C_PERSONA_ID,P.A_NOMBRES,P.A_APATERNO,P.A_AMATERNO ,P.A_DOCUMENTO , O.A_OBSERVACION,O.A_DNI,O.A_CLIENTE FROM TL_OPERACION O LEFT JOIN TL_PERSONA P ON P.C_PERSONA_ID=O.C_PERSONA_ID INNER JOIN TL_FONDO F ON F.C_FONDO_ID=O.C_FONDO_ID INNER JOIN TL_TIPOOPERACION OT ON OT.C_TIPOOPERACION_ID=O.C_TIPOOPERACION_ID WHERE O.E_ACTIVO=1 AND O.C_OPERACION_ID=? ORDER BY O.C_FONDO_ID, O.C_OPERACION_ID",
				new Long[] { id }, operacionRowMapper);
		return operacion;
	}

	@Override
	public List<Operacion> findAll() {
		List<Operacion> operacion = jdbcTemplate.query(
				"SELECT  O.C_OPERACION_ID,O.A_CODIGO,O.N_MONTO,TO_CHAR(O.F_FECHAREGISTRO,'dd/mm/YYYY') AS F_FECHAREGISTRO,TO_CHAR(O.F_ESCRITURA,'dd/mm/YYYY') AS F_ESCRITURA,O.E_ACTIVO,F.C_FONDO_ID,F.A_NOMBRE AS FONDO,OT.C_TIPOOPERACION_ID,OT.A_NOMBRE AS TIPOOPERACION,'' C_PERSONA_ID,'' A_NOMBRES,'' A_APATERNO,'' A_AMATERNO ,'' A_DOCUMENTO, O.A_OBSERVACION,O.A_DNI,O.A_CLIENTE FROM TL_OPERACION O  INNER JOIN TL_FONDO F ON F.C_FONDO_ID=O.C_FONDO_ID INNER JOIN TL_TIPOOPERACION OT ON OT.C_TIPOOPERACION_ID=O.C_TIPOOPERACION_ID WHERE O.E_ACTIVO=1 AND (O.C_FASE_ID=0 OR O.C_FASE_ID=3) ORDER BY O.C_FONDO_ID,O.C_OPERACION_ID",
				operacionRowMapper);
		return operacion;
	}

	@Override
	public void save(Operacion operacion) {
		jdbcTemplate.update(
				"INSERT INTO TL_OPERACION (A_CODIGO,C_PERSONA_ID,C_FONDO_ID,N_MONTO,C_TIPOOPERACION_ID,F_ESCRITURA,A_DNI,A_CLIENTE)VALUES(?,?,?,?,?,TO_DATE(?, 'dd/mm/yyyy'),?,?)",
				operacion.getCodigo(), 1, operacion.getFondo().getIdFondo(), operacion.getMonto(),
				operacion.getTipoOperacion().getIdTipoOperacion(), operacion.getEscritura(), operacion.getDni(),
				operacion.getCliente());
	}

	@Override
	public void update(Operacion operacion) {
		jdbcTemplate.update(
				"UPDATE TL_OPERACION SET A_CODIGO=?,C_PERSONA_ID=?,C_FONDO_ID=?,N_MONTO=?,C_TIPOOPERACION_ID=?,F_ESCRITURA=TO_DATE(?, 'dd/mm/yyyy'),A_DNI=?,A_CLIENTE=? WHERE C_OPERACION_ID=? ",
				operacion.getCodigo(), 1, operacion.getFondo().getIdFondo(), operacion.getMonto(),
				operacion.getTipoOperacion().getIdTipoOperacion(), operacion.getEscritura(), operacion.getDni(),
				operacion.getCliente(), operacion.getIdOperacion());
	}

	@Override
	public void updateObs(Operacion operacion) {
		jdbcTemplate.update("UPDATE TL_OPERACION SET A_OBSERVACION=? WHERE C_OPERACION_ID=? ",
				operacion.getObservacion(), operacion.getIdOperacion());
	}

	@Override
	public void delete(Operacion operacion) {
		jdbcTemplate.update("UPDATE TL_OPERACION SET E_ACTIVO=0 WHERE C_OPERACION_ID=?", operacion.getIdOperacion());
	}

	@Override
	public void updateOperacionEstado(Operacion operacion) {
		jdbcTemplate.update(
				"UPDATE TL_OPERACION SET C_FASE_ID=2 , C_REPORTERECAUDACION_ID=TL_SEQ_REPORTERECAUDACION.NEXTVAL WHERE C_REPORTERECAUDACION_ID IS NULL AND (C_FASE_ID=0 OR C_FASE_ID=3)");
	}

	@Override
	public void updateOperacionEstadoRevisado(Operacion operacion) {
		jdbcTemplate.update("CALL TL_PKG_TELETRANSFER.TL_SP_REGISTRARREPORTE()");
	}

	@Override
	public void clearOperation() {
		jdbcTemplate.update("CALL TL_PKG_TELETRANSFER.TL_SP_LIMPIA_REGISTR0()");
	}

	@Override
	public void saveOperaciones(Operacion operacion) {
		try {
			jdbcTemplate.update("CALL TL_PKG_TELETRANSFER.TL_SP_REGISTRAOPERACIONES(?)", operacion.getCliente());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateOperacionRechazo(Operacion operacion) {
		jdbcTemplate.update("UPDATE TL_OPERACION SET C_FASE_ID=?,C_REPORTERECAUDACION_ID=0 WHERE C_FASE_ID=?",
				operacion.getFaseNueva(), operacion.getFaseInicial());
		jdbcTemplate.update("UPDATE TL_REPORTERECAUDACION SET E_ACTIVO=0 WHERE C_ESTADOREPORTE_ID=0");
	}

	@Override
	public List<Operacion> findAllEnviados() {
		List<Operacion> operacion = jdbcTemplate.query(
				"SELECT O.C_OPERACION_ID,O.A_CODIGO,O.N_MONTO,TO_CHAR(O.F_FECHAREGISTRO,'dd/mm/YYYY') AS F_FECHAREGISTRO,TO_CHAR(O.F_ESCRITURA,'dd/mm/YYYY') AS F_ESCRITURA, O.E_ACTIVO,F.C_FONDO_ID,F.A_NOMBRE AS FONDO,OT.C_TIPOOPERACION_ID,OT.A_NOMBRE AS TIPOOPERACION,'' C_PERSONA_ID,'' A_NOMBRES,'' A_APATERNO,'' A_AMATERNO ,'' A_DOCUMENTO, O.A_OBSERVACION,O.A_DNI,O.A_CLIENTE FROM TL_OPERACION O INNER JOIN TL_FONDO F ON F.C_FONDO_ID=O.C_FONDO_ID INNER JOIN TL_TIPOOPERACION OT ON OT.C_TIPOOPERACION_ID=O.C_TIPOOPERACION_ID WHERE O.E_ACTIVO=1 AND O.C_FASE_ID=2 ORDER BY O.C_FONDO_ID,O.C_OPERACION_ID",
				operacionRowMapper);
		return operacion;
	}

	@Override
	public List<Operacion> findAllRevisados() {
		List<Operacion> operacion = jdbcTemplate.query(
				"SELECT O.C_OPERACION_ID,O.A_CODIGO,O.N_MONTO,TO_CHAR(O.F_FECHAREGISTRO,'dd/mm/YYYY') AS F_FECHAREGISTRO,TO_CHAR(O.F_ESCRITURA,'dd/mm/YYYY') AS F_ESCRITURA,O.E_ACTIVO,F.C_FONDO_ID,F.A_NOMBRE AS FONDO,OT.C_TIPOOPERACION_ID,OT.A_NOMBRE AS TIPOOPERACION,P.C_PERSONA_ID,P.A_NOMBRES,P.A_APATERNO,P.A_AMATERNO ,P.A_DOCUMENTO, O.A_OBSERVACION,O.A_DNI,O.A_CLIENTE FROM TL_OPERACION O LEFT JOIN TL_PERSONA P ON P.C_PERSONA_ID=O.C_PERSONA_ID INNER JOIN TL_FONDO F ON F.C_FONDO_ID=O.C_FONDO_ID INNER JOIN TL_TIPOOPERACION OT ON OT.C_TIPOOPERACION_ID=O.C_TIPOOPERACION_ID WHERE O.E_ACTIVO=1 AND O.C_FASE_ID=2 ORDER BY  O.C_FONDO_ID, O.C_OPERACION_ID",
				operacionRowMapper);
		return operacion;
	}

	@Override
	public List<Operacion> findAllReporte(Long id) {
		List<Operacion> operacion = jdbcTemplate.query(
				"SELECT O.C_OPERACION_ID,O.A_CODIGO,O.N_MONTO,TO_CHAR(O.F_FECHAREGISTRO,'dd/mm/YYYY') AS F_FECHAREGISTRO,TO_CHAR(O.F_ESCRITURA,'dd/mm/YYYY') AS F_ESCRITURA,O.E_ACTIVO,F.C_FONDO_ID,F.A_NOMBRE AS FONDO,OT.C_TIPOOPERACION_ID,OT.A_NOMBRE AS TIPOOPERACION,P.C_PERSONA_ID,P.A_NOMBRES,P.A_APATERNO,P.A_AMATERNO ,P.A_DOCUMENTO, O.A_OBSERVACION,O.A_DNI,O.A_CLIENTE FROM TL_OPERACION O LEFT JOIN TL_PERSONA P ON P.C_PERSONA_ID=O.C_PERSONA_ID INNER JOIN TL_FONDO F ON F.C_FONDO_ID=O.C_FONDO_ID INNER JOIN TL_TIPOOPERACION OT ON OT.C_TIPOOPERACION_ID=O.C_TIPOOPERACION_ID WHERE O.E_ACTIVO=1 AND O.C_FASE_ID=2 AND O.C_REPORTERECAUDACION_ID=? ORDER BY O.C_FONDO_ID, O.C_OPERACION_ID ",
				new Long[] { id }, operacionRowMapper);
		return operacion;
	}

	@Override
	public List<Operacion> findAllFondo(Long id) {
		List<Operacion> operacion = jdbcTemplate.query(
				"SELECT * FROM (  SELECT O.C_OPERACION_ID    ,O.A_CODIGO    ,  O.N_MONTO    , TO_CHAR(O.F_FECHAREGISTRO,'dd/mm/YYYY') AS F_FECHAREGISTRO    ,  TO_CHAR(O.F_ESCRITURA,'dd/mm/YYYY') AS F_ESCRITURA ,  	O.E_ACTIVO,F.C_FONDO_ID    ,  F.A_NOMBRE AS FONDO    ,  OT.C_TIPOOPERACION_ID    ,  OT.A_NOMBRE AS TIPOOPERACION    ,  	1 C_PERSONA_ID    ,  '' A_NOMBRES    ,  '' A_APATERNO    ,  '' A_AMATERNO    ,  '' A_DOCUMENTO    ,   O.A_OBSERVACION    ,  O.A_DNI    ,  O.A_CLIENTE  FROM  TL_OPERACION O INNER JOIN TL_FONDO F ON F.C_FONDO_ID=O.C_FONDO_ID INNER JOIN TL_TIPOOPERACION OT ON OT.C_TIPOOPERACION_ID=O.C_TIPOOPERACION_ID WHERE F.C_FONDO_ID=? AND O.E_ACTIVO=1 AND O.C_FASE_ID=1 OR O.C_FASE_ID=4  UNION ALL  SELECT    1 AS C_OPERACION_ID    ,  A_CODIGO    ,  1 AS N_MONTO    ,  TO_CHAR(SYSDATE,'dd/mm/YYYY') AS F_FECHAREGISTRO , TO_CHAR(SYSDATE,'dd/mm/YYYY') AS F_ESCRITURA    ,  1 AS E_ACTIVO,1 AS C_FONDO_ID,'FONDO' AS FONDO ,  1 AS C_TIPOOPERACION_ID    ,   'TIPOOPE' AS TIPOOPERACION    ,  1 AS C_PERSONA_ID    ,  'NOMBRES' A_NOMBRES ,  'PATERNO'AS A_APATERNO    ,  'MATERNO' A_AMATERNO    ,  'DOC' A_DOCUMENTO    ,  'OBS' AS  A_OBSERVACION    ,  'DNI' AS A_DNI    , A_DETALLE AS A_CLIENTE  FROM  TL_CODIGO_AREA  WHERE E_ACTIVO=1) ORDER BY A_CODIGO",
				new Long[] { id }, operacionRowMapper);
		return operacion;
	}

}
