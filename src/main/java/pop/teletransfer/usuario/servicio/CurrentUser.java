package pop.teletransfer.usuario.servicio;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private pop.teletransfer.usuario.wsdl.Usuario user;

	public CurrentUser(pop.teletransfer.usuario.wsdl.Usuario usuario) {
		super(usuario.getUsuario(), usuario.getClave(), AuthorityUtils.createAuthorityList("ADMIN"));
		this.user = usuario;
	}

	public pop.teletransfer.usuario.wsdl.Usuario getUser() {
		return user;
	}

	public Long getId() {
		return 1L;
	}
	@Override
	public String toString() {
		return "CurrentUser{" + "user=" + user + "} " + super.toString();
	}
}
