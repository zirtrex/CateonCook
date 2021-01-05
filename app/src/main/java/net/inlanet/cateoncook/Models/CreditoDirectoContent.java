package net.inlanet.cateoncook.Models;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CreditoDirectoContent {

    public static final List<Financiamiento> FINANCIAMIENTOS = new ArrayList<>();

    private static final int COUNT = 36;

    private int[] nroCuotas = {6, 12, 18, 24, 30};
    private double[] porcentajeInteres = {17.55, 9.16, 6.38, 4.99, 4.16};

    double montoTotal = 0;
    double impuesto;

    public CreditoDirectoContent(double montoTotal) {

        FINANCIAMIENTOS.removeAll(FINANCIAMIENTOS);
        if(montoTotal != 0.00) {
            this.montoTotal = montoTotal;
            this.impuesto = this.montoTotal * 0.12;
            double total = this.montoTotal + this.impuesto;

            for (int i = 0; i < nroCuotas.length; i++) {

                double montoBruto = montoTotal + ((montoTotal * porcentajeInteres[i]) / 100);
                double montoCuota = montoBruto / nroCuotas[i];

                addFinanciamiento(crearInversion(nroCuotas[i], montoCuota));
            }
        }
    }

    private static void addFinanciamiento(Financiamiento item) {
        FINANCIAMIENTOS.add(item);
    }

    private static Financiamiento crearInversion(int nroCuota, double montoCuota) {
        return new Financiamiento(String.valueOf(nroCuota), " INVERSIONES DE ", NumberFormat.getCurrencyInstance().format(montoCuota));
    }
}
