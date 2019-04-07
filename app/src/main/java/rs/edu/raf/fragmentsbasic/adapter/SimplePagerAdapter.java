package rs.edu.raf.fragmentsbasic.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.fragment.FirstFragment;
import rs.edu.raf.fragmentsbasic.fragment.FourthFragment;
import rs.edu.raf.fragmentsbasic.fragment.SecondFragment;
import rs.edu.raf.fragmentsbasic.fragment.ThirdFragment;

public class SimplePagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 4;

    private List<String> mTitles;

    public SimplePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        initTitles(context);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return FirstFragment.newInstance();
            case 1:
                return SecondFragment.newInstance();
            case 2:
                return ThirdFragment.newInstance();
            case 3:
                return FourthFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    private void initTitles(Context context) {
        mTitles = new ArrayList<>();
        mTitles.add(context.getString(R.string.fragment_title_1));
        mTitles.add(context.getString(R.string.fragment_title_2));
        mTitles.add(context.getString(R.string.fragment_title_3));
        mTitles.add(context.getString(R.string.fragment_title_4));
    }
}
