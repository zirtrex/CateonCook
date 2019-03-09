package net.inlanet.cateoncook.Models;

import android.util.Log;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


public class ChequeContent {

    Map<Integer, Integer> hmCheques = new HashMap<Integer, Integer>();

    Double montoTotal = 0.00;
    Integer nroCuotas, tasaInteres;

    Double tvMonto = 0.00,
            tvEntrada,
            tvSaldoRestante,
            tvInteres,
            tvTotalPagar,
            tvMontoCheque;
    String tvPorcentajeInteres;

    public ChequeContent(double montoTotal, Map<Integer, Integer> hmCheques, int nroCuotas) {

        this.montoTotal = montoTotal;
        this.hmCheques = hmCheques;
        this.nroCuotas = nroCuotas;

        this.tasaInteres = hmCheques.get(nroCuotas);

        Log.w("Interes", String.valueOf(tasaInteres));
        Log.w("Número y Tasa", hmCheques.toString());

        if(this.tasaInteres != null) {

            tvMonto = this.montoTotal;
            tvEntrada = this.montoTotal * 0.4034;
            tvSaldoRestante = tvMonto - tvEntrada;
            tvInteres = (tvSaldoRestante * this.tasaInteres) / 100;
            tvTotalPagar = tvSaldoRestante + tvInteres;
            tvMontoCheque = tvTotalPagar / nroCuotas;
            tvPorcentajeInteres = "CON INTERÉS AL: " + this.tasaInteres + "%";
        }
    }

    public String getTvMonto() {
        String convert = NumberFormat.getCurrencyInstance().format(tvMonto);
        return convert;
    }

    public String getTvEntrada() {
        String convert = NumberFormat.getCurrencyInstance().format(tvEntrada);
        return convert;
    }

    public String getTvSaldoRestante() {
        String convert = NumberFormat.getCurrencyInstance().format(tvSaldoRestante);
        return convert;
    }

    public String getTvInteres() {
        String convert = NumberFormat.getCurrencyInstance().format(tvInteres);
        return convert;
    }

    public String getTvTotalPagar() {
        String convert = NumberFormat.getCurrencyInstance().format(tvTotalPagar);
        return convert;
    }

    public String getTvMontoCheque() {
        String convert = NumberFormat.getCurrencyInstance().format(tvMontoCheque);
        return convert;
    }

    public String getTvPorcentajeInteres() {
        return tvPorcentajeInteres;
    }

}
