package net.inlanet.cateoncook.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Interfaces.CartInteractionListener;
import net.inlanet.cateoncook.Models.Cart;
import net.inlanet.cateoncook.Models.Producto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HerramientasFragment extends Fragment {

    FirebaseAuth.AuthStateListener mAuthListener;

    public CartInteractionListener mListener;

    private View view;
    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;
    private Button btnGuardar;


    public HerramientasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_herramientas, container, false);

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        btnGuardar = (Button) view.findViewById(R.id.btnGuardarProductos);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream inputStream = getResources().openRawResource(R.raw.muestra_csv);
                CSVFile csvFile = new CSVFile(inputStream);
                ArrayList<Producto> productos = csvFile.read();

                agregarProductos(productos);
                Log.w("CSV" , "boton presionado");
            }
        });

        return view;
    }

    private void agregarProductos(ArrayList<Producto> productos) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference productosDatabase  = database.getReference("productos");

        //String idProducto = productosDatabase.push().getKey();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.w("CSV" , user.getUid());
            if(user.getUid().equals("w6Zzr56KB3Mn1Ne12mytA3S9fc73")){
                int i = 1;
                for(Producto producto:productos ) {
                    producto.setIdProducto(String.valueOf(i));
                    productosDatabase.child(producto.getCategoriaApp()).child(producto.getIdProducto()).setValue(producto);
                    i++;
                    Log.w("CSV" , producto.toString());
                }
            }else{
                Log.w("CSV" , "Usuario Denegado");
            }

        }else {
            Toast.makeText(getContext(), "Necesitas iniciar sesión para guardar los datos.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartInteractionListener) {
            this.mListener = (CartInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public class CSVFile {
        InputStream inputStream;

        public CSVFile(InputStream inputStream){
            this.inputStream = inputStream;
        }

        public ArrayList<Producto> read(){

            ArrayList<Producto> listProducto = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            try {
                String csvLine;
                while ((csvLine = reader.readLine()) != null) {
                    String[] row = csvLine.split(",");

                    Producto producto = new Producto();
                    producto.setCategoriaApp(row[0]);
                    producto.setCategoria(row[1]);
                    producto.setCodigo(row[2]);
                    producto.setNombreProducto(row[3]);
                    producto.setImgUrl(row[4]);
                    producto.setPrecio(Double.parseDouble(row[5]));
                    //Agregamos el producto creado a la lista
                    listProducto.add(producto);
                }
            }
            catch (IOException ex) {
                throw new RuntimeException("Error in reading CSV file: "+ex);
            }
            finally {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    throw new RuntimeException("Error while closing input stream: "+e);
                }
            }
            return listProducto;
        }
    }

}
