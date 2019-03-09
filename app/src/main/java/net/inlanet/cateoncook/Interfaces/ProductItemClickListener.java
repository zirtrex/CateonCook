package net.inlanet.cateoncook.Interfaces;

import android.widget.ImageView;

import net.inlanet.cateoncook.Adapters.HomeRVAdapter;
import net.inlanet.cateoncook.Models.Producto;

public interface ProductItemClickListener {
    void onProductItemClick(int pos, Producto productItem, ImageView shareImageView);

    void onCategoriaItemClick(int pos, HomeRVAdapter.Elemento productItem);
}

