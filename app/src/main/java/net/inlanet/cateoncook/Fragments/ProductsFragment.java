package net.inlanet.cateoncook.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.inlanet.cateoncook.Activities.MainActivity;
import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Adapters.HomeRVAdapter;
import net.inlanet.cateoncook.Adapters.ProductsRVAdapter;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Interfaces.CurrentProductInteractionListener;
import net.inlanet.cateoncook.Interfaces.ProductItemClickListener;
import net.inlanet.cateoncook.Models.Producto;

import java.util.LinkedList;
import java.util.List;

public class ProductsFragment extends Fragment implements ProductItemClickListener{

    List<Producto> productos;
    RecyclerView rvProductos;
    ProductsRVAdapter productsRVAdapter;
    ViewGroup view;
    String categoriaApp;

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

        rvProductos = (RecyclerView) view.findViewById(R.id.rvProductos);

        if (categoriaApp != null) {

            getActivity().setTitle(categoriaApp);

            getProducts(categoriaApp);

        }

        productsRVAdapter = new ProductsRVAdapter(getContext(), productos, this);
        rvProductos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProductos.setAdapter(productsRVAdapter);

        return view;
    }

    private void getProducts(String categoriaApp){
        productos = new LinkedList<Producto>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("productos");
        Query productosQuery;

        Log.w("Categoria", categoriaApp);

        productosQuery = myRef.child(categoriaApp);

        productosQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productos.removeAll(productos);
                for(DataSnapshot snapshot : dataSnapshot.getChildren() ){
                    Producto producto = snapshot.getValue(Producto.class);
                    productos.add(producto);
                }
                productsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onProductItemClick(int position, Producto productItem, ImageView sharedImageView) {

        if (position != RecyclerView.NO_POSITION){

            mainActivityInteractionListener.setCurrentProduct(productItem);

            Fragment productDetailFragment = new ProductDetailFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_mainLayout, productDetailFragment, "Product_Detail_Fragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null).commit();

            Snackbar.make( view, "Elemento: " + productItem.getNombreProducto(),
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
    @Override
    public void onCategoriaItemClick(int position, HomeRVAdapter.Elemento categoria) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CurrentProductInteractionListener) {
            mainActivityInteractionListener = (MainActivity) context;
            this.categoriaApp = mainActivityInteractionListener.getCurrentCategoria();
            Log.w("Productos", "Producto Atachado");
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
