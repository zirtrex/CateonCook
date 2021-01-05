package net.inlanet.cateoncook.Fragments;


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
import android.widget.TextView;

import net.inlanet.cateoncook.Activities.R;

import net.inlanet.cateoncook.Interfaces.CartInteractionListener;

import java.text.NumberFormat;

public class CreditoDirectoFragment extends Fragment implements CreditoDirectoPagoFragment.OnFragmentInteractionListener {

    public static final String TAG = "CreditoDirectoFragment";

    private CartInteractionListener cartInteractionListener;

    View view;
    FrameLayout flFinanciamientos;
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

        tvMontoTotal = (TextView) view.findViewById(R.id.tvMontoTotal);
        txtEfectivo = (EditText) view.findViewById(R.id.txtEfectivo);
        txtEfectivo.setText("");
        btnGenerarPago = (Button) view.findViewById(R.id.btnGenerarPago);

        tvMontoTotal.setText(NumberFormat.getCurrencyInstance().format(montoTotal));

        creditoDirectoFinanciamientoFragment = new CreditoDirectoFinanciamientoFragment();

        final FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.popBackStack("CreditoDirectoFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_credito_directo_root, creditoDirectoFinanciamientoFragment, "CreditoDirectoFinanciamientoFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();

        flFinanciamientos = (FrameLayout) view.findViewById(R.id.fragment_credito_directo_root);
        flFinanciamientos.setVisibility(View.GONE);

        btnGenerarPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(txtEfectivo.getText())) {
                    entrada = Double.parseDouble(txtEfectivo.getText().toString());
                } else {
                    entrada = montoTotal * 0.05;
                }

                txtEfectivo.setText(String.valueOf((float) Math.round(entrada * 100) / 100));

                creditoDirectoFinanciamientoFragment.actualizarMontoTotal(entrada);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_credito_directo_root, creditoDirectoFinanciamientoFragment, "CreditoDirectoFinanciamientoFragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

                flFinanciamientos.setVisibility(View.VISIBLE);

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


