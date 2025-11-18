package co.edu.poli.servicios;

import java.util.List;
import co.edu.poli.modelo.Producto;
import co.edu.poli.modelo.Proveedor;

public interface OperacionCRUD {

    void crearElectronico(String codigo, String nombre, double precio, int stock, 
                         Proveedor proveedor, int garantiaMeses);
    void crearRopa(String codigo, String nombre, double precio, int stock, 
                  Proveedor proveedor, String talla);
    List<Producto> listarProductos();
    Producto buscarElectronico(String codigo);
    void modificarRopa(String codigo, String nuevoNombre, Double nuevoPrecio, 
                      Integer nuevoStock, String nuevaTalla);
    boolean eliminarElectronico(String codigo);
    
    // Serialización
    void guardarArchivo(String archivo);
    void leerArchivo(String archivo);
}