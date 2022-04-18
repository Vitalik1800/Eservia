package com.eservia.booking.common.view;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.eservia.butterknife.Unbinder;


public class BaseDialogFragment extends AppCompatDialogFragment {

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
