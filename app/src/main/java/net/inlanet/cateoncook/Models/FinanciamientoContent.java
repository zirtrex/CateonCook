package net.inlanet.cateoncook.Models;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FinanciamientoContent {

    public static final List<Financiamiento> FINANCIAMIENTOS = new ArrayList<Financiamiento>();

    private static final int COUNT = 36;

    private int[] nroCuotas = {3, 6, 9, 12, 18, 24, 36};
    private double[] porcentajeInteres = {2.71, 4.79, 6.89, 9.02, 13.37, 17.82, 27.05};

    double montoTotal = 0;
    double impuesto;

    public FinanciamientoContent(double montoTotal) {

        FINANCIAMIENTOS.removeAll(FINANCIAMIENTOS);
        if(montoTotal != 0.00) {
            this.montoTotal = montoTotal;
            this.impuesto = this.montoTotal * 0.12;
            double total = this.montoTotal + this.impuesto;

            for (int i = 0; i < nroCuotas.length; i++) {

                double montoBruto = montoTotal + ((montoTotal * porcentajeInteres[i]) / 100);
                double montoCuota = montoBruto / nroCuotas[i];

                String convertMontoCuota = NumberFormat.getCurrencyInstance().format(montoCuota);

                addFinanciamiento(crearInversion(nroCuotas[i], convertMontoCuota));
            }
        }
    }

    private static void addFinanciamiento(Financiamiento item) {
        FINANCIAMIENTOS.add(item);
    }

    private static Financiamiento crearInversion(int nroCuota, String montoCuota) {
        return new Financiamiento(String.valueOf(nroCuota), " INVERSIONES DE ", montoCuota);
    }

    /**
     * Constructor para el listado de las inversiones
     */
    public static class Financiamiento {
        public final String nroCuotas;
        public final String texto;
        public final String monto;

        public Financiamiento(String nroCuotas, String texto, String monto) {
            this.nroCuotas = nroCuotas;
            this.texto = texto;
            this.monto = monto;
        }

        @Override
        public String toString() {
            return nroCuotas + texto + monto;
        }
    }
}
