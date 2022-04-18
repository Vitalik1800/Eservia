package com.eservia.booking.ui.home;

import com.eservia.booking.common.view.BaseFragment;

public abstract class BaseHomeFragment extends BaseFragment {

    /**
     * Refresh
     */
    public abstract void refresh();

    /**
     * Called when a fragment will be displayed
     */
    public abstract void willBeDisplayed();

    /**
     * Called when a fragment will be hidden
     */
    public abstract void willBeHidden();
}
