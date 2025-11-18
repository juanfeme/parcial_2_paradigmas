package co.edu.poli.main;

import java.util.List;
import java.util.Scanner;
import co.edu.poli.modelo.*;
import co.edu.poli.servicios.ImplementacionCRUD;
import co.edu.poli.servicios.OperacionCRUD;

public class Main {
    private static OperacionCRUD operaciones;
    private static Proveedor[] proveedores;
    private static Scanner scanner;
    private static final String ARCHIVO = "productos.dat";
    
    public static void main(String[] args) {
        inicializarSistema();
        mostrarMenuInteractivo();
    }
    
    private static void inicializarSistema() {
        operaciones = new ImplementacionCRUD();
        scanner = new Scanner(System.in);
        
        proveedores = new Proveedor[]{
            new Proveedor(1, "TechGlobal", "USA"),
            new Proveedor(2, "ModaStyle", "Italia"),
            new Proveedor(3, "ElectroWorld", "Japón")
        };
    }
    
    private static void mostrarMenuInteractivo() {
        int opcion;
        
        do {
            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Crear producto electrónico");
            System.out.println("2. Listar todos los productos");
            System.out.println("3. Crear producto de ropa");
            System.out.println("4. Buscar electrónico específico");
            System.out.println("5. Modificar producto de ropa");
            System.out.println("6. Eliminar producto electrónico");
            System.out.println("7. Guardar datos en archivo");
            System.out.println("8. Cargar datos desde archivo");
            System.out.println("9. Salir del sistema");
            
            opcion = leerEntero("Seleccione una opción (1-9): ", 1, 9);
            
            switch (opcion) {
                case 1:
                    crearElectronico();
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    crearRopa();
                    break;
                case 4:
                    buscarElectronicoEspecifico();
                    break;
                case 5:
                    modificarRopa();
                    break;
                case 6:
                    eliminarElectronico();
                    break;
                case 7:
                    operaciones.guardarArchivo(ARCHIVO);
                    break;
                case 8:
                    operaciones.leerArchivo(ARCHIVO);
                    break;
                case 9:
                    System.out.println("Saliendo del sistema...");
                    break;
            }
            
        } while (opcion != 9);
        
        scanner.close();
    }
    
    private static void crearElectronico() {
        System.out.println("CREAR PRODUCTO ELECTRÓNICO");
        
        String codigo = leerString("Código del producto: ");
        String nombre = leerString("Nombre del producto: ");
        double precio = leerDouble("Precio: $", 0.01, 1000000);
        int stock = leerEntero("Stock disponible: ", 0, 10000);
        
        System.out.println("Proveedores disponibles:");
        for (int i = 0; i < proveedores.length; i++) {
            System.out.println((i + 1) + ". " + proveedores[i]);
        }
        int opcionProveedor = leerEntero("Proveedor (1-" + proveedores.length + "): ", 1, proveedores.length) - 1;
        
        int garantia = leerEntero("Garantía en meses: ", 0, 120);
        
        operaciones.crearElectronico(codigo, nombre, precio, stock, proveedores[opcionProveedor], garantia);
    }
    
    private static void crearRopa() {
        System.out.println("CREAR PRODUCTO DE ROPA");
        
        String codigo = leerString("Código del producto: ");
        String nombre = leerString("Nombre del producto: ");
        double precio = leerDouble("Precio: $", 0.01, 1000000);
        int stock = leerEntero("Stock disponible: ", 0, 10000);
        
        System.out.println("Proveedores disponibles:");
        for (int i = 0; i < proveedores.length; i++) {
            System.out.println((i + 1) + ". " + proveedores[i]);
        }
        int opcionProveedor = leerEntero("Proveedor (1-" + proveedores.length + "): ", 1, proveedores.length) - 1;
        
        String talla = leerString("Talla (S, M, L, XL, XXL): ");
        
        String[] tallasValidas = {"S", "M", "L", "XL", "XXL"};
        boolean tallaValida = false;
        for (String t : tallasValidas) {
            if (t.equalsIgnoreCase(talla)) {
                tallaValida = true;
                break;
            }
        }
        
        if (!tallaValida) {
            System.out.println("Talla no válida. Se usará 'M' por defecto.");
            talla = "M";
        }
        
        operaciones.crearRopa(codigo, nombre, precio, stock, proveedores[opcionProveedor], talla.toUpperCase());
    }
    
    private static void listarProductos() {
        List<Producto> productos = operaciones.listarProductos();
        
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados en el sistema.");
        } else {
            System.out.println("LISTA DE PRODUCTOS:");
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                System.out.println((i + 1) + ". " + p);
            }
        }
    }
    
    private static void buscarElectronicoEspecifico() {
        System.out.println("BUSCAR ELECTRÓNICO ESPECÍFICO");
        
        String codigo = leerString("Ingrese el código del electrónico a buscar: ");
        
        Producto electronico = operaciones.buscarElectronico(codigo);
        if (electronico != null) {
            System.out.println("Producto encontrado: " + electronico);
        } else {
            System.out.println("No se encontró ningún producto electrónico con código: " + codigo);
        }
    }
    
    private static void modificarRopa() {
        System.out.println("MODIFICAR PRODUCTO DE ROPA");
        
        String codigo = leerString("Código del producto de ropa a modificar: ");
        
        boolean existeRopa = false;
        List<Producto> productos = operaciones.listarProductos();
        for (Producto p : productos) {
            if (p instanceof Ropa && p.getCodigo().equals(codigo)) {
                existeRopa = true;
                System.out.println("Producto actual: " + p);
                break;
            }
        }
        
        if (!existeRopa) {
            System.out.println("No se encontró ningún producto de ropa con código: " + codigo);
            return;
        }
        
        System.out.println("Ingrese los nuevos valores (deje vacío para mantener el actual):");
        
        String nuevoNombre = leerStringOpcional("Nuevo nombre: ");
        Double nuevoPrecio = leerDoubleOpcional("Nuevo precio: $", 0.01, 1000000);
        Integer nuevoStock = leerEnteroOpcional("Nuevo stock: ", 0, 10000);
        
        String nuevaTalla = leerStringOpcional("Nueva talla: ");
        
        if (nuevaTalla != null && !nuevaTalla.isEmpty()) {
            String[] tallasValidas = {"S", "M", "L", "XL", "XXL"};
            boolean tallaValida = false;
            for (String t : tallasValidas) {
                if (t.equalsIgnoreCase(nuevaTalla)) {
                    tallaValida = true;
                    nuevaTalla = t;
                    break;
                }
            }
            if (!tallaValida) {
                System.out.println("Talla no válida. No se modificará la talla.");
                nuevaTalla = null;
            }
        } else {
            nuevaTalla = null;
        }
        
        operaciones.modificarRopa(codigo, nuevoNombre, nuevoPrecio, nuevoStock, nuevaTalla);
    }
    
    private static void eliminarElectronico() {
        System.out.println("ELIMINAR PRODUCTO ELECTRÓNICO");
        
        String codigo = leerString("Ingrese el código del electrónico a eliminar: ");
        
        Producto electronico = operaciones.buscarElectronico(codigo);
        if (electronico != null) {
            System.out.println("Producto a eliminar: " + electronico);
            
            String confirmar = leerString("¿Está seguro de que desea eliminar este producto? (S/N): ");
            if (confirmar.equalsIgnoreCase("S")) {
                operaciones.eliminarElectronico(codigo);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("No se encontró ningún producto electrónico con código: " + codigo);
        }
    }
    
    private static String leerString(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    
    private static String leerStringOpcional(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
    
    private static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("Error: El valor debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número entero válido.");
            }
        }
    }
    
    private static Integer leerEnteroOpcional(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return null;
                }
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("Error: El valor debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número entero válido o deje vacío.");
            }
        }
    }
    
    private static double leerDouble(String mensaje, double min, double max) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("Error: El valor debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
            }
        }
    }
    
    private static Double leerDoubleOpcional(String mensaje, double min, double max) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return null;
                }
                double valor = Double.parseDouble(input);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("Error: El valor debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido o deje vacío.");
            }
        }
    }
}