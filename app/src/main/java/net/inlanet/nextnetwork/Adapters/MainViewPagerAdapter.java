package net.inlanet.nextnetwork.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import net.inlanet.nextnetwork.Interfaces.CartInteractionListener;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter{

    private final static List<Fragment> fragmentList = new ArrayList<>();
    private final static List<String> tabsTitulos = new ArrayList<>();

    public CartInteractionListener mListener;

    public MainViewPagerAdapter(FragmentManager fm/*, CartInteractionListener mListener*/) {
        super(fm);
        //this.mListener = mListener;
    }

    public void addFragments(Fragment fragment, String titulo){
        fragmentList.add(fragment);
        tabsTitulos.add(titulo);
    }

    public CharSequence getFragmentTitle(int position) {
        return tabsTitulos.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
        //return tabsTitulos.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        //if(position == 7){
            //ProductDetailFragment productDetailFragment = new ProductDetailFragment();
            //productDetailFragment.setCurrentProduct(this.mListener.getCurrentProduct());
            //return productDetailFragment;

        //}else {
            return fragmentList.get(position);
        //}
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
