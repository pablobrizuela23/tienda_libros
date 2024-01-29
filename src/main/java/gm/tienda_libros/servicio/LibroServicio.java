package gm.tienda_libros.servicio;

import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service // para que pueda conciderarse como un bean de spring
//@Component es una anotacion padre y @Service es mas especifica
public class LibroServicio implements ILibroServicio{
    @Autowired
    private LibroRepositorio libroRepositorio;

    @Override
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    @Override
    public Libro buscarLibrosPorId(Integer idLibro) {
        Libro libro =libroRepositorio.findById(idLibro).orElse(null);

        return libro;

    }

    @Override
    public void guardarLibro(Libro libro) {
        libroRepositorio.save(libro);
    }

    @Override
    public void borrarLibro(Libro libro) {
        libroRepositorio.delete(libro);
    }
}
