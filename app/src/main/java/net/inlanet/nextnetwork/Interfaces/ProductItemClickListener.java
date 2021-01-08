package net.inlanet.nextnetwork.Interfaces;

import android.widget.ImageView;

import net.inlanet.nextnetwork.Models.Producto;
import net.inlanet.nextnetwork.Adapters.HomeRVAdapter;

public interface ProductItemClickListener {

    void onProductItemClick(int pos, Producto productItem, ImageView shareImageView);

    void onCategoriaItemClick(int pos, HomeRVAdapter.Elemento productItem);
}

