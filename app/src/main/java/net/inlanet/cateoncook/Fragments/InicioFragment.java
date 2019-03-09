package net.inlanet.cateoncook.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.inlanet.cateoncook.Activities.MainActivity;
import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Adapters.HomeRVAdapter;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Interfaces.CurrentProductInteractionListener;
import net.inlanet.cateoncook.Interfaces.ProductItemClickListener;
import net.inlanet.cateoncook.Models.Producto;

public class InicioFragment extends Fragment implements ProductItemClickListener {

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

            Fragment productFragment = new ProductsFragment();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_mainLayout, productFragment,"ProductFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();

            Snackbar.make( view, "Elemento: " + categoria.getNombreElemento(),
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

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
