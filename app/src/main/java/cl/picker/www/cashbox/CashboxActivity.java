package cl.picker.www.cashbox;

import android.app.Dialog;
import android.content.Context;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CashboxActivity extends AppCompatActivity {

    Context context = this;
    Button btnSiCerrarSesion, btnNoCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashbox);

        ProbadoresFragment probadores = new ProbadoresFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.cuerpo_dinamico, probadores);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cashbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0){
            cerrarSesion();
        }else{
            getFragmentManager().popBackStack();
        }
    }

    private void cerrarSesion(){
        final Dialog closeDialog = new Dialog(context);
        closeDialog.setTitle("Cerrar sesión");
        closeDialog.setContentView(R.layout.close_dialog);
        closeDialog.show();

        btnSiCerrarSesion = (Button)closeDialog.findViewById(R.id.btn_si_cerrar_sesion);
        btnNoCerrarSesion = (Button)closeDialog.findViewById(R.id.btn_no_cerrar_sesion);

        btnSiCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog.dismiss();
                finish();
            }
        });

        btnNoCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog.dismiss();
            }
        });
    }
}
