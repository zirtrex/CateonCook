package net.inlanet.nextnetwork.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.inlanet.nextnetwork.Activities.R;
import net.inlanet.nextnetwork.Interfaces.CartInteractionListener;

public class CatalogoFragment extends Fragment {

    public static final String TAG = "CatalogoFragment";

    public CartInteractionListener mListener;

    private View view;
    WebView webView;

    public CatalogoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        webView = (WebView) view.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "http://zirtrex.net/wp-content/uploads/2021/01/royal_prestige_catalogo.pdf";
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
