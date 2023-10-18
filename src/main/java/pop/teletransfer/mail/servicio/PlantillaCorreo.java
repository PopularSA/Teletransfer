package pop.teletransfer.mail.servicio;

import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Component;

import pop.teletransfer.dominio.Operacion;

@Component
public class PlantillaCorreo {
	String cuerpo;
	String cabecera;
	String contenido;

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getCabecera() {
		return cabecera;
	}

	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String RetornaPlantilla(List<Operacion> operaciones) {
		return "<html>"
				+ "<head><style>table {    border-collapse: collapse;    width: 100%;}th, td {    text-align: left;    padding: 8px;}tr:nth-child(even){background-color: #f2f2f2}th {    background-color: #4CAF50;    color: white;} .pie{    background-color: #4CAF50;    color: white;}</style></head>"
				+ "<body>"
				+ "<table width='100%' cellspacing='0' cellpadding='0' border='0' bgcolor='#ffffff'> <tbody> <tr> <td valign='top' align='center'> <font face='Arial, Helvetica, sans-serif' size='12px'></font> <table width='632' cellspacing='0' cellpadding='0' bgcolor='' style='border:1px solid #dddddd;background-color:#01355c'> <tbody> <tr valign='top' style='height:80px'> <td width='25' bgcolor=''> </td> <td valign='middle' style='font-family:Arial,Helvetica,sans-serif;color:#f89223;font-size:28px;text-align:left'> <br> <b>"
				+ this.getCabecera()
				+ "</b> </td> <td style='width:80px'> <a> <img height='80' width='62' border='0' src='https://ci3.googleusercontent.com/proxy/h3lDf_AQiKtlnztd5AniIj9VwPYh-5QH8VaeQmVw7ZovCswbtVMzq4dLsjfxbj3EnaD7ZqGpXRKX2_NOKqH7lfI4SNSfGA3Iz8B78jZZOTjyZNjolEeRGY_PDe1T=s0-d-e1-ft#http://creately.com/newsletter/new_template_2012/creately_logo_icon.png' alt='Creately' style='display:block;color:#4c9ac9' class='CToWUd'></a> </td> </tr> </tbody> </table> <table width='620' cellspacing='0' cellpadding='0' bgcolor='#ffffff' style='border:1px solid #ddd;border-top:none'> <tbody> <tr> <td style='height:30px;float:left;width:630px'> <table cellspacing='0' cellpadding='0'> <tbody> <tr> <td width='25px' style='height:33px'> <a href='http://www.facebook.com/prestaclub' target='_blank'> <img style='text-decoration:none;border:0' src='https://ci4.googleusercontent.com/proxy/cNkDi45kkZJErkaScuqmLVCqQQLpR2yRgiL1lMjnjh07GiprfJYvp29zjurGz1Y25a27Qq2UCJ9YA0gLUXrPphcF7cWr5mnkf5tQRsE4XGwvhU9pjvbuQ1zu=s0-d-e1-ft#http://f.i.wix.com/i/11/2076041122/Social_Icon_2013_New_Facebook.png' alt='' class='CToWUd'> </a> </td> <td width='25px' style='height:33px'> <a shape='rect' style='text-decoration:none'> <img style='text-decoration:none;border:0' src='https://ci5.googleusercontent.com/proxy/gS-2MSVGtlIb7Npx9tEd7fgJ9oIUOwaz7nqoo_x5EZdKbfbvxBnudHgnWjcNEuNgyDiwTR3oRhZVvLcSjp7aprVnhE0XRT4IsOyVMZrgD__IW-OdYuBLrok=s0-d-e1-ft#http://f.i.wix.com/i/11/2076041122/Social_Icon_2013_New_Tweeter.png' alt='' class='CToWUd'> </a> </td> <td width='25px' style='height:33px'> <a shape='rect'> <img style='text-decoration:none;border:0' src='https://ci3.googleusercontent.com/proxy/B2YuRGO92TteUjI4vnLP4OxdW2fT1dikBNud-m_YhP2PCHpCiApk8k7xDAjAzIVn9KBkKBEanRzNop2x7xbUTkmTRnLQBmSxnC9c7tGY8GBtX4iIeJvSQQ=s0-d-e1-ft#http://f.i.wix.com/i/11/2076041122/Social_Icon_2013_New_Google.png' alt='' class='CToWUd'> </a> </td> <td width='25px'> <a shape='rect'> <img style='text-decoration:none;border:0' src='https://ci5.googleusercontent.com/proxy/V3DRjT02YWL7IA_ta9rBMyP_Vdm8NE_DBoQhrbo8-FXwqaQJJY8isgYHm0fmzGlCWxi08WWBztS73ve0ic-2UUbXd_XgCFkLoPJBTiVK1VEdbH1vAM8c2PxcKA=s0-d-e1-ft#http://f.i.wix.com/i/11/2076041122/Social_Icon_2013_New_Pinterest.png' alt='' class='CToWUd'> </a> </td> <td width='25px' style='height:33px'> <a shape='rect'> <img style='text-decoration:none;border:0' alt='' src='https://ci3.googleusercontent.com/proxy/pfgkkZZBXLr5AKZfsKUDD7osguGAgHvy-27kuWoQbYc2ASdTBRKC14ogqoRTLifoW1QStWHw4WMiBVJkVE0tF_7IAmZ_Y_4MZkr0g8y5v73LSfgwtno=s0-d-e1-ft#http://f.i.wix.com/i/11/2076041122/Social_Icon_2013_New_Blog.png' class='CToWUd'> </a> </td> <td width='25px' style='height:33px'> <a shape='rect'> <img style='text-decoration:none;border:0' src='https://ci6.googleusercontent.com/proxy/SVKR3_gspeqsyIKeSSD_N2ORDQPt18RTRvZ3mwTENU1RyQfGm-3oMkqyq1AhRbBLtpPiL_R4GitgNKIeMnZ0NhGkBLubzywjD__d0VdBuvE=s0-d-e1-ft#http://f.i.wix.com/i/11/2076041122/linkedin-desktop_01.png' alt='' class='CToWUd'> </a> </td> </tr> </tbody> </table> </td> </tr> <tr> <td style='height:20px;width:630px'> <table width='620' cellspacing='0' cellpadding='5' border='0'> <tbody> <tr> <td width='15'> </td> <td>"
				+ "<p align='justify' style='margin-bottom:13px!important;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#4c4c4c;text-align:justify'>"
				+ "Estimado usuario," + "</p>"
				+ "<p align='justify' style='margin-bottom:13px!important;font-family:Arial,Helvetica,sans-serif;font-size:18px;color:#003366;text-align:justify'>"
				+ this.getContenido()
				+ "<p align='justify' style='margin-bottom:13px!important;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#4c4c4c;text-align:justify'>"
				+ " Este correo ha sido generado de manera automatica por los sistema de POPULAR-SAFI."
				+ "</p> </td> <td style='width:6px'> </td> </tr>" + " <tr><td width='15'> </td><td>"
				+ RetornaTabla(operaciones) + "</td></tr>"
				+ "</tbody> </table> <table width='620' cellspacing='0' cellpadding='5' border='0' bgcolor='#ffffff' style='margin-top:0px!important;padding:0'> <tbody> <tr> <td width='45'> </td> </tr> </tbody> </table> <table width='630' cellspacing='0' cellpadding='0' border='0' bgcolor='#f1f1f1' style='border-top:1px solid #ddd;border-bottom:1px solid #ddd;height:60px'> <tbody> <tr> <td> <table cellspacing='0' cellpadding='0' border='0' style='margin-top:0;padding-top:0'> <tbody> <tr> <td width='20' bgcolor='#f1f1f1'> </td> <td bgcolor='#f1f1f1' style='height:50px;width:63px;padding-bottom:10px'> <p align='justify' style='margin:0;font-family:Arial,Helvetica,sans-serif;color:#4c4c4c;text-align:center;font-weight:bold;font-size:11px;width:63px;margin-bottom:0'> <font face='Arial, Helvetica, sans-serif' size='1'><a style='text-decoration:none;color:#4c4c4c'> Desktop</a></font> </p> <a> <img height='23' width='53' border='0' style='display:block;color:#4c9ac9;padding-top:2px' alt='confluence' src='https://ci3.googleusercontent.com/proxy/ODFLWiSltddiTZjsfV09L5by4UiH0GhS-mUnw-1NQltuEA8qFm_34shpzA-JTnpcuWrLeX4pyXkDx0NaiE5oNpiGc55-kxiI36qdnjs8IWC5AQ=s0-d-e1-ft#http://creately.com/newsletter/new_template_2012/desktop.jpg' class='CToWUd'></a> </td> <td width='15' bgcolor='#f1f1f1'> </td> </tr> </tbody> </table> </td> <td> <table cellspacing='0' cellpadding='0' border='0'> <tbody> <tr> <td bgcolor='#f1f1f1' style='height:50px;width:100px;padding-bottom:10px'> <p align='justify' style='margin:0;font-family:Arial,Helvetica,sans-serif;font-size:11px;color:#4c4c4c;text-align:center;font-weight:bold;display:block;width:90px;margin-bottom:0'> <font face='Arial, Helvetica, sans-serif' size='1'><a style='text-decoration:none;color:#4c4c4c'> Online</a></font> </p> <a> <img height='23' width='90' border='0' style='display:block;color:#4c9ac9;padding-top:2px' alt='confluence' src='https://ci5.googleusercontent.com/proxy/L4t0ucOicUpfLb33TWL-X_ZPpcry6cEevbCgJxs8lVVaDHEgLHFUPE-2Sm1X6emSnRIxTZxF1vpD5nndQ_0exXlvLDMUwhcpibPtaPyu365Y=s0-d-e1-ft#http://creately.com/newsletter/new_template_2012/online.jpg' class='CToWUd'></a> </td> </tr> </tbody> </table> </td> <td> <table cellspacing='0' cellpadding='0' border='0' style='margin-left:0px;margin-top:0px'> <tbody> <tr> <td width='10' bgcolor='#f1f1f1'> </td> <td width='57' align='left' style='margin-right:0px;height:50px;padding-bottom:10px'> <a> <img height='48' width='57' border='0' style='display:block;color:#4c9ac9;float:left;padding-top:14px' alt='Stash' src='https://ci4.googleusercontent.com/proxy/WC_08z400QAf2Ks9AmuwbeW5iBH2MHEM8t-b8MfCVARgpf22a3z6Lld-dGYwJ4F4n4RLaDYGD_0WkV0xqfWwlWnZu9Qu_-kKLPOmEh48UYlZl_rrYy8qZAid7Q=s0-d-e1-ft#http://creately.com/newsletter/new_template_2012/google-apps-logo.jpg' class='CToWUd'></a> </td> <td width='134' align='left' style='height:50px;padding-bottom:10px'> <a> <img height='48' width='134' border='0' style='display:block;color:#4c9ac9;float:left;padding-top:14px' alt='Stash' src='https://ci6.googleusercontent.com/proxy/hyg5JhmHbKzUiwb2b862tOX6zbU7t_54St7nRA_HJmH6hhhdYA3woy4evlkyAmaH6sYRPK488legDD9XR9O7x7vSikXsrcMjjHiNm-sNRzrTtzB0leHEagHBBMaW9cVM=s0-d-e1-ft#http://creately.com/newsletter/new_template_2012/chrome-web-store-logo.jpg' class='CToWUd'></a> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table> <table width='630' cellspacing='0' cellpadding='0' bgcolor='#ffffff' align='center'> <tbody> <tr> <td height='25'> </td> </tr> <tr> <td style='font-family:Arial,Helvetica,sans-serif;font-size:11px;color:#4c4c4c;text-align:center'> <p style='margin-bottom:0;margin:0'> <font face='Arial, Helvetica, sans-serif' size='1'>Si no esta interesado en recibir mas esta notificación por favor escribanos al siguiente correo <strong><a href='mailto:soporte@popular-safi.com' target='_blank'>soporte@popular-safi.com</a></strong></font> </p> </td> </tr> <tr> <td height='25'> <span style='margin-bottom:0;margin:0;border-bottom:1px solid #ddd;display:block;min-height:10px'></span> </td> </tr> <tr> <td height='25'> </td> </tr> <tr> <td style='font-family:Arial,Helvetica,sans-serif;font-size:11px;color:#4c4c4c;text-align:center'> <p align='center' style='color:#4c4c4c;font-family:Arial;font-size:11px;font-weight:normal;line-height:110%;margin:0;padding:0;text-align:center'> <strong>Copyright© "
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ " POPULAR-SAFI.</strong> Todos los derechos reservados. Av. Nicolás de Piérola 938 Of.302 – Plaza San Martín – Cercado de Lima. Email: <strong><a href='mailto:informes@popular-safi.com' target='_blank'>informes@popular-safi.com</a></strong> </p> </td> </tr> <tr> <td height='25'> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table></body><html>";

	}

	String RetornaTabla(List<Operacion> operaciones) {
		double total = 0;
		int sumador = 0;
		String tabla = "<table border='1'>";
		tabla += "<tr><th>N°</th><th>CODIGO</th><th>FECHA REGISTRO</th><th>PRESTAMO</th><th>INVERSIONISTA</th><th>OBSERVACION</th><th>DNI</th><th>CLIENTE</th></tr>";
		for (Operacion operacion : operaciones) {
			sumador++;
			total += operacion.getMonto();
			tabla += "<tr><td>" + sumador + "</td><td>" + operacion.getCodigo() + "</td><td>"
					+ operacion.getEscritura() + "</td><td>" + operacion.getMonto() + "</td><td>"
					+ operacion.getFondo().getNombre() + "</td><td>"
					+ (operacion.getObservacion() == null ? "" : operacion.getObservacion()) + "</td><td>"
					+ operacion.getDni() + "</td><td>" + operacion.getCliente() + "</td></tr>";
		}
		total=Math.round(total * 100) / 100.0d;
		tabla += "<tr ><th class='pie'></th><th class='pie'></th><th class='pie'>Total :</th><th class='pie'>" + total
				+ "</th><th class='pie'></th><th class='pie'></th><th class='pie'></th><th class='pie'></th></tr>";
		tabla += "</table>";
		return tabla;
	}

}
