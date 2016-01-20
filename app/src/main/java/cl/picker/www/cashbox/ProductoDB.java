package cl.picker.www.cashbox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.Short4;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.picker.www.cashbox.REST.RestClient;
import cl.picker.www.cashbox.REST.ServiceGenerator;

/**
 * Created by Freddy on 19-11-2015.
 */
public class ProductoDB extends AsyncTask<String,String,Producto> {
    Context context;
    Producto productoAsync;
    ProgressDialog pDialog;

    public ProductoDB(){

    }

    public ProductoDB(Context context){
        this.context = context;
    }



    private Producto getSyncProduct(){
        RestClient syncProd = ServiceGenerator.createService(RestClient.class);
        Producto prodDb = syncProd.getProductoBySku();
        return prodDb;
    }


    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Cargando producto desde la base de datos");
        pDialog.setIndeterminate(false);
        pDialog.show();
    }

    @Override
    protected Producto doInBackground(String... params) {
        return getSyncProduct();
    }

    @Override
    protected void onPostExecute(Producto p) {
        this.productoAsync = p;
        pDialog.dismiss();
    }
}
