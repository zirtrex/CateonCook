package net.inlanet.nextnetwork.Interfaces;

public interface CartInteractionListener {

    void setMonto(double monto);

    Double getMonto();

    void updateNotificationsBadge(Integer count);

    //void changeItemFromViewPager(Integer position);

}
