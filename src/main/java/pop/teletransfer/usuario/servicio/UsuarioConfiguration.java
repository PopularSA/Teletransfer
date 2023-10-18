package pop.teletransfer.usuario.servicio;

import java.io.IOException;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class UsuarioConfiguration {
	Properties prop = new Properties();
	
	UsuarioConfiguration() throws IOException{
		prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
	}
	
	@Bean
	public Jaxb2Marshaller marshaller() throws IOException {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(prop.getProperty("wsdl.context"));
		return marshaller;
	}

	@Bean
	public UsuarioClient weatherClient(Jaxb2Marshaller marshaller) throws IOException {
		UsuarioClient client = new UsuarioClient();
		client.setDefaultUri(prop.getProperty("wsdl.url"));
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
