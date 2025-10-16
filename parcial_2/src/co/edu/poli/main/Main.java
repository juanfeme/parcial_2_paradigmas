package co.edu.poli.main;

import co.edu.poli.modelo.Electronico;
import co.edu.poli.modelo.Producto;
import co.edu.poli.modelo.Proveedor;
import co.edu.poli.modelo.Ropa;
import co.edu.poli.servicios.ImplementacionCRUD;

public class Main {
    public static void main(String[] args) {

        ImplementacionCRUD gestion = new ImplementacionCRUD();
        String archivo = "productos.dat";
        

        gestion.deserializar(archivo);

        System.out.println("Inventario Inicial");
        imprimirInventario(gestion);


        Proveedor provSony = new Proveedor("P-SONY", "Sony", "Japón");
        Proveedor provNike = new Proveedor("P-NIKE", "Nike", "USA");


        Producto tv = new Electronico("E001", "Televisor OLED 55\"", 5500000, 10, provSony, 24);
        Producto camiseta = new Ropa("R001", "Camiseta Dri-Fit", 150000, 50, provNike, "M");
        Producto consola = new Electronico("E002", "PlayStation 5", 3000000, 5, provSony, 12);


        System.out.println("Creando productos");
        gestion.crear(tv);
        gestion.crear(camiseta);
        gestion.crear(consola);


        System.out.println("Inventario Actual");
        imprimirInventario(gestion);


        System.out.println("Modificando producto");
        Producto tvModificado = new Electronico("E001", "Televisor OLED 55\"", 5200000, 8, provSony, 24);
        gestion.modificar(tvModificado);


        System.out.println("Leyendo un producto específico");
        Producto productoEncontrado = gestion.leer("E001");
        System.out.println("Encontrado: " + productoEncontrado);


        System.out.println("Eliminando un producto");
        gestion.eliminar("R001");


        System.out.println("Inventario Final");
        imprimirInventario(gestion);


        gestion.serializar(archivo);
    }
    
    public static void imprimirInventario(ImplementacionCRUD gestion) {
        if (gestion.enlistar().isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (Producto p : gestion.enlistar()) {
                System.out.println(p);
            }
        }
    }
}