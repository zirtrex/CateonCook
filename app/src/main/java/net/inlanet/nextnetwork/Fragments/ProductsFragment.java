package net.inlanet.nextnetwork.Fragments;

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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.inlanet.nextnetwork.Activities.MainActivity;
import net.inlanet.nextnetwork.Interfaces.CurrentProductInteractionListener;
import net.inlanet.nextnetwork.Interfaces.ProductItemClickListener;
import net.inlanet.nextnetwork.Models.Producto;
import net.inlanet.nextnetwork.Activities.R;
import net.inlanet.nextnetwork.Adapters.HomeRVAdapter;
import net.inlanet.nextnetwork.Adapters.ProductsRVAdapter;

import java.util.LinkedList;
import java.util.List;

public class ProductsFragment extends Fragment implements ProductItemClickListener {

    public static final String TAG = "ProductsFragment";

    List<Producto> productos = new LinkedList<Producto>();
    RecyclerView rvProductos;
    ProductsRVAdapter productsRVAdapter;
    ViewGroup view;
    TextView tvSearchMessage;
    String categoriaApp, searchText;

    MainActivity mainActivityInteractionListener;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (ViewGroup) inflater.inflate(R.layout.fragment_productos, container, false);

        tvSearchMessage = (TextView) view.findViewById(R.id.tvSearchMessage);
        rvProductos = (RecyclerView) view.findViewById(R.id.rvProductos);

        if (categoriaApp != null) {
            getActivity().setTitle(categoriaApp);
            getProducts(categoriaApp);

        } else if(searchText != null){
            getActivity().setTitle("Resultado para: " + searchText);
            getProductsBySearch(searchText);
        }

        productsRVAdapter = new ProductsRVAdapter(getContext(), productos, this);
        rvProductos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProductos.setAdapter(productsRVAdapter);

        return view;
    }

    public void getProductsBySearch(String searchText) {
        //productos = new LinkedList<Producto>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("productosv12");
        Query productosSearchQuery;

        String newSearchText = searchText.toUpperCase();

        Log.i("Busqueda por", searchText);

        productosSearchQuery = myRef.orderByChild("nombreProducto").startAt(newSearchText).endAt(newSearchText + "\uf8ff");

        productosSearchQuery.addListenerForSingleValueEvent(productsEventListener);
    }

    private void getProducts(String categoriaApp){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("productosv12");
        Query productosQuery;

        Log.w("Categoria", categoriaApp);

        productosQuery = myRef.orderByChild("categoriaApp").equalTo(categoriaApp);

        productosQuery.addValueEventListener(productsEventListener);
    }

    ValueEventListener productsEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            productos.removeAll(productos);

            for(DataSnapshot prod : dataSnapshot.getChildren() ){
                Producto producto = prod.getValue(Producto.class);
                productos.add(producto);
            }
            productsRVAdapter.notifyDataSetChanged();

            if(productos.size() == 0){
                tvSearchMessage.setVisibility(View.VISIBLE);
            }else{
                tvSearchMessage.setVisibility(View.GONE);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    @Override
    public void onProductItemClick(int position, Producto productItem, ImageView sharedImageView) {

        if (position != RecyclerView.NO_POSITION){

            mainActivityInteractionListener.setCurrentProduct(productItem);

            Fragment productDetailFragment = new ProductDetailFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_mainLayout, productDetailFragment, "Product_Detail_Fragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null).commit();

            Log.i("INFO", "Producto Seleccionado: " + productItem.getNombreProducto());
        }
    }

    @Override
    public void onCategoriaItemClick(int position, HomeRVAdapter.Elemento categoria) {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CurrentProductInteractionListener) {
            mainActivityInteractionListener = (MainActivity) context;
            this.categoriaApp = mainActivityInteractionListener.getCurrentCategoria();
            this.searchText = mainActivityInteractionListener.getSearchText();
            Log.i("Productos", "Producto Atachado");
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
