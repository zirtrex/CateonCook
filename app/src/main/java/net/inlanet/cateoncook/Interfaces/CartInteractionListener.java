package net.inlanet.cateoncook.Interfaces;

public interface CartInteractionListener {

    void saveMonto(Double monto);

    Double getMonto();

    void updateNotificationsBadge(Integer count);

    //void changeItemFromViewPager(Integer position);

}
