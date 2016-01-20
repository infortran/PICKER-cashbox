package cl.picker.www.cashbox.DBObjects;

/**
 * Created by Freddy on 22-11-2015.
 */
public class Probadores {
    private String id_probador;
    private String id_tienda;
    private int id_admin;
    private int numero_probador;
    private int estado_probador;

    public String getId_probador() {
        return id_probador;
    }

    public void setId_probador(String id_probador) {
        this.id_probador = id_probador;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public int getNumero_probador() {
        return numero_probador;
    }

    public void setNumero_probador(int numero_probador) {
        this.numero_probador = numero_probador;
    }

    public int getEstado_probador() {
        return estado_probador;
    }

    public void setEstado_probador(int estado_probador) {
        this.estado_probador = estado_probador;
    }

    @Override
    public String toString() {
        return "Probadores{" +
                "id_probador='" + id_probador + '\'' +
                ", id_tienda='" + id_tienda + '\'' +
                ", id_admin=" + id_admin +
                ", numero_probador=" + numero_probador +
                ", estado_probador=" + estado_probador +
                '}';
    }
}
