package net.inlanet.nextnetwork.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.inlanet.nextnetwork.Activities.R;

import net.inlanet.nextnetwork.Interfaces.CartInteractionListener;

import java.text.NumberFormat;

public class CreditoDirectoFragment extends Fragment implements
        CreditoDirectoPagoFragment.OnFragmentInteractionListener {

    public static final String TAG = "CreditoDirectoFragment";

    private CartInteractionListener cartInteractionListener;

    View view;
    //FrameLayout flFinanciamientos;
    ImageView ly;

    CreditoDirectoFinanciamientoFragment creditoDirectoFinanciamientoFragment;

    TextView tvMontoTotal;
    EditText txtEfectivo;
    Button btnGenerarPago;

    double montoTotal = 0.00;
    double entrada = 0.00;

    public CreditoDirectoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_credito_directo, container, false);

        ly = (ImageView) view.findViewById(R.id.ivMain2);
        String imgUrl = "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/fondo_azul_y_blanco.png?alt=media&token=736e5ead-630d-4ffb-a99a-1649bfc09c12";
        Glide.with(getActivity().getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(ly);

        tvMontoTotal = (TextView) view.findViewById(R.id.tvMontoTotal);
        txtEfectivo = (EditText) view.findViewById(R.id.txtEfectivo);
        txtEfectivo.setText("");
        btnGenerarPago = (Button) view.findViewById(R.id.btnGenerarPago);

        tvMontoTotal.setText(NumberFormat.getCurrencyInstance().format(montoTotal));

        creditoDirectoFinanciamientoFragment = new CreditoDirectoFinanciamientoFragment();

        //flFinanciamientos = (FrameLayout) view.findViewById(R.id.fragment_credito_directo_root);
        //flFinanciamientos.setVisibility(View.GONE);

        btnGenerarPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(txtEfectivo.getText())) {
                    entrada = Double.parseDouble(txtEfectivo.getText().toString());
                } else {
                    entrada = montoTotal * 0.05;
                }

                txtEfectivo.setText(String.valueOf((float) Math.round(entrada * 100) / 100));

                final FragmentManager fragmentManager = getChildFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_credito_directo_root, creditoDirectoFinanciamientoFragment, CreditoDirectoFinanciamientoFragment.TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

                creditoDirectoFinanciamientoFragment.actualizarMontoTotal();

            }
        });

        return view;
    }

    public void actualizarMontoTotal() {
        if(cartInteractionListener != null) {
            this.montoTotal = cartInteractionListener.getMonto();

            tvMontoTotal.setText(NumberFormat.getCurrencyInstance().format(montoTotal));
        }
    }

    @Override
    public double getEntrada() {
        return this.entrada;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            cartInteractionListener = (CartInteractionListener) context;
            this.montoTotal = cartInteractionListener.getMonto();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartInteractionListener = null;
    }
}


