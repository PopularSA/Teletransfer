package pop.teletransfer.configuracion;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration  extends WebMvcConfigurerAdapter{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/portada").setViewName("portada");
        registry.addViewController("/prestaclub/registroCobroSAFI").setViewName("prestaclub/registroCobroSAFI");
        registry.addViewController("/popular/validacionRegistroCobroSAFI").setViewName("popular/validacionRegistroCobroSAFI");        
        registry.addViewController("/popular/consultaValidacionRegistroCobroSAFI").setViewName("popular/consultaValidacionRegistroCobroSAFI");
        registry.addViewController("/popular/codigoPorArea").setViewName("popular/codigoPorArea");
        
    }    
}
