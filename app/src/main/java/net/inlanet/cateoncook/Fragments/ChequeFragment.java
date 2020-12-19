package net.inlanet.cateoncook.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Models.ChequeContent;
import net.inlanet.cateoncook.Activities.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChequeFragment extends Fragment {

    private CartInteractionListener mListener;

    View view;
    ArrayAdapter spnrNumeroChequesAdapter;

    public static List<Integer> nroCheques = new ArrayList<Integer>();

    Map<Integer, Integer> hmCheques = new HashMap<Integer, Integer>();

    Spinner spnrNumeroCheques;

    TextView tvMonto, tvEntrada, tvSaldoRestante, tvInteres,
            tvTotalPagar, tvNumeroCheques, tvMontoCheque,
            tvPorcentajeInteres;

    Double montoTotal = 0.00;
    int nroChequesSeleccionado;

    ImageView ly;

    public ChequeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cheque, container, false);

        spnrNumeroCheques = (Spinner) view.findViewById(R.id.spnrNumeroCheques);
        spnrNumeroChequesAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, nroCheques);
        spnrNumeroChequesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrNumeroCheques.setAdapter(spnrNumeroChequesAdapter);

        tvMonto = (TextView) view.findViewById(R.id.tvMonto);
        tvEntrada = (TextView) view.findViewById(R.id.tvEntrada);
        tvSaldoRestante = (TextView) view.findViewById(R.id.tvSaldoRestante);
        tvInteres = (TextView) view.findViewById(R.id.tvInteres);
        tvTotalPagar = (TextView) view.findViewById(R.id.tvTotalPagar);
        tvNumeroCheques = (TextView) view.findViewById(R.id.tvNumeroCheques);
        tvMontoCheque = (TextView) view.findViewById(R.id.tvMontoCheque);
        tvPorcentajeInteres = (TextView) view.findViewById(R.id.tvPorcentajeInteres);

        getCheques();

        spnrNumeroCheques.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(spnrNumeroCheques.getSelectedItemPosition()  == position){

                    nroChequesSeleccionado = Integer.parseInt(spnrNumeroCheques.getSelectedItem().toString());

                    ChequeContent chequeContent;

                    if(mListener != null) {
                        chequeContent = new ChequeContent(montoTotal, hmCheques, nroChequesSeleccionado);

                        tvMonto.setText(chequeContent.getTvMonto());
                        tvEntrada.setText(chequeContent.getTvEntrada());
                        tvSaldoRestante.setText(chequeContent.getTvSaldoRestante());
                        tvInteres.setText(chequeContent.getTvInteres());
                        tvTotalPagar.setText(chequeContent.getTvTotalPagar());
                        tvNumeroCheques.setText(nroChequesSeleccionado + " Cheque(s) de");
                        tvMontoCheque.setText(chequeContent.getTvMontoCheque());
                        tvPorcentajeInteres.setText(chequeContent.getTvPorcentajeInteres());
                    }

                }else{
                    Toast.makeText(getActivity(),"Seleccione Tarjeta y Nro de Cuotas.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ly = (ImageView) view.findViewById(R.id.ivMain);

        String imgUrl = "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/fondo_amarillo_reducido.jpg?alt=media&token=2ecb8b3e-5621-4c2a-b6a4-475822bb0ac4";

        Glide.with(getActivity().getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(ly);

        return view;
    }

    private void getCheques() {

        final int[] count = new int[1];

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference();

        final Query cheques;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            cheques = myRef.child("cheques");
        }else{
            cheques = myRef.child("cheques");
        }

        cheques.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    hmCheques.put(Integer.parseInt(postSnapshot.getKey()), Integer.parseInt(postSnapshot.getValue().toString()));

                    Log.w("Datos: ", postSnapshot.getKey());
                    Log.w("Datos: ", postSnapshot.getValue().toString());
                }
                populateLCoutas();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //count = 0;
            }
        });
    }

    private void populateLCoutas(){

        Iterator<Map.Entry<Integer, Integer>> iCuotas = hmCheques.entrySet().iterator();
        nroCheques.removeAll(nroCheques);

        while (iCuotas.hasNext()){
            Map.Entry<Integer,Double> cuota = (Map.Entry) iCuotas.next();
            nroCheques.add(cuota.getKey());
            Log.w("Populate Cuotas", String.valueOf(cuota.getKey()));
        }
        Collections.sort(nroCheques);
        spnrNumeroChequesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            mListener = (CartInteractionListener) context;
            this.montoTotal = mListener.getMonto();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
