package net.inlanet.cateoncook.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import net.inlanet.cateoncook.Activities.R;


public class CreditoFragment extends Fragment {

    View view;
    WebView wvPdf;
    private SharedPreferences prefs;

    public CreditoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        return view;
    }



}
