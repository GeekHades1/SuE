package org.hades.sue.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.hades.sue.bean.BodyCheck;
import org.hades.sue.fragment.ExBodyCheckFragment;

import java.util.List;

/**
 * Created by Hades on 2017/11/11.
 */

public class ExBodyCheckAdapter extends FragmentPagerAdapter {

    private List<List<BodyCheck>> data;

    public ExBodyCheckAdapter(FragmentManager fm,List<List<BodyCheck>> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        ExBodyCheckFragment fragment = new ExBodyCheckFragment();
        fragment.setData(data.get(position));
        fragment.setId(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).get(0).body;
    }
}
