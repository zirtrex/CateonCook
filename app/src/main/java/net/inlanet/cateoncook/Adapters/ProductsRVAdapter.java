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

import java.text.NumberFormat;
import java.util.List;

import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Interfaces.ProductItemClickListener;
import net.inlanet.cateoncook.Models.Producto;


public class ProductsRVAdapter extends RecyclerView.Adapter<ProductsRVAdapter.ViewHolder> {

    private List<Producto> lProductos;
    private Context context;
    private final ProductItemClickListener productItemClickListener;


    public ProductsRVAdapter(Context context, List<Producto> productos, ProductItemClickListener listener){
        this.context = context;
        this.lProductos = productos;
        this.productItemClickListener = listener;
    }

    @Override
    public ProductsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_productos, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Producto getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        }

        return lProductos.get(position);
    }

    @Override
    public int getItemCount() {
        return lProductos.size();
    }

    @Override
    public void onBindViewHolder(final ProductsRVAdapter.ViewHolder viewHolder, int position) {

        final Producto producto = lProductos.get(position);

        viewHolder.tvProductTitle.setText(producto.getNombreProducto());

        Double precio = producto.getPrecio();

        if(precio != null){
            double precioIVA = precio * 1.12;
            String convertPrecioIVA = NumberFormat.getCurrencyInstance().format(precioIVA);
            viewHolder.tvPrecio.setText("Precio: " + String.valueOf(convertPrecioIVA));
        }else {
            viewHolder.tvPrecio.setText("");
        }

        String imgUrl = producto.getImgUrl();

        Glide.with(context.getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(viewHolder.ivProductImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productItemClickListener.onProductItemClick(viewHolder.getAdapterPosition(), producto, viewHolder.ivProductImage);

                Log.w("Datos:", producto.getNombreProducto());

            }
        });

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
        }
    }
}
