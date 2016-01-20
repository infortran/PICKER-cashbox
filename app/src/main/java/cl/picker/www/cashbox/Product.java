package cl.picker.www.cashbox;

/**
 * Created by Freddy on 24-11-2015.
 */
public class Product {
    private String nombre;
    private int precio;
    private String picker;

    public Product(){

    }

    public Product(String nombr, int preci, String picke){
        this.nombre = nombr;
        this.precio = preci;
        this.picker = picke;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }
}
