package com.eservia.booking.ui.home.bookings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.eservia.booking.R;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.butterknife.ButterKnife;

public class BookingsManagerFragment extends BaseHomeFragment implements
        FragmentManager.OnBackStackChangedListener {

    public static final String TAG = "BookingsManagerFragment";

    FrameLayout fragBookingsContainer;

    public static BookingsManagerFragment newInstance() {
        return new BookingsManagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().addOnBackStackChangedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_bookings_manager, null);
        setUnbinder(ButterKnife.bind(this, view));
        fragBookingsContainer = view.findViewById(R.id.fragBookingsContainer);
        showActiveBookingsFragment();
        return view;
    }

    @Override
    public void onDestroy() {
        getChildFragmentManager().removeOnBackStackChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void onBackStackChanged() {
    }

    @Override
    public void refresh() {
        FragmentManager fragmentManager = getChildFragmentManager();
        for (Fragment frag : fragmentManager.getFragments()) {
            if (frag != null && frag.isVisible() && frag instanceof BaseHomeFragment) {
                ((BaseHomeFragment) frag).refresh();
            }
        }
    }

    @Override
    public void willBeDisplayed() {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment frag = fragmentManager.findFragmentById(R.id.fragBookingsContainer);
        if (frag instanceof BaseHomeFragment) {
            ((BaseHomeFragment) frag).willBeDisplayed();
        }
    }

    @Override
    public void willBeHidden() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment frag = fragmentManager.findFragmentById(R.id.fragBookingsContainer);
        if (frag != null) {
            frag.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showActiveBookingsFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentUtil.openActiveBookingsFragment(fragmentManager, R.id.fragBookingsContainer);
    }
}
