package gm.tienda_libros.vista;

import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component//para obtener el formulario de la fabrica de spring
//con esta anotacion este formulario de swing va a participar de la fabrica de spring
public class LibroForm extends JFrame {

    private LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;

    private JTextField idTexto;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JPanel panelJTexField;
    private JPanel jPanelLibro;
    private JPanel jPanelAutor;
    private JPanel jPanelPrecio;
    private JPanel jPanelExist;

    private DefaultTableModel modeloTablaLibros;

    public LibroForm(LibroServicio libroServicio){
        this.libroServicio=libroServicio;
        iniciarForma();//se encarga de recuperar la informacion de la base de datos
        //nos permite instanciar objeto de la clase LibroForma para que se visualice nuestro formulario

        agregarButton.addActionListener(e -> agregarLibro());
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });
        modificarButton.addActionListener(e -> modificarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());
    }

    private void iniciarForma() {
        setContentPane(panel);//se indica que al inicio se tiene que cargar este panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//para que se cierre la aplicacion con exito
        setVisible(true);//para que se visualice el formulario cuando lo mandemos a ejecutar
        setSize(900,700);//tamaño de la ventana
        getContentPane().setBackground(new Color(240, 230, 140));
        panelJTexField.setBackground(new Color(240, 230, 140));
        jPanelLibro.setBackground(new Color(240, 230, 140));
        jPanelAutor.setBackground(new Color(240, 230, 140));
        jPanelPrecio.setBackground(new Color(240, 230, 140));
        jPanelExist.setBackground(new Color(240, 230, 140));
        Toolkit toolkit = Toolkit.getDefaultToolkit();// se centra la ventana
        Dimension tamanioPantalla= toolkit.getScreenSize(); //se obtiene las dimensiones de nuestra ventana
        int x = tamanioPantalla.width - getWidth()/ 2;//ancho
        int y = tamanioPantalla.height - getHeight()/ 2;//alto
        setLocation(x, y);//es el punto donde se va a mostrar la aplicacion
    }

    private void agregarLibro(){
        //leer los valores del formulario
        if (libroTexto.getText().equals("")) {
            mostrarMensaje("Proporciona el nombre del libro");
            libroTexto.requestFocus();//para que cambie el cursor
            return;//salimos de la funcion
        }

        var nombreLibro= libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());

        //se crea el objeto libro

//        Libro libro = new Libro();
//        libro.setNombreLibro(nombreLibro);
//        libro.setAutor(autor);
//        libro.setPrecio(precio);
//        libro.setExistencias(existencias);

        Libro libro = new Libro(null,nombreLibro,autor,precio,existencias);
        //guardamos el libro
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se agrego el libro");
        limpiarFormulario();
        listarLibros();


    }

    private void cargarLibroSeleccionado(){
        //los indices de las columnas de nuestra tabla inician en 0
        var renglon= tablaLibros.getSelectedRow();//para saber el renglon que se selecciono
        if (renglon != -1){//Regresa -1 si no se selecciono ningun registro
            String idLibros = tablaLibros.getModel().getValueAt(renglon,0).toString();
            idTexto.setText(idLibros);
            String nombreLibro = tablaLibros.getModel().getValueAt(renglon,1).toString();
            libroTexto.setText(nombreLibro);//lo cargamos al formulario
            String autor = tablaLibros.getModel().getValueAt(renglon,2).toString();
            autorTexto.setText(autor);
            String precio = tablaLibros.getModel().getValueAt(renglon,3).toString();
            precioTexto.setText(precio);
            String existencias = tablaLibros.getModel().getValueAt(renglon,4).toString();
            existenciasTexto.setText(existencias);

        }
    }

    private void modificarLibro(){
        if (this.idTexto.getText().equals("")){
            mostrarMensaje("debe seleccionar un registro");
        }else{
            if(libroTexto.getText().equals("")){
                mostrarMensaje("Debe proporcionar nombre del libro");
                libroTexto.requestFocusInWindow();//para que se seleccione este texto
                return;
            }

            //llenamos el objeto libro a actualizar
            int idLibro = Integer.parseInt(idTexto.getText());
            var nombreLibro = libroTexto.getText();
            var autor = autorTexto.getText();
            var precio = Double.parseDouble(precioTexto.getText());
            var existencias = Integer.parseInt(existenciasTexto.getText());

            Libro libro = new Libro(idLibro,nombreLibro,autor,precio,existencias);
            libroServicio.guardarLibro(libro);
            mostrarMensaje("se modifico el libro");
            limpiarFormulario();
            listarLibros();
        }
    }

    private void eliminarLibro(){
     var renglon = tablaLibros.getSelectedRow();

        if (renglon != -1) {
            String idLibro = tablaLibros.getModel().getValueAt(renglon,0).toString();
            var libro= new Libro();
            libro.setIdLibro(Integer.parseInt(idLibro));
            libroServicio.borrarLibro(libro);
            mostrarMensaje("libro eliminado");
            limpiarFormulario();
            listarLibros();
        }




    }

    private void limpiarFormulario(){
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this,mensaje);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Creamos el elemento idTexto oculto
        idTexto = new JTextField("");
        idTexto.setVisible(false);
        this.modeloTablaLibros = new DefaultTableModel(0,5) {//creamos nuestro objeto con 5 columnas
            @Override
            public boolean isCellEditable(int row , int column){
                return false;
            }
        };
        String[] cabeceros = {"Id","Libro","Autor","Precio","Existencia"};
        this.modeloTablaLibros.setColumnIdentifiers(cabeceros);
        //se instancia el objeto jtable
        this.tablaLibros = new JTable(modeloTablaLibros);
        //evitar que se seleccionen varios registros
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarLibros();

    }

    private void listarLibros(){
        //limpiar tabla
        modeloTablaLibros.setRowCount(0);//con esto se limpia la tabla
        //obtener los libros
        var libros = libroServicio.listarLibros();
        //iteramos cada uno de los libros
        libros.forEach((libro)->{
            Object[] renglonLibro = {//vamos definiendo cada renglon
                    libro.getIdLibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            this.modeloTablaLibros.addRow(renglonLibro);//agregamos a nuestro modelo de tabla
        });

    }
}
