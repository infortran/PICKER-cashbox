package cl.picker.www.cashbox;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.picker.www.cashbox.DBObjects.Probadores;
import cl.picker.www.cashbox.REST.RestClient;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProbadoresFragment extends VolleyFragment {

    GridLayout containerButtons;
    Button btnSiActivarPicker, btnNoActivarPicker, btnAceptarSku, btnCancelarSku;
    EditText skuNewProdInit, cantidadProdInit;

    public ProbadoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_probadores, container, false);

        containerButtons = (GridLayout)view.findViewById(R.id.container_button_pickers);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPickersFromDataBase();
    }

    private void getUsedPickerFragmentById(Probadores probador){
        Bundle bundle = new Bundle();
        bundle.putString("id_probador", probador.getId_probador());
        bundle.putInt("num_probador", probador.getNumero_probador());
        bundle.putInt("estado_probador", probador.getEstado_probador());
        bundle.putInt("id_admin", probador.getId_admin());

        iniciarPickerFragment iniciarPicker = new iniciarPickerFragment();
        iniciarPicker.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.setCustomAnimations(R.anim.gla_there_come,R.anim.gla_there_gone);

        transaction.replace(R.id.cuerpo_dinamico, iniciarPicker);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void getNewPickerFragmentById(final Probadores probador){
        final Dialog customDialog = new Dialog(getActivity());
        customDialog.setContentView(R.layout.drawer_activar_picker);
        customDialog.setTitle("Activar Picker");
        customDialog.show();

        btnSiActivarPicker = (Button)customDialog.findViewById(R.id.dialog_yes_activar_picker);
        btnSiActivarPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                addInitProductDialog(probador);
            }
        });

        btnNoActivarPicker = (Button)customDialog.findViewById(R.id.dialog_no_activar_picker);
        btnNoActivarPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();

            }
        });
    }

    private void addInitProductDialog(final Probadores probador){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Agregar un producto de inicio");
        dialog.setContentView(R.layout.dialog_agregar_producto_sku);
        dialog.show();

        skuNewProdInit = (EditText)dialog.findViewById(R.id.sku_new_product_init);
        cantidadProdInit = (EditText)dialog.findViewById(R.id.cantidad_new_product_init);

        btnAceptarSku = (Button)dialog.findViewById(R.id.btn_aceptar_sku);
        btnAceptarSku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skuProd = skuNewProdInit.getText().toString();
                String cantidadProd = cantidadProdInit.getText().toString();

                addInitProduct(skuProd, cantidadProd, probador);
                dialog.dismiss();
            }
        });
        btnCancelarSku = (Button)dialog.findViewById(R.id.btn_cancelar_sku);
        btnCancelarSku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void addInitProduct(String sku, String cantidad, final Probadores probador){
        String url = base_url+"add_init_product";

        Map<String,String> params = new HashMap<>();
        params.put("sku_producto", sku);
        params.put("cantidad_prod", cantidad);
        params.put("id_probador", probador.getId_probador());


        CustomRequest addInitProd = new CustomRequest(
                Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.getString("valid");
                            if(respuesta.equals("0")){
                                Toast.makeText(getActivity().getApplicationContext(), "Ud no tiene ningun producto registrado con este SKU", Toast.LENGTH_LONG).show();
                            }else if(respuesta.equals("2")){
                                Toast.makeText(getActivity().getApplicationContext(), "Este SKU esta duplicado, consulte con el desarrollador", Toast.LENGTH_LONG).show();
                            }else{
                                Bundle bundle = new Bundle();
                                bundle.putString("id_probador", probador.getId_probador());
                                bundle.putInt("num_probador", probador.getNumero_probador());
                                bundle.putInt("estado_probador", probador.getEstado_probador());
                                bundle.putInt("id_admin", probador.getId_admin());

                                iniciarPickerFragment iniciarPicker = new iniciarPickerFragment();
                                iniciarPicker.setArguments(bundle);
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();

                                transaction.setCustomAnimations(R.anim.gla_there_come, R.anim.gla_there_gone);

                                transaction.replace(R.id.cuerpo_dinamico, iniciarPicker);
                                transaction.addToBackStack(null);
                                transaction.commit();

                                activarPicker(probador.getId_admin(), probador.getId_probador());
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
        addToQueue(addInitProd);
    }

    //CARGAR LOS PICKERS EN LA PANTALLA INICIAL
    private void getPickersFromDataBase(){
        //URL del webservice
        String url = base_url+"get_pickers_from_database";

        //Parametros que enviaremos al webservice
        Map<String,String> parametros = new HashMap<>();
        parametros.put("id_admin","1");

        //Dialogo que mostraremos mientras hace el proceso

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Obteniendo Informacion de Probadores PICKER...");
        pDialog.setIndeterminate(false);
        pDialog.show();

        //JSONObject Request con Volley
        CustomRequest jsonObjReq = new CustomRequest(
                Request.Method.POST,
                url,parametros,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //RESPUESTA DEL WEB SERVICE
                        final Button[] boton = new Button[jsonObject.length()];


                        LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        LinearLayout.LayoutParams paramsCapa = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        paramsCapa.weight = 1;



                        for(int i = 0; i < jsonObject.length();i++){
                            String pickername = "picker_"+i;
                            try {
                                JSONObject picker = jsonObject.getJSONObject(pickername);

                                final Probadores probador = new Probadores();

                                probador.setId_probador(picker.getString("id_probador"));
                                probador.setEstado_probador(picker.getInt("estado_probador"));
                                probador.setNumero_probador(picker.getInt("numero_probador"));
                                probador.setId_admin(picker.getInt("id_tienda"));
                                final boolean activeProbador = picker.getBoolean("active_probador");


                                boton[i] = new Button(getActivity());
                                boton[i].setLayoutParams(paramsButton);
                                if(String.valueOf(probador.getEstado_probador()).equals("1")){
                                    if(activeProbador){
                                        boton[i].setBackground(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.new_picker_usado));
                                        boton[i].setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                getUsedPickerFragmentById(probador);
                                            }
                                        });
                                    }else{
                                        boton[i].setBackground(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.new_picker_libre));
                                        boton[i].setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                getNewPickerFragmentById(probador);
                                            }
                                        });
                                    }

                                }else{
                                    boton[i].setBackground(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.new_picker_no_disponible));
                                }


                                TextView textoBoton = new TextView(getActivity());
                                textoBoton.setText("Probador "+probador.getNumero_probador());
                                textoBoton.setTextSize(25);
                                textoBoton.setGravity(Gravity.CENTER);

                                LinearLayout capa = new LinearLayout(getActivity());
                                capa.setLayoutParams(paramsCapa);
                                capa.setOrientation(LinearLayout.VERTICAL);
                                capa.addView(boton[i]);
                                capa.addView(textoBoton);

                                containerButtons.addView(capa);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //containerButtons.addView(setProbadores(jsonObject.length()));
                        //obtenerPickers(jsonObject);
                        try {
                            String error = jsonObject.getString("err");
                            if(error.equals("null")){
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Probadores")
                                        .setMessage("No se ha encontrado ningún probador registrado a esta tienda")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(),"error desconocido",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.dismiss();
                        onConnectionFinished();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error de conexión")
                                .setMessage(volleyError.toString())
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                        pDialog.dismiss();
                    }
                }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/x-www-form-urlencoded"); not used this

                Log.d("getHeaders", params.toString());
                return params;
            }


        };
        addToQueue(jsonObjReq);
    }


    //
    private void activarPicker(final int idAdmin, final String idProbador){
        String url = base_url_probador+"add_picker_to_active";

        Map<String,String> params = new HashMap<>();
        params.put("id_admin", String.valueOf(idAdmin));
        params.put("id_probador", idProbador);

        CustomRequest addActivePicker = new CustomRequest(
                Request.Method.POST,
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final Boolean activado = response.getBoolean("activar");
                            if(activado){
                                Toast.makeText(getActivity().getApplicationContext(), "Picker Activado",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(), "Este Picker ya esta activo",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),"error ql:"+error,Toast.LENGTH_LONG).show();
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
        addToQueue(addActivePicker);
    }









}
