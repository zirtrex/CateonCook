package net.inlanet.cateoncook.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.inlanet.cateoncook.Fragments.FormaPagoFragment;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Models.FinanciamientoContent;

import java.util.List;

import net.inlanet.cateoncook.Activities.R;

public class FinanciamientoRVAdapter extends RecyclerView.Adapter<FinanciamientoRVAdapter.ViewHolder> {

    private Context context;
    private final List<FinanciamientoContent.Financiamiento> mValues;
    private final CartInteractionListener cartInteractionListener;

    public FinanciamientoRVAdapter(Context context, List<FinanciamientoContent.Financiamiento> items, CartInteractionListener listener) {
        this.context = context;
        mValues = items;
        cartInteractionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_financiamiento_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final FinanciamientoContent.Financiamiento valor = mValues.get(position);

        viewHolder.mItem = mValues.get(position);
        viewHolder.tvNroCuotas.setText(mValues.get(position).nroCuotas);
        viewHolder.tvTexto.setText(mValues.get(position).texto);
        viewHolder.tvMonto.setText(mValues.get(position).monto);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (null != cartInteractionListener) {

                //Log.w("Financiamiento: ", cartInteractionListener.getMonto().toString());

                //Log.w("Financiamiento:", valor.monto);

                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Log.w("Financiamiento: ", getItem(position).nroCuotas);

                    String nroCuotas = getItem(position).nroCuotas;

                    Bundle args = new Bundle();
                    args.putInt("nroCuotas", Integer.parseInt(nroCuotas));

                    Fragment formaPagoFragment = new FormaPagoFragment();
                    formaPagoFragment.setArguments(args);

                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_financiamiento_root, formaPagoFragment,"Fragment_Forma_Pago")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();

                }
            }
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public FinanciamientoContent.Financiamiento getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        }
        return mValues.get(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView tvNroCuotas;
        public final TextView tvTexto;
        public final TextView tvMonto;
        public FinanciamientoContent.Financiamiento mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNroCuotas = (TextView) view.findViewById(R.id.tvNroCuotas);
            tvTexto = (TextView) view.findViewById(R.id.tvTexto);
            tvMonto = (TextView) view.findViewById(R.id.tvMonto);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTexto.getText() + "'";
        }
    }
}
