package cl.picker.www.cashbox.ListAdapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cl.picker.www.cashbox.Product;
import cl.picker.www.cashbox.R;

/**
 * Created by Freddy on 22-11-2015.
 */
public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> listaProductos;

    public ProductAdapter(Context context, List<Product> listaProducto){
        this.context = context;
        this.listaProductos = listaProducto;
    }
    @Override
    public int getCount() {
        return this.listaProductos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaProductos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.fila_producto_probador, parent, false);
        }

        ImageView imgProducto = (ImageView)rowView.findViewById(R.id.img_fila_producto_probador);
        TextView txtNombreProducto = (TextView)rowView.findViewById(R.id.nombre_prod_fila_probador);
        TextView txtPrecioProducto = (TextView)rowView.findViewById(R.id.precio_fila_producto_probador);

        Product prod = this.listaProductos.get(position);
        txtNombreProducto.setText(prod.getNombre());
        txtPrecioProducto.setText(String.valueOf(prod.getPrecio()));

        return rowView;
    }
}
