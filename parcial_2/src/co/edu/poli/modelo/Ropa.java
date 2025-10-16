package co.edu.poli.modelo;

public class Ropa extends Producto {
    private String talla;

    public Ropa(String codigo, String nombre, double precio, int stock, Proveedor proveedor, String talla) {
        super(codigo, nombre, precio, stock, proveedor);
        this.talla = talla;
    }

    // Getter y Setter
    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    @Override
    public String toString() {
        return "Ropa{" +super.toString() +", talla='" + talla + '\'' +'}';
    }
}