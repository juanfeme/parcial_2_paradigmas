package co.edu.poli.servicios;

import co.edu.poli.modelo.Producto;
import java.util.List;

public interface OperacionCRUD {
    void crear(Producto producto);
    Producto leer(String codigo);
    void modificar(Producto producto);
    void eliminar(String codigo);
    List<Producto> enlistar();
    void serializar(String nombreArchivo);
    List<Producto> deserializar(String nombreArchivo);
}