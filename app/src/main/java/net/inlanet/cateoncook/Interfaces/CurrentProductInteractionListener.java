package net.inlanet.cateoncook.Interfaces;

import net.inlanet.cateoncook.Models.Producto;

public interface CurrentProductInteractionListener {

    void setCurrentProduct(Producto product);

    Producto getCurrentProduct();

}
