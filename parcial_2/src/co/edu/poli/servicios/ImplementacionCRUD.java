package co.edu.poli.servicios;

import co.edu.poli.modelo.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionCRUD implements OperacionCRUD {
    private List<Producto> lista;

    public ImplementacionCRUD() {
        this.lista = new ArrayList<>();
    }

    @Override
    public void crear(Producto producto) {
        lista.add(producto);
        System.out.println("Producto '" + producto.getNombre() + "' creado.");
    }

    @Override
    public Producto leer(String codigo) {
        for (Producto p : lista) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void modificar(Producto productoModificado) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(productoModificado.getCodigo())) {
                lista.set(i, productoModificado);
                System.out.println("Producto con código '" + productoModificado.getCodigo() + "' modificado.");
                return;
            }
        }
    }

    @Override
    public void eliminar(String codigo) {
        Producto aEliminar = leer(codigo);
        if (aEliminar != null) {
            lista.remove(aEliminar);
            System.out.println("Producto '" + aEliminar.getNombre() + "' eliminado.");
        } else {
            System.out.println("No se encontró producto con el código " + codigo);
        }
    }

    @Override
    public List<Producto> enlistar() {
        return lista;
    }

    @Override
    public void serializar(String nombreArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(lista);
            System.out.println("Datos serializados correctamente en " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Producto> deserializar(String nombreArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            lista = (List<Producto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se encontró el archivo, se creará uno nuevo al serializar.");
            this.lista = new ArrayList<>();
        }
        return lista;
    }
}