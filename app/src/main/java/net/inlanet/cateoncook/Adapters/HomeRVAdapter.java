package net.inlanet.cateoncook.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.inlanet.cateoncook.Interfaces.ProductItemClickListener;
import net.inlanet.cateoncook.Activities.R;

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
        lElementos.add(new Elemento("SUGERENCIAS", "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/sugerencias.jpg?alt=media&token=2f8f194f-4f97-4bac-9880-77ca894e1ba4"));
        lElementos.add(new Elemento("SETS","https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/sets.jpg?alt=media&token=2e911147-d852-4355-a791-d7015f5bff5b"));
        lElementos.add(new Elemento("OMELETERAS Y PAELLERAS", "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/omeleteras_paelleras.jpg?alt=media&token=854c1fe9-6f24-4939-a12b-f17c6fa7b7dd"));
        lElementos.add(new Elemento("COMALES Y CUCHILLOS", "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/comales_cuchillos.jpg?alt=media&token=0ff64838-4404-47ed-8bb4-5ac3b2dce5b3"));
        lElementos.add(new Elemento("ACCESORIOS", "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/accesorios.jpg?alt=media&token=18e751f5-35fa-4194-86f7-7cbb74b547ad"));
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

        String imgUrl = elemento.getImgResource();
        //viewHolder.ivEImage.setImageResource(imgUrl);

        //String imgUrl = elemento.getImgResource();

        Glide.with(context.getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(viewHolder.ivEImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productItemClickListener.onCategoriaItemClick(viewHolder.getAdapterPosition(), elemento);

                Log.i("Categoria Seleccionada", elemento.getNombreElemento());

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
        private String imgResource;

        public Elemento() {}

        public Elemento(String nombreElemento, String imgResource) {
            this.nombreElemento = nombreElemento;
            this.imgResource = imgResource;
        }

        public String getNombreElemento() {
            return this.nombreElemento;
        }

        public void setNombreElemento(String nombreElemento) {
            this.nombreElemento = nombreElemento;
        }

        public String getImgResource() {
            return imgResource;
        }

        public void setImgResource(String imgResource) {
            this.imgResource = imgResource;
        }
    }

}
