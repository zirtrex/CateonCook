package net.inlanet.cateoncook.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Fragments.ProductDetailFragment;
import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Models.Productos;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final CartInteractionListener mListener;

    private List<Productos> lProductos;
    private Context context;


    public RecyclerAdapter(Context context, List<Productos> productos, CartInteractionListener listener){
        this.lProductos = productos;
        mListener = listener;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        Productos producto = lProductos.get(position);

        viewHolder.tvProductTitle.setText(producto.getNombreProducto());

        if(producto.getPrecio() != null){
            viewHolder.tvPrecio.setText("Precio: $" + producto.getPrecio().toString());
        }else {
            viewHolder.tvPrecio.setText("");
        }

        String imgUrl = producto.getImgUrl();

        Glide.with(context.getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(viewHolder.ivProductImage);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Productos getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        }

        return lProductos.get(position);

    }

    @Override
    public int getItemCount() {
        return lProductos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        public ImageView ivProductImage;
        public TextView tvProductTitle, tvPrecio;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductImage = (ImageView) itemView.findViewById(R.id.ivProductImage);
            tvProductTitle = (TextView) itemView.findViewById(R.id.tvProductTitle);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){

                        // Creamos un nuevo Bundle para pasar el monto
                        Bundle args = new Bundle();
                        // Colocamos el monto total
                        args.putString("nombreProducto", getItem(position).getNombreProducto());
                        args.putString("imgUrl", getItem(position).getImgUrl());
                        args.putDouble("precio", getItem(position).getPrecio());

                        Fragment fProductDetailFragment = new ProductDetailFragment();
                        fProductDetailFragment.setArguments(args);
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_main, fProductDetailFragment,"Fragment Product Detail")
                                .addToBackStack(null)
                                .commit();

                        /*Snackbar.make( v, "Click detected on item " + position,
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();*/

                    }

                }
            });
        }
    }
}
