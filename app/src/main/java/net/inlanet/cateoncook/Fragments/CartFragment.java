package net.inlanet.cateoncook.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Adapters.CartAdapter;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Models.Cart;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

public class CartFragment extends Fragment implements View.OnClickListener{

    CartInteractionListener cartInteractionListener;
    FirebaseAuth.AuthStateListener mAuthListener;

    View view;
    CartAdapter cartAdapter;
    RecyclerView rvCart;
    Double cartPrecioTotal = 0.00;
    List<Cart> lCart;
    Button btnFinanciamiento, btnCheques;
    TabLayout tabLayout;

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.w("Login" , "Usuario Logueado");
                }else {
                    Log.w("Login" , "Sin usuario activo");
                }
            }
        };

        rvCart = (RecyclerView) view.findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        lCart = new LinkedList<>();

        Query cartDatabase;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            cartDatabase = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getUid());
        }else{
            cartDatabase = FirebaseDatabase.getInstance().getReference().child("cart");
        }

        cartDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lCart.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren() ){
                    Cart cart = snapshot.getValue(Cart.class);
                    lCart.add(cart);
                }
                cartAdapter.notifyDataSetChanged();
                calculateTotal(lCart);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Initialize the adapter and attach it to the RecyclerView
        cartAdapter = new CartAdapter(getContext(), lCart);
        rvCart.setAdapter(cartAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                String id = (String) viewHolder.itemView.getTag();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){

                    DatabaseReference cartDatabase = FirebaseDatabase.getInstance().getReference("cart").child(user.getUid()).child(id);
                    cartDatabase.removeValue();
                    cartAdapter.notifyDataSetChanged();

                    Log.w("session" , "Usuario Logueado");
                    Toast.makeText(viewHolder.itemView.getContext(), "Producto eliminado correctamente",
                            Toast.LENGTH_LONG).show();

                }else {

                    Log.i("session" , "Sin usuario activo");
                    Toast.makeText(viewHolder.itemView.getContext(), "No tienes permiso para eliminar",
                            Toast.LENGTH_LONG).show();

                }

            }
        }).attachToRecyclerView(rvCart);

        btnFinanciamiento = (Button) view.findViewById(R.id.btnFinanciamiento);
        btnFinanciamiento.setOnClickListener(this);

        btnCheques = (Button) view.findViewById(R.id.btnCheques);
        btnCheques.setOnClickListener(this);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);

    }

    @Override
    public void onPause(){
        super.onPause();
        //getCart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    public void calculateTotal(List<Cart> lcart){

        Double cartPrecioTotal = 0.00;

        for (int i = 0; i < lcart.size(); i++)
        {
            Double price = lcart.get(i).getCartPrecioTotal();

            cartPrecioTotal += price;

        }

        this.cartPrecioTotal = cartPrecioTotal;

        TextView tvCartPrecioTotal = (TextView) view.findViewById(R.id.tvCartPrecioTotal);
        String convertPrice = NumberFormat.getCurrencyInstance().format(this.cartPrecioTotal);
        tvCartPrecioTotal.setText(convertPrice);

        if (null != cartInteractionListener) {
            cartInteractionListener.saveMonto(this.cartPrecioTotal);
            cartInteractionListener.updateNotificationsBadge(lcart.size());
        }

    }

    @Override
    public void onClick(View view) {

        // Creamos un nuevo Bundle para pasar el monto
        Bundle args = new Bundle();
        // Colocamos el monto total
        args.putDouble("montoTotal", cartPrecioTotal);

        switch (view.getId()) {
            case R.id.btnFinanciamiento:

                //getActivity().onBackPressed();

                if (null != cartInteractionListener) {
                    cartInteractionListener.saveMonto(this.cartPrecioTotal);
                }

                //Fragment fFinanciamiento = new FinanciamientoFragment();
                //fFinanciamiento.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();

                tabLayout.getTabAt(1).select();

                break;
            case R.id.btnCheques:

                if (null != cartInteractionListener) {
                    cartInteractionListener.saveMonto(this.cartPrecioTotal);
                }

                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();

                tabLayout.getTabAt(2).select();

                break;

            default:

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            cartInteractionListener = (CartInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "Se debe implementar CartInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartInteractionListener = null;
    }

}
