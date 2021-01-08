package net.inlanet.nextnetwork.Models;

import java.text.NumberFormat;


public class CreditoDirectoPagoContent {

    int nroCuotas;
    double montoTotal = 0.00;
    double efectivo = 0.00;
    double montoCuota = 0.00;
    double tvSubTotalConsumos = 0.00,
            tvIVA,
            tvTotalConsumos,
            tvSaldoAFinanciar,
            tvInteresMaximo;


    public CreditoDirectoPagoContent(double montoTotal, int cuotas, double entrada) {

        this.montoTotal = montoTotal;
        this.nroCuotas = cuotas;
        this.efectivo = entrada;

        tvSubTotalConsumos = this.montoTotal / 1.12;
        tvIVA = tvSubTotalConsumos * 0.12;
        tvTotalConsumos = tvSubTotalConsumos + tvIVA;
        tvSaldoAFinanciar = tvTotalConsumos - this.efectivo;
        montoCuota = tvSaldoAFinanciar / nroCuotas;
        tvInteresMaximo = 1.292;

    }

    public String getTvSubTotalConsumos() {
        return NumberFormat.getCurrencyInstance().format(tvSubTotalConsumos);
    }

    public String getTvIVA() {
        return NumberFormat.getCurrencyInstance().format(tvIVA);
    }

    public String getTvTotalConsumos() {
        return NumberFormat.getCurrencyInstance().format(tvTotalConsumos);
    }

    public String getTvEfectivo() {
        return NumberFormat.getCurrencyInstance().format(this.efectivo);
    }

    public String getTvSaldoAFinanciar() {
        return NumberFormat.getCurrencyInstance().format(tvSaldoAFinanciar);
    }

    public String getTvInteresMaximo() {
        return String.valueOf(tvInteresMaximo) + "% MV";
    }

}
