package pop.teletransfer.controlador;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.portable.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pop.teletransfer.dominio.CodigoArea;
import pop.teletransfer.dominio.Operacion;
import pop.teletransfer.dominio.Parametro;
import pop.teletransfer.dominio.ReporteRecaudacion;
import pop.teletransfer.repositorio.CodigoAreaRepositorio;
import pop.teletransfer.repositorio.OperacionRepositorio;
import pop.teletransfer.repositorio.ParametroRepositorio;
import pop.teletransfer.repositorio.ReporteRecaudacionRepositorio;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

@RestController
@RequestMapping("/reporte")
@Component
public class ReporteRecaudacionControlador {
	@Autowired
	private ReporteRecaudacionRepositorio reporteRecaudacionRepositorio;

	@Autowired
	private OperacionRepositorio operacionRepositorio;

	@Autowired
	private CodigoAreaRepositorio codigoAreaRepositorio;

	@Autowired
	ParametroRepositorio parametroRepositorio;
	
	@RequestMapping(method = GET)
	public Resultado findAll() {
		Resultado oResultado = new Resultado();
		//hha201471220: validacion cuando no tiene ninguna carga
		List<ReporteRecaudacion> oListReporteRecaudacion = reporteRecaudacionRepositorio.findAll();
		if(oListReporteRecaudacion.size()==0)
		{
			ReporteRecaudacion oReporteRecaudacion= new ReporteRecaudacion();
			oReporteRecaudacion.setActivo(0);
			oReporteRecaudacion.setEstado("");
			oReporteRecaudacion.setFechaRegistro("AUN SIN CARGA");
			oListReporteRecaudacion.add(oReporteRecaudacion);
		}
		oResultado.setData(oListReporteRecaudacion);
		oResultado.setRecordsTotal(oResultado.getData().size());
		oResultado.setRecordsFiltered(oResultado.getData().size());
		return oResultado;
	}

	private static final int BYTES_DOWNLOAD = 1024;

	@RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
	public void doDownload(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/plain");
			OutputStream os = response.getOutputStream();

			//List<CodigoArea> codigoAreas = codigoAreaRepositorio.findAll();
			List<Operacion> operaciones = operacionRepositorio.findAllFondo(id);

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			/* CABECERA */
			String cabeceraCodigo = "";
			String cabeceraFondo = "";
			String cabeceraFecha = "";
			String cabeceraItems = "";
			String cabeceraResto = "";
			/* CABECERA */
			/* CUERPO */
			String cuerpoCodigo = "DD19301958058";
			String cuerpoOperacion = "";
			String cuerpoCliente = "";
			String cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
			String codigoOperacion = "";
			String clienteOperacion = "";
			/* CUERPO */
			switch (id.toString()) {
			case "1":/* Fondo Capital Emprendedor */
				response.setHeader("Content-Disposition", "attachment;filename=CREP.txt");
				cabeceraCodigo = "CC19301958058";
				cabeceraFondo = "PFONDO CAPITAL EMPRENDEDOR               ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				os.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				os.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19301958058";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					os.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					os.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "2":/* Fondo MYPE */
				response.setHeader("Content-Disposition", "attachment;filename=CDPG.txt");
				cabeceraCodigo = "CC19301737299";
				cabeceraFondo = "PFONDO MYPE-TCHN,FONDO INVERSION PRIVADO ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				os.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				os.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19301737299";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					os.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					os.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "3":/* Fondo Perez Hidalgo */
				response.setHeader("Content-Disposition", "attachment;filename=CREP.txt");
				cabeceraCodigo = "CC19302093609";
				cabeceraFondo = "PFONDO PEREZ HIDALGO,FONDO DE INVERSION  ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				os.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				os.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19302093609";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					os.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					os.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "4":/* Fondo Popular 1 - Renta Mixta */
				response.setHeader("Content-Disposition", "attachment;filename=CREP.txt");
				cabeceraCodigo = "CC19101613451";
				cabeceraFondo = "PFONDO POPULAR 1 - RENTA MIXTA           ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				os.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				os.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19101613451";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					os.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					os.write(System.getProperty("line.separator").getBytes());
				}
				break;
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void Descarga(Long id) {
		try {
			List<Parametro> parametros = parametroRepositorio.findAll();
			Map<String, String> params = new HashMap<String, String>();
			for (Parametro p : parametros) {
				params.put(p.getClave(), p.getValor());
			}
			
			//response.setContentType("text/plain");

			FileOutputStream fop = null;
			File file;
			file = new File(params.get("adjunto.dir") + "CREP(" + id + ").txt");
			fop = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
			}

			//OutputStream os = response.getOutputStream();

			//List<CodigoArea> codigoAreas = codigoAreaRepositorio.findAll();
			List<Operacion> operaciones = operacionRepositorio.findAllFondo(Long.parseLong(id + ""));

			/*for (CodigoArea codigoArea : codigoAreas) {
				Operacion operacion = new Operacion();
				operacion.setCodigo(codigoArea.getCodigoArea());
				operacion.setCliente(codigoArea.getDetalle());
				operaciones.add(operacion);
			}*/

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			/* CABECERA */
			String cabeceraCodigo = "";
			String cabeceraFondo = "";
			String cabeceraFecha = "";
			String cabeceraItems = "";
			String cabeceraResto = "";
			/* CABECERA */
			/* CUERPO */
			String cuerpoCodigo = "DD19301958058";
			String cuerpoOperacion = "";
			String cuerpoCliente = "";
			String cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
			String codigoOperacion = "";
			String clienteOperacion = "";
			/* CUERPO */
			switch (id.toString()) {
			case "1":/* Fondo Capital Emprendedor */
				//response.setHeader("Content-Disposition", "attachment;filename=CREP.txt");
				cabeceraCodigo = "CC19301958058";
				cabeceraFondo = "PFONDO CAPITAL EMPRENDEDOR SOLES         ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";

				// fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems +
				// cabeceraResto).getBytes());

				fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				fop.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19301958058";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					fop.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					fop.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "2":/* Fondo MYPE */
				//response.setHeader("Content-Disposition", "attachment;filename=CREP.txt");
				cabeceraCodigo = "CC19301737299";
				cabeceraFondo = "PFONDO MYPE-TCHN,FONDO INVERSION PRIVADO ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				fop.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19301737299";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					fop.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					fop.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "3":/* Fondo Perez Hidalgo */
				//response.setHeader("Content-Disposition", "attachment;filename=CREP.txt");
				cabeceraCodigo = "CC19302093609";
				cabeceraFondo = "PFONDO PEREZ HIDALGO,FONDO DE INVERSION  ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				fop.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19302093609";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					fop.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					fop.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "4":/* Fondo Popular 1 - Renta Mixta */
				//response.setHeader("Content-Disposition", "attachment;filename=CREP.txt");
				cabeceraCodigo = "CC19101613451";
				cabeceraFondo = "PFONDO POPULAR 1 - RENTA MIXTA SOLES     ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				fop.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19101613451";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					fop.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					fop.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "5":/* Fondo Capital Emprendedor Dolares*/
				cabeceraCodigo = "CC19311957231";
				cabeceraFondo = "PFONDO CAPITAL EMPRENDEDOR DOLARES       ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";

				fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				fop.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19311957231";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					fop.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					fop.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "6":/* Fondo Popular 1 - Renta Mixta Dolares*/
				cabeceraCodigo = "CC19111607503";
				cabeceraFondo = "PFONDO POPULAR 1 - RENTA MIXTA DOLARES   ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				fop.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19111607503";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					fop.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					fop.write(System.getProperty("line.separator").getBytes());
				}
				break;
			case "7":/* Fondo Mype-TCHN dólares*/
				cabeceraCodigo = "CC19311733491";
				cabeceraFondo = "PFONDO MYPE - TCHN DOLARES               ";
				cabeceraFecha = dateFormat.format(date);
				cabeceraItems = ("000000000" + operaciones.size()).substring((operaciones.size() + "").length());
				cabeceraResto = "000000000000000                                                                                                                10";
				fop.write((cabeceraCodigo + cabeceraFondo + cabeceraFecha + cabeceraItems + cabeceraResto).getBytes());
				fop.write(System.getProperty("line.separator").getBytes());
				cuerpoCodigo = "DD19311733491";
				cuerpoOperacion = "";
				cuerpoCliente = "";
				cuerpoResto = "0000000000000000000000000      000000000000000000000000000000000000000000000000000000000000000000000000";
				codigoOperacion = "";
				clienteOperacion = "";
				for (Operacion operacion : operaciones) {
					codigoOperacion = operacion.getCodigo();
					clienteOperacion = operacion.getCliente();
					cuerpoCliente = clienteOperacion
							+ "                                                                      "
									.substring(clienteOperacion.length());
					cuerpoOperacion = ("00000000000000" + codigoOperacion).substring(codigoOperacion.length());
					fop.write((cuerpoCodigo + cuerpoOperacion + cuerpoCliente + cuerpoResto).getBytes());
					fop.write(System.getProperty("line.separator").getBytes());
				}
				break;
			}
			
			fop.flush();
			fop.close();
			System.out.println("finalizo de exportar archivos de codigos con formatos de banco");

		} catch (Exception ex) {
			System.out.println("error Descarga");
			System.out.println(ex);
			//System.out.println(e.getMessage());
		}
	}

	class Resultado {
		List<ReporteRecaudacion> data;
		Double sumatoria;
		int recordsTotal;
		int recordsFiltered;

		public List<ReporteRecaudacion> getData() {
			return data;
		}

		public void setData(List<ReporteRecaudacion> data) {
			this.data = data;
		}

		public Double getSumatoria() {
			return sumatoria;
		}

		public void setSumatoria(Double sumatoria) {
			this.sumatoria = sumatoria;
		}

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

	}
}
