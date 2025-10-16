package co.edu.poli.main;

import co.edu.poli.modelo.Electronico;
import co.edu.poli.modelo.Producto;
import co.edu.poli.modelo.Proveedor;
import co.edu.poli.modelo.Ropa;
import co.edu.poli.servicios.ImplementacionCRUD;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ImplementacionCRUD gestion = new ImplementacionCRUD();
        String archivo = "productos.dat";

        gestion.deserializar(archivo);

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        agregarProducto(scanner, gestion);
                        break;
                    case 2:
                        System.out.println("Inventario Actual");
                        imprimirInventario(gestion);
                        break;
                    case 3:
                        modificarProducto(scanner, gestion);
                        break;
                    case 4:
                        eliminarProducto(scanner, gestion);
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige un número del 1 al 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes introducir un número. Intenta de nuevo.");
                scanner.nextLine();
            }
        }

        gestion.serializar(archivo);
        System.out.println("Datos guardados. ¡Hasta pronto!");
        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println(" MENÚ DE GESTIÓN DE PRODUCTOS");
        System.out.println("1. Añadir un nuevo producto");
        System.out.println("2. Listar todos los productos");
        System.out.println("3. Modificar un producto");
        System.out.println("4. Eliminar un producto");
        System.out.println("5. Guardar y Salir");
        System.out.print("Elige una opción: ");
    }

    public static void agregarProducto(Scanner scanner, ImplementacionCRUD gestion) {
        try {
            System.out.println(" Añadir Nuevo Producto ");
            System.out.print("¿Qué tipo de producto es? (1: Electrónico, 2: Ropa): ");
            int tipo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Código: ");
            String codigo = scanner.nextLine();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Precio: ");
            double precio = scanner.nextDouble();
            System.out.print("Stock: ");
            int stock = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Información del Proveedor");
            System.out.print("ID del Proveedor: ");
            String provId = scanner.nextLine();
            System.out.print("Nombre del Proveedor: ");
            String provNombre = scanner.nextLine();
            System.out.print("País de Origen del Proveedor: ");
            String provPais = scanner.nextLine();
            Proveedor proveedor = new Proveedor(provId, provNombre, provPais);

            Producto nuevoProducto = null;
            if (tipo == 1) {
                System.out.print("Meses de garantía: ");
                int garantia = scanner.nextInt();
                scanner.nextLine();
                nuevoProducto = new Electronico(codigo, nombre, precio, stock, proveedor, garantia);
            } else if (tipo == 2) {
                System.out.print("Talla (S, M, L, XL): ");
                String talla = scanner.nextLine();
                nuevoProducto = new Ropa(codigo, nombre, precio, stock, proveedor, talla);
            } else {
                System.out.println("Tipo de producto no válido.");
                return;
            }

            gestion.crear(nuevoProducto);

        } catch (InputMismatchException e) {
            System.out.println("Error: Has introducido un tipo de dato incorrecto. Vuelve a intentarlo.");
            scanner.nextLine();
        }
    }

    public static void modificarProducto(Scanner scanner, ImplementacionCRUD gestion) {
        System.out.println("Modificar Producto");
        System.out.print("Introduce el código del producto a modificar: ");
        String codigo = scanner.nextLine();

        Producto producto = gestion.leer(codigo);
        if (producto == null) {
            System.out.println("No se encontró ningún producto con ese código.");
            return;
        }

        try {
            System.out.println("Producto encontrado: " + producto.getNombre());
            System.out.print("Nuevo precio (actual: " + producto.getPrecio() + "): ");
            double nuevoPrecio = scanner.nextDouble();
            System.out.print("Nuevo stock (actual: " + producto.getStock() + "): ");
            int nuevoStock = scanner.nextInt();
            scanner.nextLine();

            producto.setPrecio(nuevoPrecio);
            producto.setStock(nuevoStock);
            
            gestion.modificar(producto);
            
            System.out.println("Producto actualizado correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("Error: Has introducido un tipo de dato incorrecto.");
            scanner.nextLine();
        }
    }

    public static void eliminarProducto(Scanner scanner, ImplementacionCRUD gestion) {
        System.out.println("Eliminar producto");
        System.out.print("Introduce el código del producto a eliminar: ");
        String codigo = scanner.nextLine();
        gestion.eliminar(codigo);
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