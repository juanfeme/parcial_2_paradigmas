package co.edu.poli.modelo;

import java.io.Serializable;

public abstract class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;
    private Proveedor proveedor;

    public Producto(String codigo, String nombre, double precio, int stock, Proveedor proveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }


    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Nombre: " + nombre + ", Precio: $" + precio + 
               ", Stock: " + stock + ", " + proveedor;
    }


    public abstract String getInfoEspecifica();
}