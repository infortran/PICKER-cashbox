package cl.picker.www.cashbox;

import android.os.Bundle;
import android.app.Fragment;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by Freddy on 10-11-2015.
 */
public class VolleyFragment extends Fragment{
    VolleyS volley;
    RequestQueue requestQueue;
    //public String base_url = "http://192.168.1.34:8080/picker/pickercashbox/";
    //public String base_url_assets = "http://192.168.1.34:8080/picker/assets/";
    //public String base_url_probador = "http://192.168.1.34:8080/picker/pickerprobador/";
    public String base_url = "http://www.picker.cl/pickercashbox/";
    public String base_url_assets = "http://www.picker.cl/assets/";
    public String base_url_probador = "http://www.picker.cl/pickerprobador/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyS.getInstance(getActivity().getApplicationContext());
        requestQueue = volley.getRequestQueue();
    }

    public void addToQueue(Request request){
        if (request != null) {
            request.setTag(this);
            if (requestQueue == null)
                requestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            onPreStartConnection();
            requestQueue.add(request);
        }
    }

    public void onPreStartConnection() {
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
