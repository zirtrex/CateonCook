package net.inlanet.nextnetwork.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.inlanet.nextnetwork.Adapters.FinanciamientoCreditoDirectoRVAdapter;
import net.inlanet.nextnetwork.Interfaces.CartInteractionListener;
import net.inlanet.nextnetwork.Models.CreditoDirectoContent;
import net.inlanet.nextnetwork.Activities.R;

import java.text.NumberFormat;


public class CreditoDirectoFinanciamientoFragment extends Fragment {

    public static final String TAG = "CreditoDirectoFinanciamientoFragment";

    private CartInteractionListener cartInteractionListener;
    private CreditoDirectoPagoFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    View view;
    ImageView ly;

    TextView tvMontoTotal, tvEntrada;
    Double montoTotal = 0.00;

    public CreditoDirectoFinanciamientoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_credito_directo_financiamiento, container, false);

        actualizarMontoTotal();

        return view;
    }

    public void actualizarMontoTotal() {

        if(cartInteractionListener != null) {
            montoTotal = cartInteractionListener.getMonto();
            double nuevoMonto = this.montoTotal;
            double entrada = onFragmentInteractionListener.getEntrada();

            Log.w("Entrada", Double.toString(entrada));

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvFinanciamientos);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            if (entrada > 0.00) {
                nuevoMonto =   montoTotal - entrada;
                Log.w("Nuevo monto", Double.toString(nuevoMonto));
                CreditoDirectoContent creditoDirectoContent = new CreditoDirectoContent(nuevoMonto);
                recyclerView.setAdapter(new FinanciamientoCreditoDirectoRVAdapter(getParentFragment(), CreditoDirectoContent.FINANCIAMIENTOS, cartInteractionListener));

                tvMontoTotal = (TextView) view.findViewById(R.id.tvMontoTotal);
                tvEntrada = (TextView) view.findViewById(R.id.tvEntrada);
                tvMontoTotal.setText("El monto de compra total es: " + NumberFormat.getCurrencyInstance().format(montoTotal));
                tvEntrada.setText("Entrada de: " + NumberFormat.getCurrencyInstance().format(entrada));
            }

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            cartInteractionListener = (CartInteractionListener) context;
            this.montoTotal = cartInteractionListener.getMonto();
            Log.w("onAttach", "Credito Directo Financiamiento Atachado");
        } else {
            throw new RuntimeException(context.toString()
                    + "Se debe implementar CartInteractionListener");
        }

        if (getParentFragment() instanceof CreditoDirectoPagoFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (CreditoDirectoPagoFragment.OnFragmentInteractionListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartInteractionListener = null;
    }

}
