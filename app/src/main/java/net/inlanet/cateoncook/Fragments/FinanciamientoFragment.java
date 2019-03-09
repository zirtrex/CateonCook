package net.inlanet.cateoncook.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Adapters.FinanciamientoRVAdapter;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Models.FinanciamientoContent;

import java.text.NumberFormat;


public class FinanciamientoFragment extends Fragment {

    CartInteractionListener cartInteractionListener;

    View view;
    TextView tvMontoTotal;
    Double montoTotal = 0.00;

    public FinanciamientoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_financiamiento, container, false);

        actualizarMontoCart();

        return view;
    }

    public void actualizarMontoCart(){
        if(cartInteractionListener != null) {
            montoTotal = cartInteractionListener.getMonto();

            Log.w("Financiamiento", Double.toString(montoTotal));

            tvMontoTotal = (TextView) view.findViewById(R.id.tvMontoTotal);
            String convertPrice = NumberFormat.getCurrencyInstance().format(montoTotal);
            tvMontoTotal.setText("El monto de compra total es: " + convertPrice);

            FinanciamientoContent financiamientoContent = new FinanciamientoContent(montoTotal);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvFinanciamientos);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new FinanciamientoRVAdapter(getContext(), FinanciamientoContent.FINANCIAMIENTOS, cartInteractionListener));
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            cartInteractionListener = (CartInteractionListener) context;
            this.montoTotal = cartInteractionListener.getMonto();
            Log.w("onAttach", "Financiamiento Atachado");
        } else {
            throw new RuntimeException(context.toString()
                    + "Se debe implementar CartInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartInteractionListener = null;
    }

}
