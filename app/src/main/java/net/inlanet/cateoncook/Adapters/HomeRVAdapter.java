package net.inlanet.cateoncook.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Fragments.ProductsFragment;
import net.inlanet.cateoncook.Interfaces.ProductItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.ViewHolder> {

    private List<Elemento> lElementos;
    private Context context;
    private final ProductItemClickListener productItemClickListener;

    public HomeRVAdapter(Context context, ProductItemClickListener listener){
        this.context = context;
        this.initializeData();
        this.productItemClickListener = listener;
    }

    private void initializeData(){
        lElementos = new ArrayList<>();
        lElementos.add(new Elemento("SUGERENCIAS", R.drawable.sugerencias));
        lElementos.add(new Elemento("SETS", R.drawable.sets));
        lElementos.add(new Elemento("OMELETERAS Y PAELLERAS", R.drawable.omeleteras_paelleras));
        lElementos.add(new Elemento("COMALES Y CUCHILLOS", R.drawable.comales_cuchillos));
        lElementos.add(new Elemento("ACCESORIOS", R.drawable.accesorios));
    }

    @Override
    public HomeRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_inicio, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Elemento getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        }
        return lElementos.get(position);
    }

    @Override
    public int getItemCount() {
        return lElementos.size();
    }

    @Override
    public void onBindViewHolder(final HomeRVAdapter.ViewHolder viewHolder, int position) {

        final Elemento elemento = lElementos.get(position);

        viewHolder.tvETitle.setText(elemento.getNombreElemento());

        int imgUrl = elemento.getImgResource();
        viewHolder.ivEImage.setImageResource(imgUrl);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productItemClickListener.onCategoriaItemClick(viewHolder.getAdapterPosition(), elemento);

                Log.w("Datos:", elemento.getNombreElemento());

            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivEImage;
        public TextView tvETitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ivEImage = (ImageView) itemView.findViewById(R.id.ivEImage);
            tvETitle = (TextView) itemView.findViewById(R.id.tvETitle);
        }
    }

    public class Elemento {

        private String nombreElemento;
        private int imgResource;

        public Elemento() {}

        public Elemento(String nombreElemento, int imgResource) {
            this.nombreElemento = nombreElemento;
            this.imgResource = imgResource;
        }

        public String getNombreElemento() {
            return this.nombreElemento;
        }

        public void setNombreElemento(String nombreElemento) {
            this.nombreElemento = nombreElemento;
        }

        public int getImgResource() {
            return imgResource;
        }

        public void setImgResource(int imgResource) {
            this.imgResource = imgResource;
        }
    }

}
