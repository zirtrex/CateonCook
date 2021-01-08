package net.inlanet.nextnetwork.Models;

public class Financiamiento {

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
