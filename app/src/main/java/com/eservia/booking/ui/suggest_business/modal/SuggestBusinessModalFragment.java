package com.eservia.booking.ui.suggest_business.modal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.utils.KeyboardUtil;

import moxy.presenter.InjectPresenter;

public class SuggestBusinessModalFragment extends BaseHomeFragment implements SuggestBusinessModalView {

    public static final String TAG = "SuggestBusinessModalFragment";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    ProgressBar pbProgress;
    TextView tvToolbarSubTitle;
    RelativeLayout rlCardHolderSuggestBusiness;
    CardView cvContainerSuggestBusiness;
    EditText etName;
    EditText etCity;
    EditText etAddress;
    Button btnAdd;
    TextView tvLater;

    @InjectPresenter
    SuggestBusinessModalPresenter mPresenter;

    private BaseActivity mActivity;

    public static SuggestBusinessModalFragment newInstance() {
        SuggestBusinessModalFragment fragment = new SuggestBusinessModalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggest_business_modal, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        pbProgress = view.findViewById(R.id.pbProgress);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        rlCardHolderSuggestBusiness = view.findViewById(R.id.rlCardHolderSuggestBusiness);
        cvContainerSuggestBusiness = view.findViewById(R.id.cvContainerSuggestBusiness);
        etName = view.findViewById(R.id.etName);
        etCity = view.findViewById(R.id.etCity);
        etAddress = view.findViewById(R.id.etAddress);
        btnAdd = view.findViewById(R.id.btnAdd);
        tvLater = view.findViewById(R.id.tvLater);
        btnAdd.setOnClickListener(v -> onAcceptClick());
        tvLater.setOnClickListener(v -> onLaterClick());
        mPresenter = new SuggestBusinessModalPresenter();
        initViews();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            KeyboardUtil.hideSoftKeyboard(mActivity);
            mActivity.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setLightStatusBar(mActivity);
    }

    @Override
    public void willBeHidden() {
    }

    public void onAcceptClick() {
        trySend();
    }
    public void onLaterClick() {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mPresenter.onLaterClick();
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onSendSuggestionSuccess() {
    }

    @Override
    public void onSendSuggestionFailed(Throwable throwable) {
        MessageUtil.showSnackbar(fragmentContainer, throwable);
    }

    @Override
    public void finish() {
        mActivity.onBackPressed();
    }

    @Override
    public void showSuccess() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.popAllBackStack(fragmentManager);
        openThankYouFragment();
    }

    private void trySend() {
        KeyboardUtil.hideSoftKeyboard(mActivity);

        String name = etName.getText().toString();
        String city = etCity.getText().toString();
        String address = etAddress.getText().toString();

        etName.setError(ValidatorUtil.isTextValid(mActivity, name));
        etCity.setError(ValidatorUtil.isTextValid(mActivity, city));
        etAddress.setError(ValidatorUtil.isTextValid(mActivity, address));

        if (!hasErrors()) {
            mPresenter.onAcceptClick(name, city, address);
        }
    }

    private boolean hasErrors() {
        boolean error = false;

        if (etAddress.getError() != null) {
            etAddress.requestFocus();
            error = true;
        }

        if (etCity.getError() != null) {
            etCity.requestFocus();
            error = true;
        }

        if (etName.getError() != null) {
            etName.requestFocus();
            error = true;
        }
        return error;
    }

    private void initViews() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);

        initSwipeRefresh();

        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderSuggestBusiness,
                cvContainerSuggestBusiness);

    }

    private void initSwipeRefresh() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }

    private void openThankYouFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.openSuggestBusinessThankYouFragment(fragmentManager, R.id.suggestBusinessContainer);
    }
}
