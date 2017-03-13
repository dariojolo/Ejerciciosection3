package com.dariojolo.ejerciciosection3.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dariojolo.ejerciciosection3.R;
import com.dariojolo.ejerciciosection3.models.Fruta;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dario on 10/3/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Fruta> frutas;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    private Activity activity;

/*    public MyAdapter( List<Movie> movies, int layout, OnItemClickListener itemClickListener, OnItemLongClickListener itemLongClickListener){
        this.movies = movies;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }*/

    public MyAdapter(List<Fruta> frutas, int layout, Activity activity ,OnItemClickListener itemClickListener) {
        this.frutas = frutas;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(frutas.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return frutas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        //Implementamos las interfaces OnCreateContextMenuListener y OnMenuItemClickListener
        //para hacer uso del context menu en recyclerView y sobreescribimos los metodos
        public TextView textViewName;
        public TextView textViewDesc;
        public TextView textViewCant;
        public ImageView imageViewFruta;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.textViewNombre);
            textViewDesc = (TextView) v.findViewById(R.id.textViewDesc);
            textViewCant = (TextView) v.findViewById(R.id.textViewCant);
            imageViewFruta = (ImageView) v.findViewById(R.id.imageViewFruta);

            // Añadimos al view el listener para el context menu, en vez de hacerlo en el activity
            // Mediante el metodo registerForContextMenu
            itemView.setOnCreateContextMenuListener(activity);
        }

        public void bind(final Fruta fruta, final OnItemClickListener listener) {
            //Procesamos los datos a renderizar
            textViewName.setText(fruta.getNombre());
            textViewDesc.setText(fruta.getDescripcion());
            textViewCant.setText("" + fruta.getCantidad());
            //Logica aplicada para limitar  la cantidad de cada fruta
            if (fruta.getCantidad() == Fruta.LIMIT_QUANTITY) {
                textViewCant.setTextColor(ContextCompat.getColor(activity, R.color.colorAlert));
                textViewCant.setTypeface(null, Typeface.BOLD);
            } else {
                textViewCant.setTextColor(ContextCompat.getColor(activity, R.color.defaultTextColor));
                textViewCant.setTypeface(null, Typeface.NORMAL);
            }
            //Picasso
            Picasso.with(context).load(fruta.getImgFruta()).fit().into(imageViewFruta);
            //imageViewPoster.setImageResource(movie.getPoster());

            //Definimos que por cada elemento de nuestro RecyclerView, tenemos un clickListener
            //Que se comporta de la siguiente manera
            //itemView.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View v) {
            //       listener.onItemClick(fruta, getAdapterPosition());
            //   }
            //});
            //Añadimos el listener click para cada elemento fruta
            imageViewFruta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(fruta, getAdapterPosition());
                }
            });

        }

        // Sobreescribimos OnCreateContextMenu dentro del ViewHolder
        // En vez de hacerlo en el activity
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //Recogemos la posicion con el metodo getAdapterPosition
            Fruta frutaSeleccionada = frutas.get(this.getAdapterPosition());
            //Establecemos titulo e icono para cada elemento, mirando en sus propiedades
            contextMenu.setHeaderTitle(frutaSeleccionada.getNombre());
            contextMenu.setHeaderIcon(frutaSeleccionada.getImgIcono());
            //Inflamos el menu
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.context_menu, contextMenu);
            //Por ultimo añadimos uno por uno el listener onMenuItemClick para controlar las acciones en el contextMenu
            //Anteriormente lo manejabamos con el metodo onContextItemSelected en el activity
            for (int i = 0; i < contextMenu.size(); i++) {
                contextMenu.getItem(i).setOnMenuItemClickListener(this);
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            // No obtenemos nuestro objeto info
            // porque la posicion la podemos rescatar desde getAdapterPosition()
            switch (menuItem.getItemId()) {
                case R.id.del_fruta:
                    // Como estamos dentro del adaptador podemos acceder  a los metodos propios
                    // de èl como notifyItemRemove o notifyItemChange
                    frutas.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                case R.id.reset_fruta:
                    frutas.get(getAdapterPosition()).resetQuantity();
                    notifyItemChanged(getAdapterPosition());
                    return true;
                default:
                    return false;

            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Fruta fruta , int position);
    }
}


