package net.inlanet.cateoncook.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import net.inlanet.cateoncook.Activities.R;


public class CatalogoFragment extends Fragment {

    View view;
    WebView wvPdf;
    private SharedPreferences prefs;

    public CatalogoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        /*wvPdf = (WebView) view.findViewById(R.id.wvCatalogo);

        wvPdf.getSettings().setJavaScriptEnabled(true);
        wvPdf.getSettings().setLoadsImagesAutomatically(true);
        wvPdf.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvPdf.loadUrl("http://inqmc.com/wp-content/uploads/inqmc/sites/5/2018/07/reporte-07052018171310.pdf");

        prefs = getActivity().getSharedPreferences("ayuntaap", getContext().MODE_PRIVATE);
        wvPdf = (WebView) view.findViewById(R.id.wvCatalogo);
        WebSettings settings = wvPdf.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
        wvPdf.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        String url = prefs.getString("http://inqmc.com/wp-content/uploads/inqmc/sites/5/2018/07/CATALOGO_ECUADOR_ABRIL_2018.pdf","");
        wvPdf.loadUrl(url);*/

        /*pdfViewCatalogo = (PDFView) view.findViewById(R.id.pdfViewCatalogo);
        //pdfViewCatalogo.fromAsset("CATALOGO_ECUADOR_ABRIL_2018.pdf").load();

        Intent intent = new Intent(getContext(), CatalogoActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/

        return view;
    }



}
