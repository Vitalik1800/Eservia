package com.eservia.booking.ui.delivery.resto.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.utils.KeyboardUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import moxy.presenter.InjectPresenter;

public class DeliveryAddressFragment extends BaseHomeFragment implements DeliveryAddressView {

    public static final String TAG = "resto_delivery_address_fragment";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    ProgressBar pbProgress;
    CommonPlaceHolder phlPlaceholder;
    RelativeLayout rlPage;
    TextView tvToolbarSubTitle;
    TextView tvToolbarTitle;
    RecyclerView rvAddresses;
    EditText etSearch;
    ImageView ivSearchIcon;
    LinearLayout llContentPage;

    @InjectPresenter
    DeliveryAddressPresenter mPresenter;

    private BaseActivity mActivity;

    private DeliveryAddressAdapter mAddressAdapter;

    public static DeliveryAddressFragment newInstance(@DeliveryAddressMode int mode) {
        EventBus.getDefault().postSticky(new DeliveryAddressRestoExtra(mode));
        return new DeliveryAddressFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        pbProgress = view.findViewById(R.id.pbProgress);
        phlPlaceholder = view.findViewById(R.id.phlPlaceholder);
        rlPage = view.findViewById(R.id.rlPage);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        tvToolbarTitle = view.findViewById(R.id.tvToolbarTitle);
        rvAddresses = view.findViewById(R.id.rvAddresses);
        etSearch = view.findViewById(R.id.etSearch);
        ivSearchIcon = view.findViewById(R.id.ivSearchIcon);
        llContentPage = view.findViewById(R.id.llContentPage);
        toolbar.setOnClickListener(v -> onToolbarClick());
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

    public void onToolbarClick() {
        scrollTop();
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
        rlPage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        rlPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPageLoadingError() {
        llContentPage.setVisibility(View.INVISIBLE);
        phlPlaceholder.setState(CommonPlaceHolder.STATE_EMPTY);
    }

    @Override
    public void setItems(List<DeliveryAddressAdapterItem> items) {
        mAddressAdapter.replaceAll(items);
        revalidatePlaceHolder();
    }

    @Override
    public void closePage() {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mActivity.onBackPressed();
    }

    @Override
    public void setCityTitles() {
        tvToolbarTitle.setText(mActivity.getResources().getString(R.string.specify_the_city));
        etSearch.setHint(mActivity.getResources().getString(R.string.city_town));
    }

    @Override
    public void setStreetTitles() {
        tvToolbarTitle.setText(mActivity.getResources().getString(R.string.specify_the_street));
        etSearch.setHint(mActivity.getResources().getString(R.string.street));
    }

    private void revalidatePlaceHolder() {
        phlPlaceholder.setState(mAddressAdapter.getItemCount() > 0 ?
                CommonPlaceHolder.STATE_HIDE : CommonPlaceHolder.STATE_EMPTY);
    }

    private void scrollTop() {
        if (rvAddresses != null) {
            rvAddresses.post(() -> rvAddresses.smoothScrollToPosition(0));
        }
    }

    private void initViews() {
        initToolbar();
        initSpinner();
        initList();
        initSearch();
    }

    private void initToolbar() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
    }

    private void search(String query) {
        mPresenter.onSearchQuery(query);
    }

    private void initList() {
        mAddressAdapter = new DeliveryAddressAdapter(mActivity, mPresenter);
        rvAddresses.setHasFixedSize(true);
        rvAddresses.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvAddresses.setAdapter(mAddressAdapter);
        rvAddresses.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    KeyboardUtil.hideSoftKeyboard(mActivity);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void initSpinner() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(etSearch.getText().toString());
                return true;
            }
            return false;
        });
        ivSearchIcon.setOnClickListener(view -> search(etSearch.getText().toString()));
        etSearch.addTextChangedListener(new SimpleTextWatcher() {
            private Timer timer = new Timer();
            private int changeCount = 0;

            @Override
            public void textChanged(String s) {
                if (changeCount < 1) {
                    changeCount++;
                    return;
                }
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(() -> search(s));
                    }
                }, 500);
            }
        });
        etSearch.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etSearch, new View[]{etSearch}));
    }
}
