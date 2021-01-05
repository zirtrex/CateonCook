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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.inlanet.cateoncook.Adapters.FinanciamientoCreditoDirectoRVAdapter;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Models.CreditoDirectoContent;
import net.inlanet.cateoncook.Activities.R;

import java.text.NumberFormat;


public class CreditoDirectoFinanciamientoFragment extends Fragment {

    public static final String TAG = "CreditoDirectoFinanciamientoFragment";

    CartInteractionListener cartInteractionListener;

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

        actualizarMontoTotal(0.00);

        ly = (ImageView) view.findViewById(R.id.ivMain);
        String imgUrl = "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/fondo_financiamiento_inversiones.jpg?alt=media&token=20519f0e-21ec-418e-a7af-70461e8937ab";
        Glide.with(getActivity().getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(ly);

        return view;
    }

    public void actualizarMontoTotal(Double entrada) {

        if(cartInteractionListener != null) {
            montoTotal = cartInteractionListener.getMonto();
            Double nuevoMonto = montoTotal;

            Log.w("Monto Total", Double.toString(entrada));

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvFinanciamientos);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            if (entrada > 0.00) {
                nuevoMonto =   montoTotal - entrada;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartInteractionListener = null;
    }

}
