package net.inlanet.nextnetwork.Interfaces;

import net.inlanet.nextnetwork.Models.Producto;

public interface CurrentProductInteractionListener {

    void setCurrentProduct(Producto product);

    Producto getCurrentProduct();

}
