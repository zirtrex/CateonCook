package net.inlanet.cateoncook.Interfaces;

public interface CartInteractionListener {

    void setMonto(double monto);

    Double getMonto();

    void updateNotificationsBadge(Integer count);

    //void changeItemFromViewPager(Integer position);

}
