package pop.teletransfer.usuario.servicio;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import pop.teletransfer.usuario.wsdl.GetUsuarioRequest;
import pop.teletransfer.usuario.wsdl.GetUsuarioResponse;
import pop.teletransfer.usuario.wsdl.Usuario;

@Configuration
public class UsuarioClient extends WebServiceGatewaySupport {

	Properties prop = new Properties();

	UsuarioClient() throws IOException {
		prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
	}

	public GetUsuarioResponse GetUsuario(Usuario usuario) {

		return new GetUsuarioResponse();
	}

}
