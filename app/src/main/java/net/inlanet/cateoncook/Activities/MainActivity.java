package net.inlanet.cateoncook.Activities;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.inlanet.cateoncook.Adapters.MainViewPagerAdapter;
import net.inlanet.cateoncook.Fragments.CartFragment;
import net.inlanet.cateoncook.Fragments.FinanciamientoFragment;
import net.inlanet.cateoncook.Fragments.TabContainerFragment;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Interfaces.CurrentCategoriaInteractionListener;
import net.inlanet.cateoncook.Interfaces.CurrentProductInteractionListener;
import net.inlanet.cateoncook.Models.Cart;
import net.inlanet.cateoncook.Models.Producto;
import net.inlanet.cateoncook.Util.Utils;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CartInteractionListener, CurrentProductInteractionListener,
        CurrentCategoriaInteractionListener {

    FirebaseAuth.AuthStateListener mAuthListener;

    FragmentManager fragmentManager;
    private TabContainerFragment tabContainerFragment;
    private CartFragment cartFragment;

    TextView tvUserEmail;
    Button btnLogin, btnLogout;

    DrawerLayout drawer;
    Toolbar toolbar;
    TabLayout tabs;

    Producto producto;
    String categoriaApp;

    int mNotificationsCount = 0;
    Double montoTotal = 0.00;

    List<Cart> lCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Inicio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.w("session" , "Usuario Logueado");
                    tvUserEmail.setText(user.getEmail());
                    tvUserEmail.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.GONE);
                    btnLogout.setVisibility(View.VISIBLE);
                }else {
                    Log.w("session" , "Sin usuario activo");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }
        };

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View header = navigationView.getHeaderView(0);

        if (savedInstanceState == null) {
            // withholding the previously created fragment from being created again
            // On orientation change, it will prevent fragment recreation
            // its necessary to reserving the fragment stack inside each tab
            initScreen();

        } else {
            // restoring the previously created fragment
            // and getting the reference
            tabContainerFragment = (TabContainerFragment) getSupportFragmentManager().findFragmentByTag("Fragment_Tab_Container");
        }

        tvUserEmail = (TextView) header.findViewById(R.id.tvUserEmail);
        btnLogin = (Button) header.findViewById(R.id.btnLogin);
        btnLogout = (Button) header.findViewById(R.id.btnLogout);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }

                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                btnLogin.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.GONE);
                tvUserEmail.setVisibility(View.GONE);
            }
        });

        lCart = new LinkedList<Cart>();

        getCart();

    }

    private void initScreen() {
        tabContainerFragment = new TabContainerFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, tabContainerFragment, "Fragment_Tab_Container")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCount);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_notifications:

                cartFragment = (CartFragment) fragmentManager.findFragmentByTag("Fragment_Cart");

                if(cartFragment == null){
                    cartFragment = new CartFragment();
                    fragmentManager.beginTransaction()
                            .add(R.id.frame_container, cartFragment, "Fragment_Cart")
                            .addToBackStack(null)
                            .commit();
                }else{
                    if(fragmentManager.getBackStackEntryCount() > 0) {
                        fragmentManager.popBackStack();
                    }
                    /*fragmentManager.beginTransaction()
                            .remove(cartFragment)
                           .addToBackStack("Fragment_Cart")
                           .commit();*/
                }

                return true;

            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /*int id = item.getItemId();

        Fragment miFragment = null;
        boolean fragmentSeleccionado = false;

        if (id == R.id.nav_productos) {

            //viewPager.setCurrentItem(0);

        }else if (id == R.id.nav_financiamientos) {

            //viewPager.setCurrentItem(1);

        }else if (id == R.id.nav_formas_de_pago) {

            //viewPager.setCurrentItem(2);

        }

        if (fragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment)
                    .addToBackStack(null).commit();
        }*/

        drawer.closeDrawer(GravityCompat.START);
        return true;
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

                lCart.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren() ){
                    Cart cart = snapshot.getValue(Cart.class);
                    lCart.add(cart);
                }
                calculateTotal(lCart);
                //Log.w("Datos:", Long.toString(dataSnapshot.getChildrenCount()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //count = 0;
            }
        });
    }

    public double calculateTotal(List<Cart> lcart){

        montoTotal = 0.00;

        for (int i = 0; i < lcart.size(); i++)
        {
            Double price = lcart.get(i).getCartPrecioTotal();
            montoTotal += price;
        }

        return montoTotal;
    }

    @Override
    public void setCurrentProduct(Producto product){
        this.producto = product;
    }

    @Override
    public Producto getCurrentProduct(){
        return this.producto;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public void saveMonto(Double montoTotal) {
        this.montoTotal = montoTotal;

        int vpId = tabContainerFragment.getViewPager().getId();
        String fragmentTag = makeFragmentName(vpId, 1);
        MainViewPagerAdapter vpAdapter = (MainViewPagerAdapter) tabContainerFragment.getViewPager().getAdapter();
        FinanciamientoFragment financiamientoFragment = (FinanciamientoFragment) vpAdapter.getItem(1);

        if(financiamientoFragment != null){
            financiamientoFragment.actualizarMontoCart();
            /*Toast.makeText(getApplicationContext(),
                    "fragmentB == null",
                    Toast.LENGTH_SHORT).show();*/
        }
        Log.w("Main Activity", "Se ha pulsado saveMonto " + String.valueOf(montoTotal));
        //Toast.makeText(getApplicationContext(), "Se ha pulsado saveMonto " + String.valueOf(montoTotal), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateNotificationsBadge(Integer count) {
        mNotificationsCount = count;

        //Forzamos la destrucción del menu
        invalidateOptionsMenu();
    }

    @Override
    public Double getMonto() {
        return this.montoTotal;
    }

    @Override
    public void setCurrentCategoria(String categoriaApp){
        this.categoriaApp = categoriaApp;
    }

    @Override
    public String getCurrentCategoria(){
        return categoriaApp;
    }

    /*@Override
    public void onFailure(@NonNull Exception exception) {
        int errorCode = ((StorageException) exception).getErrorCode();
        String errorMessage = exception.getMessage();
        // test the errorCode and errorMessage, and handle accordingly

    }*/
}