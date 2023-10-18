package pop.teletransfer.mail.servicio;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import pop.teletransfer.dominio.Fondo;
import pop.teletransfer.dominio.Parametro;
import pop.teletransfer.dominio.Receptor;
import pop.teletransfer.mail.dominio.Mail;
import pop.teletransfer.repositorio.FondoRepositorio;
import pop.teletransfer.repositorio.OperacionRepositorio;
import pop.teletransfer.repositorio.ParametroRepositorio;
import pop.teletransfer.repositorio.ReceptorRepositorio;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MailServicioImpl implements MailServicio {



	@Autowired
	OperacionRepositorio operacionRepositorio;

	@Autowired
	ParametroRepositorio parametroRepositorio;

	@Autowired
	private FondoRepositorio fondoRepositorio;

	@Autowired
	private ReceptorRepositorio receptorRepositorio;

	@Override
	public void Notificar(Mail mail) {
		List<Parametro> parametros = parametroRepositorio.findAll();
		Map<String, String> params = new HashMap<String, String>();
		for (Parametro p : parametros) {
			params.put(p.getClave(), p.getValor());
		}
		String subject = params.get("mail.asunto");
		Properties props = new Properties();
		props.put("mail.smtp.host", params.get("mail.smtp.host"));
		props.put("mail.transport.protocol", params.get("mail.transport.protocol"));
		props.put("mail.smtp.starttls.enable", params.get("mail.smtp.starttls.enable"));
		props.put("mail.smtp.port", params.get("mail.smtp.port"));
		Session session = Session.getDefaultInstance(props);
		try {
			InternetAddress fromAddress = new InternetAddress(params.get("mail.usuario"));
			InternetAddress toAddress = new InternetAddress(params.get("mail.TO"));

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(fromAddress);
			msg.setRecipient(Message.RecipientType.TO, toAddress);

			for (Receptor r : receptorRepositorio.findAllFase()) {
				msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(r.getCorreo()));
			}

			msg.setSubject(subject);
			msg.setSentDate(new Date());
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(params.get("mail.contenido"));
			multipart.addBodyPart(messageBodyPart);

			List<Fondo> fondos = fondoRepositorio.findAll();

			for (Fondo fondo : fondos) {
				messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(params.get("mail.contenido"));
				DataSource source = new FileDataSource(
						params.get("adjunto.dir") + "CREP(" + fondo.getIdFondo() + ").txt");
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("CREP(" + fondo.getIdFondo() + ").txt");
				multipart.addBodyPart(messageBodyPart);
			}

			msg.setContent(multipart);
			Transport.send(msg, params.get("mail.usuario"), params.get("mail.clave"));
			operacionRepositorio.clearOperation();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
