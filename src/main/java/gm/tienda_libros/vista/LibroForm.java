package gm.tienda_libros.vista;

import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
@Component//para obtener el formulario de la fabrica de spring
//con esta anotacion este formulario de swing va a participar de la fabrica de spring
public class LibroForm extends JFrame {

    private LibroServicio libroServicio;
    private JPanel panel;

    public LibroForm(LibroServicio libroServicio){
        this.libroServicio=libroServicio;
        iniciarForma();//se encarga de recuperar la informacion de la base de datos
        //nos permite instanciar objeto de la clase LibroForma para que se visualice nuestro formulario

    }

    private void iniciarForma() {
        setContentPane(panel);//se indica que al inicio se tiene que cargar este panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//para que se cierre la aplicacion con exito
        setVisible(true);//para que se visualice el formulario cuando lo mandemos a ejecutar
        setSize(900,700);//tamaño de la ventana
        Toolkit toolkit = Toolkit.getDefaultToolkit();// se centra la ventana
        Dimension tamanioPantalla= toolkit.getScreenSize(); //se obtiene las dimensiones de nuestra ventana
        int x = tamanioPantalla.width - getWidth()/ 2;//ancho
        int y = tamanioPantalla.height - getHeight()/ 2;//alto
        setLocation(x, y);//es el punto donde se va a mostrar la aplicacion
    }


}
