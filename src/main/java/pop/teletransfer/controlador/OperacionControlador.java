package pop.teletransfer.controlador;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pop.teletransfer.comun.servicio.ComunServicio;
//import pop.teletransfer.comun.servicio.ComunServicio;
import pop.teletransfer.dominio.CodigoArea;
import pop.teletransfer.dominio.Fondo;
import pop.teletransfer.dominio.Operacion;
import pop.teletransfer.dominio.TipoOperacion;
import pop.teletransfer.mail.dominio.Mail;
import pop.teletransfer.mail.servicio.MailServicio;
import pop.teletransfer.mail.servicio.PlantillaCorreo;
import pop.teletransfer.repositorio.CodigoAreaRepositorio;
import pop.teletransfer.repositorio.FondoRepositorio;
import pop.teletransfer.repositorio.OperacionRepositorio;
//import pop.teletransfer.repositorio.OperacionRepositorio;
import pop.teletransfer.repositorio.ReceptorRepositorio;
import pop.teletransfer.repositorio.ReporteRecaudacionRepositorio;
import pop.teletransfer.repositorio.TipoOperacionRepositorio;

import java.util.ArrayList;

import java.sql.Date;
import java.util.Iterator;

@EnableAsync
@RestController
@RequestMapping("/operacion")
public class OperacionControlador {
	@Autowired
	private OperacionRepositorio operacionRepositorio;

	@Autowired
	private FondoRepositorio fondoRepositorio;

	@Autowired
	private TipoOperacionRepositorio tipoOperacionRepositorio;
	@Autowired
	private ReceptorRepositorio receptorRepositorio;

	@Autowired
	private MailServicio mailServicio;

	@Autowired
	private ComunServicio comunServicio;

	@Autowired
	private Mail mail;

	@Autowired
	private PlantillaCorreo plantillaCorreo;

	@Autowired
	private Operacion operacion;

	@Autowired
	private CodigoAreaRepositorio codigoAreaRepositorio;

	@Autowired
	private ReporteRecaudacionControlador reporteRecaudacionControlador;

	final String emisorCorreo = "popular-sistema@popular-safi.com";
	final String cabeceraCorreo = "POPULAR-SISTEMAS: PROCESO DE TELETRANFERENCIA";

	@RequestMapping(value = "/{id}", method = GET)
	public Resultado findOne(@PathVariable Long id) {
		Resultado oResultado = new Resultado();

		if (id.equals(0)) {
			oResultado.setData(new ArrayList<Operacion>());
		} else {
			oResultado.setData(operacionRepositorio.findAllReporte(id));
		}
		oResultado.setRecordsTotal(oResultado.getData().size());
		oResultado.setRecordsFiltered(oResultado.getData().size());
		double sumador = (double) 0;
		for (Operacion o : oResultado.getData()) {
			sumador += o.getMonto();
		}
		oResultado.setSumatoria(Math.round(sumador * 100) / 100.0d);
		return oResultado;
	}

	@RequestMapping(method = GET)
	public Resultado findAll() {
		Resultado oResultado = new Resultado();
		oResultado.setData(operacionRepositorio.findAll());
		oResultado.setRecordsTotal(oResultado.getData().size());
		oResultado.setRecordsFiltered(oResultado.getData().size());
		double sumador = (double) 0;
		for (Operacion o : oResultado.getData()) {
			sumador += o.getMonto();
		}
		oResultado.setSumatoria(Math.round(sumador * 100) / 100.0d);
		return oResultado;
	}

	@RequestMapping(value = "/registrado", method = GET)
	public Resultado findAllEnviados() { // ESTADO REGISTRADO PRESTACLUB
		Resultado oResultado = new Resultado();
		oResultado.setData(operacionRepositorio.findAllRevisados());
		oResultado.setRecordsTotal(oResultado.getData().size());
		oResultado.setRecordsFiltered(oResultado.getData().size());
		double sumador = (double) 0;
		for (Operacion o : oResultado.getData()) {
			sumador += o.getMonto();
		}
		oResultado.setSumatoria(Math.round(sumador * 100) / 100.0d);
		return oResultado;
	}

	@RequestMapping(value = "/revisado", method = GET)
	public Resultado findAllRevisados() { // ESTADO REGISTRADO PRESTACLUB
		Resultado oResultado = new Resultado();
		oResultado.setData(operacionRepositorio.findAllEnviados());
		oResultado.setRecordsTotal(oResultado.getData().size());
		oResultado.setRecordsFiltered(oResultado.getData().size());
		double sumador = (double) 0;
		for (Operacion o : oResultado.getData()) {
			sumador += o.getMonto();
		}
		oResultado.setSumatoria(Math.round(sumador * 100) / 100.0d);
		return oResultado;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Resultado handleFileUpload(@RequestParam("file") MultipartFile file) throws UnsupportedEncodingException {
		Resultado oResultado = new Resultado();
		String fondonombre = "";
		operacionRepositorio.clearOperation();
		if (!file.isEmpty()) {
			try {
				XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
				XSSFWorkbook test = new XSSFWorkbook();
				XSSFSheet sheet = wb.getSheetAt(0);
				XSSFRow row;
				XSSFCell cell;
				Iterator rows = sheet.rowIterator();
				int contadorFilas = 0;
				List<Operacion> listaOperacion = new ArrayList<>();
				boolean flagBlancos = false;
				while (rows.hasNext()) {
					row = (XSSFRow) rows.next();
					contadorFilas++;
					if (contadorFilas > 1) {
						Iterator cells = row.cellIterator();
						int contadorColumnas = 0;
						Operacion operacion = new Operacion();

						while (cells.hasNext() && !flagBlancos) {
							contadorColumnas++;
							cell = (XSSFCell) cells.next();
							switch (contadorColumnas) {
							case 1:
								if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {

									operacion.setCodigo(cell.getStringCellValue());

								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									operacion.setCodigo(Math.round(cell.getNumericCellValue()) + "");
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
									flagBlancos = true;

								}
								break;
							case 22:
								TipoOperacion tipoOperacion = new TipoOperacion();
								if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && !flagBlancos) {

									switch (cell.getStringCellValue()) {
									case "Nueva Solicitud":
										tipoOperacion = new TipoOperacion(1, "", null, 1);
										break;
									case "Ampliacion":
										tipoOperacion = new TipoOperacion(2, "", null, 1);
										break;
									case "Refinanciamiento":
										tipoOperacion = new TipoOperacion(3, "", null, 1);
										break;
									}
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								} else {
									tipoOperacion.setNombre("");
								}
								operacion.setTipoOperacion(tipoOperacion);
								break;
							case 33:
								if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
									operacion.setEscritura(cell.getStringCellValue());
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
									operacion.setEscritura(formatter.format(cell.getDateCellValue()));
								} else {
									operacion.setEscritura("");
								}
								break;
							case 44:
								if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
									operacion.setMonto(Double.parseDouble(cell.getStringCellValue()));
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									operacion.setMonto(cell.getNumericCellValue());
								} else {
									operacion.setMonto(Double.parseDouble("0"));
								}
								break;
							case 2:
								Fondo fondo = new Fondo();
								if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
									switch (cell.getStringCellValue().toString().toUpperCase()) {
									case "FONDO CAPITAL EMPRENDEDOR SOLES":
										fondo = new Fondo(1, "", null, 1);
										// EMPRENDEDOR";
										break;
									case "FONDO MYPE TCHN":
										fondo = new Fondo(2, "", null, 1);
										// INVERSION PRIVADO";
										break;
									case "FONDO PEREZ HIDALGO":
										fondo = new Fondo(3, "", null, 1);
										// HIDALGO,FONDO DE INVERSION";
										break;
									case "FONDO POPULAR 1 - RM SOLES":
										fondo = new Fondo(4, "", null, 1);
										// RENTA MIXTA";
										break;
									case "FONDO CAPITAL EMPRENDEDOR DOLARES":
										fondo = new Fondo(5, "", null, 1);
										// RENTA MIXTA";
										break;
									case "FONDO POPULAR 1 - RM DOLARES":
										fondo = new Fondo(6, "", null, 1);
										// RENTA MIXTA";
										break;
									case "FONDO MYPE DOLARES":
										fondo = new Fondo(7, "", null, 1);
										// MYPE TCHN DOLARES";
										break;
									}
									
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								} else {
								}
								operacion.setFondo(fondo);
								break;
							case 66:
								if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
									operacion.setDni(cell.getStringCellValue().trim());
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									String documento = (cell.getNumericCellValue() + "").trim();
									documento = documento.substring(0, 1)
											+ documento.substring(2, (documento.length() - 2));
									operacion.setDni(documento);
								} else {
								}
								break;
							case 3:
								if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
									operacion.setCliente(
											comunServicio.ValidaCaractereEspeciales((cell.getStringCellValue())));
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								} else {
								}
								break;
							}
							operacion.setIdOperacion(1);
						}
						listaOperacion.add(operacion);
					}
				}

				oResultado.setData(listaOperacion);
				oResultado.setRecordsTotal(listaOperacion.size());
				oResultado.setRecordsFiltered(listaOperacion.size());
				
/*				double sumador = (double) 0;
				for (Operacion o : listaOperacion) {
					sumador += o.getMonto();
				}
				oResultado.setSumatoria(Math.round(sumador * 100) / 100.0d);
				System.out.println(oResultado.getSumatoria());
*/
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getName())));

				stream.close();
			} catch (Exception e) {
				System.out.println("error de carga excel");
				System.out.println("This is error : ");
				System.out.println(e);
			}

		} else {
			List<Operacion> listaOperacion = new ArrayList<>();
			oResultado.setData(listaOperacion);
			oResultado.setRecordsTotal(listaOperacion.size());
			oResultado.setRecordsFiltered(listaOperacion.size());
			oResultado.setSumatoria((double) 0);
		}
		ProcesaTabla(oResultado);
		return oResultado;
	}

	void ProcesaTabla(Resultado oResultado) {
		try {
			String cadena = "";
			String cadenaAnterior = "";
			int sumador = 0;
			String valor = "";
			for (Operacion operacion : oResultado.getData()) {
				operacion.setTipoOperacion(new TipoOperacion(1, "", null, 1));
				operacion.setEscritura("01/01/17");
				operacion.setDni("12345678");
				operacion.setMonto(1.0);

				valor = operacion.getFondo().getIdFondo() + "$" + operacion.getTipoOperacion().getIdTipoOperacion()
						+ "$" + operacion.getCodigo().toString() + "$" + operacion.getEscritura().toString() + "$0$"
						+ operacion.getDni() + "$" + operacion.getCliente() + "$" + operacion.getMonto().toString();
				cadenaAnterior = cadena;
				if (sumador == 0) {
					cadena += valor;
				} else {
					cadena = cadena + ";" + valor;
				}
				sumador++;
				byte[] b = cadena.getBytes("UTF-8");
				if (b.length > 4000) {
					operacion.setCliente(cadenaAnterior);
					operacionRepositorio.saveOperaciones(operacion);
					cadena = valor;
				}
			}

			List<CodigoArea> data = codigoAreaRepositorio.findAll();
			operacion.setCliente(cadena);
			operacionRepositorio.saveOperaciones(operacion);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Operacion saveOperacion(@RequestBody Operacion ope) {
		ope.setMonto(Math.round(ope.getMonto() * 100) / 100.0d);
		ope.setCliente(comunServicio.ValidaCaractereEspeciales(ope.getCliente()));
		operacionRepositorio.save(ope);
		return operacion;
	}

	@RequestMapping(value = "/actualizacion", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Operacion updateOperacion(@RequestBody Operacion ope) {
		ope.setCliente(comunServicio.ValidaCaractereEspeciales(ope.getCliente()));
		operacionRepositorio.update(ope);
		return ope;
	}

	@RequestMapping(value = "/actualizacionObs", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Operacion updateObsOperacion(@RequestBody Operacion ope) {
		operacionRepositorio.updateObs(ope);
		return ope;
	}

	@RequestMapping(value = "/eliminacion", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Operacion deleteOperacion(@RequestBody Operacion ope) {
		operacionRepositorio.delete(ope);
		return ope;
	}

	@RequestMapping(value = "/registraenvio", method = RequestMethod.POST)
	public Resultado updateOperacionEstado(@RequestBody Operacion ope, HttpServletResponse response) {
		Resultado oResultado = new Resultado();
		operacionRepositorio.updateOperacionEstadoRevisado(ope);
		NotificarEnvio1();
		return oResultado;
	}

	void NotificarEnvio1() {

		List<Fondo> fondos = fondoRepositorio.findAll();
		for (Fondo fondo : fondos) {
			reporteRecaudacionControlador.Descarga(Long.parseLong(fondo.getIdFondo() + ""));
		}

		mail.setAsunto("POPULAR-SISTEMAS: ENVIO REPORTE DE OPERACIONES PRESTACLUB.");
		plantillaCorreo.setCabecera(cabeceraCorreo);
		plantillaCorreo.setContenido("SE HA REGISTRADO EL SIGUIENTE REPORTE PARA SU REVISION.");
		mail.setContenido(plantillaCorreo.RetornaPlantilla(operacionRepositorio.findAllEnviados()));
		mail.setEmisor(emisorCorreo);
		mailServicio.Notificar(mail);
	}

	@RequestMapping(value = "/revisaenvio", method = RequestMethod.POST)
	public Operacion updateOperacionEstadoRevisado(@RequestBody Operacion ope) { /* ESTADO REGISTRADO */
		operacionRepositorio.updateOperacionEstadoRevisado(ope);
		mail.setAsunto("POPULAR-SISTEMAS: REVISION REPORTE DE OPERACIONES POPULAR");
		PlantillaCorreo plantillaCorreo = new PlantillaCorreo();
		plantillaCorreo.setCabecera(cabeceraCorreo);
		plantillaCorreo.setContenido("SE HA REGISTRADO EL SIGUIENTE REPORTE PARA SU REVISION");
		mail.setContenido(plantillaCorreo.RetornaPlantilla(operacionRepositorio.findAllRevisados()));
		mail.setEmisor(emisorCorreo);
		mailServicio.Notificar(mail);
		return ope;
	}

	@RequestMapping(value = "/rechazaenvio", method = RequestMethod.POST)
	public Operacion updateOperacionRechazo(@RequestBody Operacion ope) {
		operacion.setFaseNueva(3);
		operacion.setFaseInicial(1);
		operacionRepositorio.updateOperacionRechazo(operacion);
		mail.setAsunto("POPULAR-SISTEMAS: RECHAZO REPORTE DE OPERACIONES PRESTACLUB");
		PlantillaCorreo plantillaCorreo = new PlantillaCorreo();
		plantillaCorreo.setCabecera(cabeceraCorreo);
		plantillaCorreo.setContenido("SE HA REGISTRADO EL SIGUIENTE REPORTE PARA SU REVISION");
		mail.setContenido(plantillaCorreo.RetornaPlantilla(operacionRepositorio.findAll()));
		mail.setEmisor(emisorCorreo);
		mailServicio.Notificar(mail);
		return ope;
	}

	@RequestMapping(value = "/devuelveenvio", method = RequestMethod.POST)
	public Operacion updateOperacionDevolucion(@RequestBody Operacion ope) {

		operacion.setFaseNueva(1);
		operacion.setFaseInicial(2);
		operacionRepositorio.updateOperacionRechazo(operacion);
		mail.setAsunto("POPULAR-SISTEMAS: DEVOLUCION REPORTE DE OPERACIONES POPULAR");
		PlantillaCorreo plantillaCorreo = new PlantillaCorreo();
		plantillaCorreo.setCabecera(cabeceraCorreo);
		plantillaCorreo.setContenido("SE HA REGISTRADO EL SIGUIENTE REPORTE PARA SU REVISION");
		mail.setContenido(plantillaCorreo.RetornaPlantilla(operacionRepositorio.findAllEnviados()));
		mailServicio.Notificar(mail);
		return ope;
	}

	class Resultado {
		List<Operacion> data;
		Double sumatoria;

		public Double getSumatoria() {
			return sumatoria;
		}

		public void setSumatoria(Double sumatoria) {
			this.sumatoria = sumatoria;
		}

		int recordsTotal;

		public int getRecordsTotal() {
			return recordsTotal;
		}

		public void setRecordsTotal(int recordsTotal) {
			this.recordsTotal = recordsTotal;
		}

		public int getRecordsFiltered() {
			return recordsFiltered;
		}

		public void setRecordsFiltered(int recordsFiltered) {
			this.recordsFiltered = recordsFiltered;
		}

		int recordsFiltered;

		public List<Operacion> getData() {
			return data;
		}

		public void setData(List<Operacion> data) {
			this.data = data;
		}
	}
}
