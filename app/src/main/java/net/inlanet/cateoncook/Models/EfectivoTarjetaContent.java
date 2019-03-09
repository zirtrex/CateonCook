package net.inlanet.cateoncook.Models;

import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class EfectivoTarjetaContent {

    Map<String, HashMap<Integer, Double>> hmTarjetas = new HashMap<String, HashMap<Integer, Double>>();

    String tarjeta;
    int nroCuotas;
    Double montoTotal = 0.00;
    Double tasaInteres;
    Double interesMensual;
    Double efectivo = 0.00;
    Double montoCuota = 0.00;
    Double tvConsumos = 0.00,
            tvSubTotalConsumos,
            tvIVA,
            tvTotalConsumos,
            tvInteresFinanciamientoDiferido,
            tvTotal;
    String tvFactor,
            tvResumenCuotas,
            tvInteresMensual;


    public EfectivoTarjetaContent(double montoTotal,
                                  Map<String, HashMap<Integer, Double>> hmTarjetas,
                                  String tarjeta,
                                  int cuotas,
                                  Double efectivo) {

        this.montoTotal = montoTotal;
        this.hmTarjetas = hmTarjetas;
        this.tarjeta = tarjeta;
        this.nroCuotas = cuotas;
        this.efectivo = efectivo;

        HashMap<Integer, Double> hmCoutas = hmTarjetas.get(tarjeta);
        this.tasaInteres = hmCoutas.get(String.valueOf(nroCuotas));

        Log.w("Interes", String.valueOf(tasaInteres));
        Log.w("Tarjeta y Nro Cuotas", hmCoutas.toString());

        if(this.tasaInteres != null) {

            tvTotalConsumos = this.montoTotal - this.efectivo;
            tvConsumos = tvTotalConsumos / 1.12;
            tvSubTotalConsumos = tvTotalConsumos / 1.12;
            tvIVA = tvTotalConsumos - tvConsumos;
            tvInteresFinanciamientoDiferido = (tvTotalConsumos * tasaInteres) / 100;
            tvTotal = tvTotalConsumos + tvInteresFinanciamientoDiferido;
            montoCuota = tvTotal / nroCuotas;
            interesMensual = montoCuota - (tvTotalConsumos / nroCuotas);

            tvFactor = "Con factor al: " + String.valueOf(tasaInteres) + "%";

            String convertMontoCuota = NumberFormat.getCurrencyInstance().format(montoCuota);
            tvResumenCuotas = nroCuotas + " FINANCIAMIENTOS DE " + convertMontoCuota;

            String convertInteresMensual = NumberFormat.getCurrencyInstance().format(interesMensual);
            tvInteresMensual = " INTERÉS MENSUAL DE: " + convertInteresMensual;
        }
    }

    public String getTvConsumos() {
        String convertInteresMensual = NumberFormat.getCurrencyInstance().format(tvConsumos);
        return convertInteresMensual;
    }

    public String getTvSubTotalConsumos() {
        String convertSubTotalConsumos = NumberFormat.getCurrencyInstance().format(tvSubTotalConsumos);
        return convertSubTotalConsumos;
    }

    public String getTvIVA() {
        String convertIVA = NumberFormat.getCurrencyInstance().format(tvIVA);
        return convertIVA;
    }

    public String getTvTotalConsumos() {
        String convertTotalConsumos = NumberFormat.getCurrencyInstance().format(tvTotalConsumos);
        return convertTotalConsumos;
    }

    public String getTvInteresFinanciamientoDiferido() {
        String convertInteresFinanciamientoDiferido = NumberFormat.getCurrencyInstance().format(tvInteresFinanciamientoDiferido);
        return convertInteresFinanciamientoDiferido;
    }

    public String getTvTotal() {
        String convertTotal = NumberFormat.getCurrencyInstance().format(tvTotal);
        return convertTotal;
    }

    public String getTvFactor() {
        return tvFactor;
    }

    public String getTvResumenCuotas() {
        return tvResumenCuotas;
    }

    public String getTvInteresMensual() {
        return tvInteresMensual;
    }

}
