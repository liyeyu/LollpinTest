package com.ssc.lollpintest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter{

    private String[] mTitles;
    private List<Fragment> mFragments;

    public MyViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


}
