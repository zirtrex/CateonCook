package net.inlanet.cateoncook.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.inlanet.cateoncook.Interfaces.OnFragmentInteractionListener;
import net.inlanet.cateoncook.Util.Utils;
import net.inlanet.cateoncook.Activities.R;
import net.inlanet.cateoncook.Adapters.FormasPagoAdapter;

public class FormasPagoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private View view;
    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;

    public FormasPagoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_formas_pago, container, false);

        getActivity().setTitle(getText(R.string.title_fragment_formas_de_pago));

        if(Utils.rotacion == 0) {

            View parent = (View) container.getParent();

            if (appBar == null) {

                appBar = (AppBarLayout) parent.findViewById(R.id.appBar);
                tabs = new TabLayout(getActivity());
                appBar.addView(tabs);

                viewPager = (ViewPager) view.findViewById(R.id.vpFormasPago);

                fillViewPager(viewPager);

                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });

                tabs.setupWithViewPager(viewPager);
            }
            tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        }else{
            Utils.rotacion = 1;
        }

        return view;
    }

    private void fillViewPager(ViewPager viewPager) {

        FormasPagoAdapter fpAdapter = new FormasPagoAdapter(getFragmentManager());

        fpAdapter.addFragments(new EfectivoTarjetaFragment(), "Efectivo y/o Tarjeta");
        fpAdapter.addFragments(new ChequeFragment(), "Pago en Cheque");

        viewPager.setAdapter(fpAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(Utils.rotacion == 0) {
            appBar.removeView(tabs);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
