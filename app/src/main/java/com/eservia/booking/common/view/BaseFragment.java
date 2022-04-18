package com.eservia.booking.common.view;


import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eservia.butterknife.Unbinder;

public class BaseFragment extends Fragment {

    @Nullable
    private Unbinder mUnbinder;

    public void setUnbinder(Unbinder unbinder) {
        mUnbinder = unbinder;
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        super.onDestroyView();
    }
}
