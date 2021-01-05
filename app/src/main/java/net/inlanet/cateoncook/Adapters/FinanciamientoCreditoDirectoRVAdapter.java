package net.inlanet.cateoncook.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Fragments.CreditoDirectoPagoFragment;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Models.Financiamiento;

import java.util.List;

public class FinanciamientoCreditoDirectoRVAdapter extends RecyclerView.Adapter<FinanciamientoCreditoDirectoRVAdapter.ViewHolder> {

    private Fragment context;
    private final List<Financiamiento> mValues;
    private final CartInteractionListener cartInteractionListener;

    public FinanciamientoCreditoDirectoRVAdapter(Fragment context, List<Financiamiento> items, CartInteractionListener listener) {
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

        final Financiamiento valor = mValues.get(position);

        viewHolder.mItem = mValues.get(position);
        viewHolder.tvNroCuotas.setText(mValues.get(position).nroCuotas);
        viewHolder.tvTexto.setText(mValues.get(position).texto);
        viewHolder.tvMonto.setText(mValues.get(position).monto);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != cartInteractionListener) {

                    int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Log.w("Financiamiento: ", getItem(position).nroCuotas);

                        String nroCuotas = getItem(position).nroCuotas;
                        String pagoMinimo = getItem(position).monto;

                        Bundle args = new Bundle();
                        args.putInt("nroCuotas", Integer.parseInt(nroCuotas));
                        args.putString("pagoMinimo", pagoMinimo);

                        Fragment creditoDirectoPagoFragment = new CreditoDirectoPagoFragment();
                        creditoDirectoPagoFragment.setArguments(args);

                        FragmentManager fragmentManager = ((Fragment) context).getChildFragmentManager();
                        fragmentManager.popBackStack("CreditoDirectoFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_credito_directo_root, creditoDirectoPagoFragment, "CreditoDirectoPagoFragment")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack("CreditoDirectoFragment")
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

    public Financiamiento getItem(int position) {
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
        public Financiamiento mItem;

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
