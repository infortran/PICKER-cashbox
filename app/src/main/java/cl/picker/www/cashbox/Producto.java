package cl.picker.www.cashbox;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Freddy on 19-11-2015.
 */
public class Producto {
    @SerializedName("id_prod")
    private int idProducto;
    @SerializedName("sku_prod")
    private String skuProducto;
    @SerializedName("nombre_prod")
    private String nombreProducto;
    @SerializedName("activo_prod")
    private int activoProducto;
    @SerializedName("desc_breve_prod")
    private String descBreveProducto;
    @SerializedName("descripcion_prod")
    private String descripcionProducto;
    @SerializedName("precio_mayorista_prod")
    private float precioMayoristaProducto;
    @SerializedName("precio_venta_prod")
    private float precioVentaProducto;
    @SerializedName("precio_venta_iva_prod")
    private float precioVentaIvaProducto;
    @SerializedName("impuesto_prod")
    private float impuestoProducto;
    @SerializedName("txt_stock_prod")
    private String textoStockProducto;
    @SerializedName("txt_no_stock_prod")
    private String textoNoStockProducto;

    private Date fechaDisponibleProducto;
    @SerializedName("cantidad_prod")
    private int cantidadProducto;

    public Producto(){
    }

    public Producto(int id, String sku, String nombre, int activo, String descBreve, String descripcion,
                    float precioMayorista, float precioVenta, float precioVentaIva, float impuesto,
                    String textoStock, String textoNoStock, Date fechaDisponible, int cantidad){
        this.idProducto = id;
        this.skuProducto = sku;
        this.nombreProducto = nombre;
        this.activoProducto = activo;
        this.descBreveProducto = descBreve;
        this.descripcionProducto = descripcion;
        this.precioMayoristaProducto = precioMayorista;
        this.precioVentaProducto = precioVenta;
        this.precioVentaIvaProducto = precioVentaIva;
        this.impuestoProducto = impuesto;
        this.textoStockProducto = textoStock;
        this.textoNoStockProducto = textoNoStock;
        this.fechaDisponibleProducto = fechaDisponible;
        this.cantidadProducto = cantidad;
    }

    public String getSkuProducto() {
        return skuProducto;
    }

    public void setSkuProducto(String skuProducto) {
        this.skuProducto = skuProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getActivoProducto() {
        return activoProducto;
    }

    public void setActivoProducto(int activoProducto) {
        this.activoProducto = activoProducto;
    }

    public String getDescBreveProducto() {
        return descBreveProducto;
    }

    public void setDescBreveProducto(String descBreveProducto) {
        this.descBreveProducto = descBreveProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public float getPrecioMayoristaProducto() {
        return precioMayoristaProducto;
    }

    public void setPrecioMayoristaProducto(float precioMayoristaProducto) {
        this.precioMayoristaProducto = precioMayoristaProducto;
    }

    public float getPrecioVentaProducto() {
        return precioVentaProducto;
    }

    public void setPrecioVentaProducto(float precioVentaProducto) {
        this.precioVentaProducto = precioVentaProducto;
    }

    public float getPrecioVentaIvaProducto() {
        return precioVentaIvaProducto;
    }

    public void setPrecioVentaIvaProducto(float precioVentaIvaProducto) {
        this.precioVentaIvaProducto = precioVentaIvaProducto;
    }

    public float getImpuestoProducto() {
        return impuestoProducto;
    }

    public void setImpuestoProducto(float impuestoProducto) {
        this.impuestoProducto = impuestoProducto;
    }

    public String getTextoStockProducto() {
        return textoStockProducto;
    }

    public void setTextoStockProducto(String textoStockProducto) {
        this.textoStockProducto = textoStockProducto;
    }

    public String getTextoNoStockProducto() {
        return textoNoStockProducto;
    }

    public void setTextoNoStockProducto(String textoNoStockProducto) {
        this.textoNoStockProducto = textoNoStockProducto;
    }

    public Date getFechaDisponibleProducto() {
        return fechaDisponibleProducto;
    }

    public void setFechaDisponibleProducto(Date fechaDisponibleProducto) {
        this.fechaDisponibleProducto = fechaDisponibleProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", skuProducto='" + skuProducto + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", activoProducto=" + activoProducto +
                ", descBreveProducto='" + descBreveProducto + '\'' +
                ", descripcionProducto='" + descripcionProducto + '\'' +
                ", precioMayoristaProducto=" + precioMayoristaProducto +
                ", precioVentaProducto=" + precioVentaProducto +
                ", precioVentaIvaProducto=" + precioVentaIvaProducto +
                ", impuestoProducto=" + impuestoProducto +
                ", textoStockProducto='" + textoStockProducto + '\'' +
                ", textoNoStockProducto='" + textoNoStockProducto + '\'' +
                ", fechaDisponibleProducto=" + fechaDisponibleProducto +
                ", cantidadProducto=" + cantidadProducto +
                '}';
    }
}
