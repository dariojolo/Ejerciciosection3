package com.dariojolo.ejerciciosection3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.dariojolo.ejerciciosection3.R;
import com.dariojolo.ejerciciosection3.adapters.MyAdapter;
import com.dariojolo.ejerciciosection3.models.Fruta;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fruta> frutas;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // registerForContextMenu(recycler);


        frutas = getAllFrutas();
        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MyAdapter(frutas, R.layout.recycler_view_item, this, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Fruta fruta, int position) {
                //deleteMovie(position);
                //sumarFruta(position);
                fruta.addQuantity(1);
                adapter.notifyItemChanged(position);
            }
        });

        //Este metodo se puede usar cuando sabemos que el layout del recycler no van a cambiar de tamaño
        recycler.setHasFixedSize(true);
        //Se le agrega una animacion por defecto
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        //No registramos nada para el menu contexto, lo movemos al ViewHolder del adaptador
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_name:
                int position = frutas.size();
                frutas.add(position, new Fruta("Plumb: " + ++contador,"Fruta agregada por el usuario", R.drawable.plum_bg,R.mipmap.plum_ic,0));
                adapter.notifyItemInserted(position);
                layoutManager.scrollToPosition(position);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private List<Fruta> getAllFrutas() {
        return new ArrayList<Fruta>() {{
            add(new Fruta("Manzana", "Descripcion manzana",R.drawable.apple_bg,R.mipmap.apple_ic,0));
            add(new Fruta("Banana", "Descripcion Banana",R.drawable.banana_bg,R.mipmap.banana_ic,0));
            add(new Fruta("Cereza", "Descripcion Cereza",R.drawable.cherry_bg,R.mipmap.cherry_ic,0));
            add(new Fruta("Naranja", "Descripcion Naranja",R.drawable.orange_bg,R.mipmap.orange_ic,0));
            add(new Fruta("Pera", "Descripcion Pera",R.drawable.pear_bg,R.mipmap.pear_ic,0));
            add(new Fruta("Mora", "Descripcion Mora",R.drawable.raspberry_bg,R.mipmap.raspberry_ic,0));
            add(new Fruta("Frutilla", "Descripcion Frutilla",R.drawable.strawberry_bg,R.mipmap.strawberry_ic,0));
        }};
    }

    private void deleteMovie(int position){
        frutas.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
