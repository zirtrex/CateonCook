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

import com.bumptech.glide.Glide;

import net.inlanet.nextnetwork.Interfaces.CartInteractionListener;
import net.inlanet.nextnetwork.Models.TarjetaCreditoContent;
import net.inlanet.nextnetwork.Activities.R;
import net.inlanet.nextnetwork.Adapters.FinanciamientoTarjetaCreditoRVAdapter;

import java.text.NumberFormat;


public class TarjetaCreditoFinanciamientoFragment extends Fragment {

    public static final String TAG = "TarjetaCreditoFinanciamientoFragment";

    CartInteractionListener cartInteractionListener;

    View view;
    TextView tvMontoTotal;
    Double montoTotal = 0.00;

    ImageView ly;

    public TarjetaCreditoFinanciamientoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tarjeta_credito_financiamiento, container, false);

        actualizarMontoTotal();

        ly = (ImageView) view.findViewById(R.id.ivMain);
        String imgUrl = "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/fondo_azul_y_blanco.png?alt=media&token=736e5ead-630d-4ffb-a99a-1649bfc09c12";
        Glide.with(getActivity().getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(ly);

        return view;
    }

    public void actualizarMontoTotal() {

        if (cartInteractionListener != null) {
            this.montoTotal = cartInteractionListener.getMonto();

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvFinanciamientos);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            TarjetaCreditoContent tarjetaCreditoContent = new TarjetaCreditoContent(this.montoTotal);
            recyclerView.setAdapter(new FinanciamientoTarjetaCreditoRVAdapter(getContext(), TarjetaCreditoContent.FINANCIAMIENTOS, cartInteractionListener));

            tvMontoTotal = (TextView) view.findViewById(R.id.tvMontoTotal);
            tvMontoTotal.setText("El monto de compra total es: " + NumberFormat.getCurrencyInstance().format(this.montoTotal));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            cartInteractionListener = (CartInteractionListener) context;
            Log.w("onAttach", "Tarjeta Credito Financiamiento Atachado");
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
