package com.hpm.sp.streaminfoportal.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.hpm.sp.streaminfoportal.FullWidthFragment;

import java.util.List;

/**
 * Created by kumardivyarajat on 09/07/16.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<String> imageList;

    public HomePagerAdapter(FragmentManager fm, List<String> imageList) {
        super(fm);
        this.imageList = imageList;
    }

    @Override
    public Fragment getItem(int pos) {
        return FullWidthFragment.newInstance(imageList.get(pos));
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }


    @Override
    public int getCount() {
        return imageList.size();
    }
}