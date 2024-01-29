package gm.tienda_libros;

import gm.tienda_libros.vista.LibroForm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class TiendaLibrosApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext contextoSpring =////aca se solicita una instancia del formulario y asi integramos swing con spring
				new SpringApplicationBuilder(TiendaLibrosApplication.class)
						.headless(false)
						.web(WebApplicationType.NONE)//indicamos que no es una aplicacion web
						.run(args);

		//ejecutamos el codigo para cargar el formulario
		 EventQueue.invokeLater(()->{
			 //obtenemos el objeto form a travez de spring
			 LibroForm libroForm = contextoSpring.getBean(LibroForm.class);
			 libroForm.setVisible(true);
		 });

	}

}
