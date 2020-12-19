package net.inlanet.cateoncook.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import net.inlanet.cateoncook.Activities.MainActivity;
import net.inlanet.cateoncook.Interfaces.CurrentProductInteractionListener;
import net.inlanet.cateoncook.Models.Cart;
import net.inlanet.cateoncook.Models.Producto;
import net.inlanet.cateoncook.Activities.R;

import java.text.NumberFormat;

public class ProductDetailFragment extends Fragment
        implements View.OnClickListener, CurrentProductInteractionListener {

    FirebaseAuth.AuthStateListener mAuthListener;

    MainActivity mainActivity;

    View view;

    Producto currentProduct;

    String nombreProducto, imgUrl;
    Double precio;

    private int mQuantity = 1;
    private double mTotalPrice;

    ImageView tvImagenProducto;
    TextView tvNombreProducto, tvPrecio, tvCostoTotal;
    Button btnAgregarProducto, btnIncrement, btnDecrement;

    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.w("session" , "Usuario Logueado");
                }else {
                    Log.i("session" , "Sin usuario activo");
                }
            }
        };

        tvImagenProducto = (ImageView) view.findViewById(R.id.ivCartImagenProducto);
        tvCostoTotal = (TextView) view.findViewById(R.id.tvCostoTotal);

        if (currentProduct != null) {

            nombreProducto = currentProduct.getNombreProducto();
            precio = currentProduct.getPrecio() * 1.12;
            //double precioIVA = precio * 1.12;
            String convertPrecioIVA = NumberFormat.getCurrencyInstance().format(precio);
            imgUrl = currentProduct.getImgUrl();

            tvNombreProducto = (TextView) view.findViewById(R.id.tvNombreProducto);
            tvNombreProducto.setText(nombreProducto);

            tvPrecio = (TextView) view.findViewById(R.id.tvPrecio);

            tvPrecio.setText(convertPrecioIVA);

            Glide.with(this)
                    .load(imgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.load)
                    .into(tvImagenProducto);

            btnAgregarProducto = (Button) view.findViewById(R.id.btnAgregarProducto);
            btnIncrement = (Button) view.findViewById(R.id.btnIncrement);
            btnDecrement = (Button) view.findViewById(R.id.btnDecrement);

            btnAgregarProducto.setOnClickListener(this);
            btnIncrement.setOnClickListener(this);
            btnDecrement.setOnClickListener(this);

            if (mQuantity == 1){
                mTotalPrice = precio;
                displayCost(mTotalPrice);
            }
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        getCart();
    }

    @Override
    public void onPause(){
        super.onPause();
        getCart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    public void increment(){
        mQuantity = mQuantity + 1;
        displayQuantity(mQuantity);
        mTotalPrice = mQuantity * this.precio;
        displayCost(mTotalPrice);
    }

    public void decrement(){
        if (mQuantity > 1){
            mQuantity = mQuantity - 1;
            displayQuantity(mQuantity);
            mTotalPrice = mQuantity * this.precio;
            displayCost(mTotalPrice);
        }
    }

    private void displayQuantity(int numberOfItems) {
        TextView tvCantidad = (TextView) view.findViewById(R.id.tvCantidad);
        tvCantidad.setText(String.valueOf(numberOfItems));
    }

    private void displayCost(double totalPrice) {

        String convertPrice = NumberFormat.getCurrencyInstance().format(totalPrice);
        tvCostoTotal.setText(convertPrice);
    }

    public void addToCart() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("¿Desea agregar el producto?");

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addValuesToCart();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addValuesToCart() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference cartDatabase  = database.getReference("cart");

        Cart cart = new Cart();

        String cartId = cartDatabase.push().getKey();

        cart.setCartId(cartId);
        cart.setCartNombreProducto(nombreProducto);
        cart.setCartPrecio(precio);
        cart.setCartImgUrl(imgUrl);
        cart.setCartCantidad(mQuantity);
        cart.setCartPrecioTotal(mTotalPrice);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){

            cartDatabase.child(user.getUid()).child(cartId).setValue(cart);
            Log.w("session" , "Usuario Logueado");
            getCart();
            Toast.makeText(getContext(), "Producto agregado correctamente",
                    Toast.LENGTH_LONG).show();


        }else {
            Log.i("session" , "Sin usuario activo");
            Toast.makeText(getContext(), "Necesitas iniciar sesión para guardar los datos.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getCart() {

        final int[] count = new int[1];

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference();

        Query carts;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            carts = myRef.child("cart").child(user.getUid());
        }else{
            carts = myRef.child("cart");
        }

        carts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count[0] = (int) dataSnapshot.getChildrenCount();
                updateNotificationsBadge(count[0]);

                //Log.w("Datos:", Long.toString(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //count = 0;
            }
        });
    }

    private void updateNotificationsBadge(final int count) {

        if (null != mainActivity) {
            mainActivity.updateNotificationsBadge(count);
            mainActivity.setCurrentProduct(null);
            //getActivity().onBackPressed();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAgregarProducto:
                addToCart();
                break;
            case R.id.btnIncrement:
                increment();
                break;
            case R.id.btnDecrement:
                decrement();
                break;
            default:

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            setCurrentProduct(mainActivity.getCurrentProduct());
            Log.w("Attach", "Detalle de Producto Atachado");
        } else {
            throw new RuntimeException(context.toString()
                    + "Se debe implementar CartInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public void setCurrentProduct(Producto product) {
        this.currentProduct = product;
    }

    @Override
    public Producto getCurrentProduct() {
        return this.currentProduct;
    }
}
