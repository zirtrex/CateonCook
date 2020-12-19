package net.inlanet.cateoncook.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.inlanet.cateoncook.Activities.MainActivity;
import net.inlanet.cateoncook.Interfaces.CurrentProductInteractionListener;
import net.inlanet.cateoncook.Interfaces.ProductItemClickListener;
import net.inlanet.cateoncook.Models.Producto;
import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Adapters.HomeRVAdapter;

public class InicioFragment extends Fragment implements ProductItemClickListener {

    public static final String TAG = "InicioFragment";

    MainActivity mainActivityInteractionListener;
    RecyclerView rvInicio;
    HomeRVAdapter inicioRVAdapter;
    View view;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        rvInicio = (RecyclerView) view.findViewById(R.id.rvInicio);

        inicioRVAdapter = new HomeRVAdapter(getActivity(), this);
        rvInicio.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvInicio.setAdapter(inicioRVAdapter);

        return view;
    }

    @Override
    public void onProductItemClick(int position, Producto productItem, ImageView sharedImageView) {

    }

    @Override
    public void onCategoriaItemClick(int position, HomeRVAdapter.Elemento categoria) {

        if (position != RecyclerView.NO_POSITION){

            mainActivityInteractionListener.setCurrentCategoria(categoria.getNombreElemento());
            mainActivityInteractionListener.setSearchText(null);

            Fragment productsFragment = getActivity().getSupportFragmentManager().findFragmentByTag(ProductsFragment.TAG);

            if(productsFragment == null) {
                productsFragment = new ProductsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_mainLayout, productsFragment, ProductsFragment.TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

                Log.i("INFO", categoria.getNombreElemento().toString());
            }else if (!productsFragment.getClass().getName().equalsIgnoreCase(productsFragment.getClass().getName())) {
                productsFragment = new ProductsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_mainLayout, productsFragment, ProductsFragment.TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

                Log.i("INFO", categoria.getNombreElemento().toString());

            } else {
                //currentFragment es igual a newFragment
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CurrentProductInteractionListener) {
            mainActivityInteractionListener = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CurrentProductInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivityInteractionListener = null;
    }

}
