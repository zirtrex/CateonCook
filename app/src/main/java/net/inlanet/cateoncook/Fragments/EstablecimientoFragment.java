package net.inlanet.cateoncook.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import net.inlanet.cateoncook.Models.Establecimiento;
import net.inlanet.cateoncook.Models.EstablecimientoContent;
import net.inlanet.cateoncook.Activities.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class EstablecimientoFragment extends Fragment implements View.OnClickListener {

    View view;
    CartInteractionListener cartInteractionListener;
    ArrayAdapter spnrTarjetasAdapter;

    public static List<String> lTarjetas = new ArrayList<>();

    Map<String, Establecimiento> hmEstablecimientos = new HashMap<String, Establecimiento>();

    Spinner spnrTarjetas;
    TextView tvCaducidad, tvCodigoEstablecimiento, tvCodigo2MesesGracia,
            tvTelQuito, tvTelGuayaquil, tvTelCuenca, tvNota;

    String tarjetaSeleccionada;

    ImageView ly;

    public EstablecimientoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_establecimiento, container, false);

        spnrTarjetas = (Spinner) view.findViewById(R.id.spnrTarjetas);

        getEstablecimientos();

        spnrTarjetasAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, lTarjetas);
        spnrTarjetasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrTarjetas.setAdapter(spnrTarjetasAdapter);

        tvCaducidad = (TextView) view.findViewById(R.id.tvCaducidad);
        tvCodigoEstablecimiento = (TextView) view.findViewById(R.id.tvCodigoEstablecimiento);
        tvCodigo2MesesGracia = (TextView) view.findViewById(R.id.tvCodigo2MesesGracia);
        tvTelQuito = (TextView) view.findViewById(R.id.tvTelQuito);
        tvTelGuayaquil = (TextView) view.findViewById(R.id.tvTelGuayaquil);
        tvTelCuenca = (TextView) view.findViewById(R.id.tvTelCuenca);
        tvNota = (TextView) view.findViewById(R.id.tvNota);


        spnrTarjetas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(spnrTarjetas.getSelectedItemPosition()  == position){

                    tarjetaSeleccionada = spnrTarjetas.getSelectedItem().toString();

                    EstablecimientoContent establecimientoContent;

                    establecimientoContent = new EstablecimientoContent(hmEstablecimientos, tarjetaSeleccionada);

                    if(establecimientoContent.getEstablecimiento() != null) {

                        tvCaducidad.setText(establecimientoContent.getEstablecimiento().getCaducidad());
                        tvCodigoEstablecimiento.setText(establecimientoContent.getEstablecimiento().getCodigo());
                        tvCodigo2MesesGracia.setText(establecimientoContent.getEstablecimiento().getCodigo2MesesGracia());
                        tvTelQuito.setText(establecimientoContent.getEstablecimiento().getTelefonos().get("Quito"));
                        tvTelGuayaquil.setText(establecimientoContent.getEstablecimiento().getTelefonos().get("Guayaquil"));
                        tvTelCuenca.setText(establecimientoContent.getEstablecimiento().getTelefonos().get("Cuenca"));
                        tvNota.setText(establecimientoContent.getEstablecimiento().getNota());

                    }


                }else{
                    Toast.makeText(getActivity(),"Seleccione Tarjeta.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        tvTelQuito.setOnClickListener(this);
        tvTelGuayaquil.setOnClickListener(this);
        tvTelCuenca.setOnClickListener(this);

        ly = (ImageView) view.findViewById(R.id.ivMain);

        String imgUrl = "https://firebasestorage.googleapis.com/v0/b/cateoncook.appspot.com/o/fondo_amarillo_reducido.jpg?alt=media&token=2ecb8b3e-5621-4c2a-b6a4-475822bb0ac4";

        Glide.with(getActivity().getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.load)
                .into(ly);

        return view;
    }

    private void getEstablecimientos() {

        final int[] count = new int[1];

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference();

        final Query tarjetas;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            tarjetas = myRef.child("establecimientos");
        }else{
            tarjetas = myRef.child("establecimientos");
        }

        tarjetas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    Establecimiento establecimiento = postSnapshot.getValue(Establecimiento.class);

                    hmEstablecimientos.put(postSnapshot.getKey(), establecimiento);

                    Log.w("Establecimiento Key", postSnapshot.getKey());
                    Log.w("Establecimiento Data", postSnapshot.getValue().toString());
                }
                populateLTarjetas();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //count = 0;
            }
        });
    }

    private void populateLTarjetas(){

        Iterator<Map.Entry<String, Establecimiento>> iTarjetas = hmEstablecimientos.entrySet().iterator();
        lTarjetas.removeAll(lTarjetas);

        while (iTarjetas.hasNext()){
            Map.Entry<String, HashMap<Integer,Double>> tarjeta = (Map.Entry) iTarjetas.next();
            lTarjetas.add(tarjeta.getKey());
            Log.w("Populate Tarjetas", tarjeta.getKey());
        }
        spnrTarjetasAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        String telefono = "";

        switch (view.getId()) {

            case R.id.tvTelCuenca:
                telefono = tvTelCuenca.getText().toString();
                break;
            case R.id.tvTelGuayaquil:
                telefono = tvTelGuayaquil.getText().toString();
                break;
            case R.id.tvTelQuito:
                telefono = tvTelQuito.getText().toString();
                break;
            default:
                break;

        }

        if(telefono != "") {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefono, null));
            startActivity(intent);
        }
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartInteractionListener = null;
    }

}
