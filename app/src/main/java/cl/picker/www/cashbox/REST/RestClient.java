package cl.picker.www.cashbox.REST;

import java.util.List;

import cl.picker.www.cashbox.Producto;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Freddy on 21-11-2015.
 */
public interface RestClient {
   // @GET("/get_product_by_sku")
  //  void getProducto (Callback<Producto> callback);

    @GET("/get_product_by_sku")
    Producto getProductoBySku();
}
