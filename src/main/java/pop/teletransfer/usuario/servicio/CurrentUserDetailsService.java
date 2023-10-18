package pop.teletransfer.usuario.servicio;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pop.teletransfer.usuario.wsdl.GetUsuarioResponse;
import pop.teletransfer.usuario.wsdl.Usuario;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	@Override
	public CurrentUser loadUserByUsername(String user) throws UsernameNotFoundException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(UsuarioConfiguration.class);
		ctx.refresh();
		Usuario usuario=new Usuario();
		usuario.setUsuario(user);
		UsuarioClient usuarioClient = ctx.getBean(UsuarioClient.class);
		GetUsuarioResponse response = usuarioClient.GetUsuario(usuario);
		return new CurrentUser(response.getUsuario());
	}

}
