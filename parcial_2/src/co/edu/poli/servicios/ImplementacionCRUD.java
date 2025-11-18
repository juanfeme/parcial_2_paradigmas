package co.edu.poli.servicios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import co.edu.poli.modelo.*;

public class ImplementacionCRUD implements OperacionCRUD {
    private List<Producto> productos;
    
    public ImplementacionCRUD() {
        this.productos = new ArrayList<>();
    }
    
    @Override
    public void crearElectronico(String codigo, String nombre, double precio, 
                               int stock, Proveedor proveedor, int garantiaMeses) {
        Electronico electronico = new Electronico(codigo, nombre, precio, stock, proveedor, garantiaMeses);
        productos.add(electronico);
        System.out.println("✓ Producto electrónico '" + nombre + "' creado exitosamente.");
    }
    
    @Override
    public void crearRopa(String codigo, String nombre, double precio, 
                         int stock, Proveedor proveedor, String talla) {
        Ropa ropa = new Ropa(codigo, nombre, precio, stock, proveedor, talla);
        productos.add(ropa);
        System.out.println("✓ Producto de ropa '" + nombre + "' creado exitosamente.");
    }
    
    @Override
    public List<Producto> listarProductos() {
        return new ArrayList<>(productos); // Retorna copia para evitar modificación externa
    }
    
    @Override
    public Producto buscarElectronico(String codigo) {
        for (Producto producto : productos) {
            if (producto instanceof Electronico && producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }
    
    @Override
    public void modificarRopa(String codigo, String nuevoNombre, Double nuevoPrecio, 
                             Integer nuevoStock, String nuevaTalla) {
        for (Producto producto : productos) {
            if (producto instanceof Ropa && producto.getCodigo().equals(codigo)) {
                Ropa ropa = (Ropa) producto;
                if (nuevoNombre != null) ropa.setNombre(nuevoNombre);
                if (nuevoPrecio != null) ropa.setPrecio(nuevoPrecio);
                if (nuevoStock != null) ropa.setStock(nuevoStock);
                if (nuevaTalla != null) ropa.setTalla(nuevaTalla);
                System.out.println("✓ Producto de ropa modificado exitosamente.");
                return;
            }
        }
        System.out.println("✗ No se encontró producto de ropa con código: " + codigo);
    }
    
    @Override
    public boolean eliminarElectronico(String codigo) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            if (producto instanceof Electronico && producto.getCodigo().equals(codigo)) {
                productos.remove(i);
                System.out.println("✓ Producto electrónico eliminado exitosamente.");
                return true;
            }
        }
        System.out.println("✗ No se encontró producto electrónico con código: " + codigo);
        return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void guardarArchivo(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
            System.out.println("✓ Datos guardados exitosamente en: " + archivo);
        } catch (IOException e) {
            System.out.println("✗ Error al guardar: " + e.getMessage());
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void leerArchivo(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            productos = (List<Producto>) ois.readObject();
            System.out.println("✓ Datos cargados exitosamente desde: " + archivo);
        } catch (FileNotFoundException e) {
            System.out.println("ℹ El archivo no existe. Se creará uno nuevo al guardar.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("✗ Error al leer: " + e.getMessage());
        }
    }
    
    // Método adicional para obtener todos los productos (sin copia)
    public List<Producto> getProductos() {
        return productos;
    }
}