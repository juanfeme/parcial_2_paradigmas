package co.edu.poli.modelo;

public class Electronico extends Producto {
    private int garantiaMeses;

    public Electronico(String codigo, String nombre, double precio, int stock, Proveedor proveedor, int garantiaMeses) {
        super(codigo, nombre, precio, stock, proveedor);
        this.garantiaMeses = garantiaMeses;
    }

    // Getter y Setter
    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    @Override
    public String toString() {
        return "Electronico{" +super.toString() +", garantiaMeses=" + garantiaMeses +'}';
    }
}