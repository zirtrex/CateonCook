package net.inlanet.nextnetwork.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.inlanet.nextnetwork.Activities.R;
import net.inlanet.nextnetwork.Adapters.MainViewPagerAdapter;


public class TabContainerFragment extends Fragment implements ViewPager.OnPageChangeListener{

    public static final String TAG = "TabContainerFragment";

    private View view;
    private TabLayout tabs;
    private ViewPager viewPager;
    private MainViewPagerAdapter vpAdapter;

    public TabContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tabs_container, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.vpMain);
        viewPager.addOnPageChangeListener(this);

        tabs = (TabLayout) getActivity().findViewById(R.id.tabs);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fillViewPager();
        fillTabs();
    }

    public ViewPager getViewPager(){
        return this.viewPager;
    }

    private void fillTabs() {

        tabs.setupWithViewPager(viewPager);

            /*tabs.addTab(tabs.newTab().setText("0"));
            tabs.addTab(tabs.newTab().setText("1"));
            tabs.addTab(tabs.newTab().setText("2"));
            tabs.addTab(tabs.newTab().setText("3"));
            tabs.addTab(tabs.newTab().setText("4"));*/

        tabs.getTabAt(0).setIcon(R.drawable.ic_productos);
        tabs.getTabAt(1).setIcon(R.drawable.ic_financiamiento);
        tabs.getTabAt(2).setIcon(R.drawable.ic_cheques);
        tabs.getTabAt(3).setIcon(R.drawable.ic_credito_directo);
        tabs.getTabAt(4).setIcon(R.drawable.ic_catalogo);

        //change ViewPager page when tab selected
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void fillViewPager() {

        vpAdapter = new MainViewPagerAdapter(getActivity().getSupportFragmentManager());

        if(vpAdapter.getCount() == 0) {

            vpAdapter.addFragments(new InicioFragment(), "Inicio"); //0
            vpAdapter.addFragments(new TarjetaCreditoFinanciamientoFragment(), "Financiamiento");
            vpAdapter.addFragments(new ChequeFragment(), "Cheque");
            vpAdapter.addFragments(new CreditoDirectoFragment(), "Credito Directo");
            vpAdapter.addFragments(new CatalogoFragment(), "Catalogo"); //4
        }

        viewPager.setAdapter(vpAdapter);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        MainViewPagerAdapter vpAdapter = (MainViewPagerAdapter) viewPager.getAdapter();
        String titulo = (String) vpAdapter.getFragmentTitle(position);
        getActivity().setTitle(titulo);
        //Toast.makeText(getActivity().getApplicationContext(), titulo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

}
