package pop.teletransfer.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/recursos/*");
		web.ignoring().antMatchers("/recursos/css/*");
		web.ignoring().antMatchers("/recursos/js/*");
		web.ignoring().antMatchers("/recursos/jquery-ui-1.11.4.eggplant/*");

		web.ignoring().antMatchers("/recursos/bower_components/jquery/dist/*");
		web.ignoring().antMatchers("/recursos/bower_components/bootstrap/dist/js/*");
		web.ignoring().antMatchers("/recursos/dist/js/*");
		web.ignoring().antMatchers("/recursos/bower_components/metisMenu/dist/*");
		web.ignoring().antMatchers("/recursos/bower_components/morrisjs/dist/*");
		web.ignoring().antMatchers("/recursos/dist/js/morrisjs/dist/*");
		web.ignoring().antMatchers("/recursos/bower_components/morrisjs/*");
		web.ignoring().antMatchers("/recursos/bower_components/raphael/*");

		web.ignoring().antMatchers("/recursos/bower_components/bootstrap/dist/css/*");
		web.ignoring().antMatchers("/recursos/bower_components/metisMenu/dist/*");
		web.ignoring().antMatchers("/recursos/dist/css/*");
		web.ignoring().antMatchers("/recursos/bower_components/morrisjs/*");
		web.ignoring().antMatchers("/recursos/bower_components/font-awesome/css/*");
		web.ignoring().antMatchers("/recursos/images/*");

	}

}