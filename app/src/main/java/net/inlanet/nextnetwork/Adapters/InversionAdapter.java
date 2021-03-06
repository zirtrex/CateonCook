package net.inlanet.nextnetwork.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.inlanet.nextnetwork.Interfaces.CartInteractionListener;
import net.inlanet.nextnetwork.Models.InversionesContent;

import java.util.List;

import net.inlanet.nextnetwork.Activities.R;

public class InversionAdapter extends RecyclerView.Adapter<InversionAdapter.ViewHolder> {

    private final List<InversionesContent.Inversiones> mValues;
    private final CartInteractionListener mListener;

    public InversionAdapter(List<InversionesContent.Inversiones> items, CartInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_financiamiento_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvNroCuotas.setText(mValues.get(position).nroCuotas);
        holder.tvTexto.setText(mValues.get(position).texto);
        holder.tvMonto.setText(mValues.get(position).monto);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.setMonto(0.00);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNroCuotas;
        public final TextView tvTexto;
        public final TextView tvMonto;
        public InversionesContent.Inversiones mItem;

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
