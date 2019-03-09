package net.inlanet.cateoncook.Models;

import android.util.Log;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


public class EstablecimientoContent {

    Map<String, Establecimiento> hmEstablecimiento = new HashMap<String, Establecimiento>();

    Establecimiento establecimiento;

    public EstablecimientoContent(Map<String, Establecimiento> hmEstablecimiento, String tarjeta) {

        this.hmEstablecimiento = hmEstablecimiento;

        Log.w("Tarjeta", hmEstablecimiento.toString());

        establecimiento = hmEstablecimiento.get(tarjeta);

    }

    public Establecimiento getEstablecimiento(){
        return establecimiento;
    }

}
