package com.eservia.booking.ui.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class HomeAdapter extends FragmentPagerAdapter {

    private final ArrayList<BaseHomeFragment> fragments = new ArrayList<>();
    private BaseHomeFragment currentFragment;

    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public BaseHomeFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((BaseHomeFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public void addFragment(BaseHomeFragment fragment) {
        fragments.add(fragment);
    }

    public BaseHomeFragment getCurrentFragment() {
        return currentFragment;
    }
}
