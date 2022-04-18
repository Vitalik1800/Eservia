package com.eservia.booking.ui.home.search;

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
import androidx.fragment.app.FragmentTransaction;

import com.eservia.booking.R;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.search.sector.SectorFragment;
import com.eservia.butterknife.ButterKnife;

public class SearchManagerFragment extends BaseHomeFragment implements
        FragmentManager.OnBackStackChangedListener {

    public static final String TAG = "SearchManagerFragment";

    FrameLayout fragSearchContainer;

    public static SearchManagerFragment newInstance() {
        return new SearchManagerFragment();
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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_search_manager, null);
        setUnbinder(ButterKnife.bind(this, view));
        fragSearchContainer = view.findViewById(R.id.fragSearchContainer);
        showSectorFragment();
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
        Fragment frag = fragmentManager.findFragmentById(R.id.fragSearchContainer);
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
        Fragment frag = fragmentManager.findFragmentById(R.id.fragSearchContainer);
        if (frag != null) {
            frag.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showSectorFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //FragmentUtil.applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(SectorFragment.TAG);
        if (fragment == null) {
            fragment = SectorFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.fragSearchContainer, fragment, SectorFragment.TAG);
        fragmentTransaction.commit();
    }
}
