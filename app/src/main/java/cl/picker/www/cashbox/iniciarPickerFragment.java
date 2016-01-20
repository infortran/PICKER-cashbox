package cl.picker.www.cashbox;


import android.app.Dialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.picker.www.cashbox.ListAdapters.ProductAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class iniciarPickerFragment extends VolleyFragment {

    TextView numProbador, idProbador;
    ListView listaProductosSelect;
    String idProbadorArg;

    int numberProbador,
    estadoProbadorArg,
    idAdminArg;

    Button btnAgregarPorCaja, btnFinalizarVenta, btnSiFinalizarVenta, btnNoFinalizarVenta;

    public iniciarPickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberProbador = getArguments().getInt("num_probador");
            idProbadorArg = getArguments().getString("id_probador");
            estadoProbadorArg = getArguments().getInt("estado_probador");
            idAdminArg = getArguments().getInt("id_admin");

            Log.d("number probador", ""+numberProbador);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_iniciar_picker, container, false);
        numProbador = (TextView)view.findViewById(R.id.numero_probador_init_picker);
        idProbador = (TextView)view.findViewById(R.id.id_probador_init_picker);
        listaProductosSelect = (ListView)view.findViewById(R.id.lista_productos_por_probador);
        numProbador.setText(String.valueOf(numberProbador));
        idProbador.setText(idProbadorArg);
        btnAgregarPorCaja = (Button)view.findViewById(R.id.btn_agregar_por_caja);
        btnAgregarPorCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnFinalizarVenta = (Button)view.findViewById(R.id.btn_finalizar_venta);
        btnFinalizarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFinalizarVenta(idProbadorArg, idAdminArg);
            }
        });

        leerCarrito(String.valueOf(idAdminArg), idProbadorArg);
        return view;
    }

    private void dialogFinalizarVenta(final String idProbador, final int idAdmin){
        final Dialog dialogo = new Dialog(getActivity());
        dialogo.setTitle("Finalizar la Venta");
        dialogo.setContentView(R.layout.dialog_finalizar_venta);
        dialogo.show();

        btnSiFinalizarVenta = (Button)dialogo.findViewById(R.id.btn_si_finalizar_venta);
        btnSiFinalizarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarVenta(idProbador, idAdmin);
                dialogo.dismiss();
            }
        });
        btnNoFinalizarVenta = (Button)dialogo.findViewById(R.id.btn_no_finalizar_venta);
        btnNoFinalizarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });
    }

    private void cargarLista(List productos){

    }

    private void leerCarrito(String _idAdmin, String _idProbador){
        String url = base_url+"leer_carrito";

        Map<String, String> params = new HashMap<>();
        params.put("id_admin", _idAdmin);
        params.put("id_probador", _idProbador);

        CustomRequest request = new CustomRequest(
                Request.Method.POST,
                url,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List productosLista = new ArrayList();
                for(int i = 0; i < response.length();i++){
                    String nombreProd = "prod_"+i;
                    try {
                        JSONObject productoSel = response.getJSONObject(nombreProd);

                        String nombreProdList = productoSel.getString("nombre_prod");
                        int precioProdList = productoSel.getInt("precio_venta_iva_prod");

                        productosLista.add(new Product(nombreProdList,precioProdList,"0"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listaProductosSelect.setAdapter(new ProductAdapter(getActivity(), productosLista));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        addToQueue(request);
    }

    private void addInitProduct(String skuProd){
        String url = base_url_probador+"add_init_product";

        Map<String,String> params = new HashMap<>();
        params.put("sku_producto",skuProd);

        CustomRequest addInitProd = new CustomRequest(
                Request.Method.POST,
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //si el producto es encontrado procesar la lista
                        //si no es encontrado mostrar alerta que el producto no esta en la base de datos
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                     }
                }){
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("Content-Type", "application/x-www-form-urlencoded"); not used this

                    Log.d("getHeaders", params.toString());
                    return params;
                }
            };

        addToQueue(addInitProd);
    }

    private void finalizarVenta(final String idProbador, final int idAdmin){
        String url = base_url_probador+"finalizar_venta_probador";

        Map<String, String> params = new HashMap<>();
        params.put("id_admin", String.valueOf(idAdmin));
        params.put("id_probador", idProbador);

        CustomRequest finalizarVenta = new CustomRequest(
                Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final Boolean desactivado = response.getBoolean("desactivar");
                            if(desactivado){
                                Toast.makeText(getActivity().getApplicationContext(),"Picker Desactivado",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(),"Este Picker no esta activo",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/x-www-form-urlencoded"); not used this

                Log.d("getHeaders", params.toString());
                return params;
            }
        };
        addToQueue(finalizarVenta);
    }
}
