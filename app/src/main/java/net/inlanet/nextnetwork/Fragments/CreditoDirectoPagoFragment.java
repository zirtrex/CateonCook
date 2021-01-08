package net.inlanet.nextnetwork.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.inlanet.nextnetwork.Interfaces.CartInteractionListener;
import net.inlanet.nextnetwork.Models.CreditoDirectoPagoContent;
import net.inlanet.nextnetwork.Activities.R;

public class CreditoDirectoPagoFragment extends Fragment {

    public static final String TAG = "CreditoDirectoPagoFragment";

    private CartInteractionListener cartInteractionListener;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    private View view;

    TextView tvSubTotalConsumos, tvIVA, tvTotalConsumos, tvEfectivo,
            tvSaldoAFinanciar, tvInteresMaximo, tvPagoMinimo;

    double montoTotal = 0.00;
    int nroCuotasSeleccionado;
    String pagoMinimo = "";

    public CreditoDirectoPagoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_credito_directo_pago, container, false);

        getActivity().setTitle("");

        tvSubTotalConsumos = (TextView) view.findViewById(R.id.tvSubTotalConsumos);
        tvIVA = (TextView) view.findViewById(R.id.tvIVA);
        tvTotalConsumos = (TextView) view.findViewById(R.id.tvTotalConsumos);
        tvEfectivo = (TextView) view.findViewById(R.id.tvEfectivo);
        tvSaldoAFinanciar = (TextView) view.findViewById(R.id.tvSaldoAFinanciar);
        tvInteresMaximo = (TextView) view.findViewById(R.id.tvInteresMaximo);
        tvPagoMinimo = (TextView) view.findViewById(R.id.tvPagoMinimo);

        this.montoTotal = cartInteractionListener.getMonto();

        if(getArguments() != null) {
            nroCuotasSeleccionado = getArguments().getInt("nroCuotas");
            pagoMinimo = getArguments().getString("pagoMinimo");
        }

        CreditoDirectoPagoContent cdpc;

        double entrada = onFragmentInteractionListener.getEntrada();

        cdpc = new CreditoDirectoPagoContent(this.montoTotal, this.nroCuotasSeleccionado, entrada);

        tvSubTotalConsumos.setText(cdpc.getTvSubTotalConsumos());
        tvIVA.setText(cdpc.getTvIVA());
        tvTotalConsumos.setText(cdpc.getTvTotalConsumos());
        tvEfectivo.setText(cdpc.getTvEfectivo());
        tvSaldoAFinanciar.setText(cdpc.getTvSaldoAFinanciar());
        tvInteresMaximo.setText(cdpc.getTvInteresMaximo());
        tvPagoMinimo.setText(pagoMinimo);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            cartInteractionListener = (CartInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartInteractionListener");
        }

        if (getParentFragment() instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartInteractionListener = null;
    }

    public interface OnFragmentInteractionListener {
        double getEntrada();
    }

}
