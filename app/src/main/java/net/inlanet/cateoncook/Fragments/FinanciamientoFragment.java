package net.inlanet.cateoncook.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Adapters.InversionAdapter;
import net.inlanet.cateoncook.Interfaces.OnFragmentInteractionListener;
import net.inlanet.cateoncook.Models.InversionesContent;
import net.inlanet.cateoncook.Models.InversionesContent.Inversiones;

import java.text.NumberFormat;


public class FinanciamientoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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

        getActivity().setTitle(getText(R.string.title_fragment_financiamiento));

        if(getArguments() != null) {
            montoTotal = getArguments().getDouble("montoTotal");
        }

        tvMontoTotal = (TextView) view.findViewById(R.id.tvMontoTotal);
        String convertPrice = NumberFormat.getCurrencyInstance().format(montoTotal);
        tvMontoTotal.setText("El monto de compra total es: " + convertPrice);


        InversionesContent ic = new InversionesContent(montoTotal);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvFinanciamientos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new InversionAdapter(InversionesContent.INVERSIONES, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.montoTotal = mListener.getMonto();
        } else {
            throw new RuntimeException(context.toString()
                    + "Se debe implementar OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
