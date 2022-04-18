package com.eservia.booking.ui.home.search.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.booking.beauty.BookingBeautyActivity;
import com.eservia.booking.ui.business_page.beauty.BusinessPageBeautyActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.home.search.search.sheet.SearchSheetDialog;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.booking.ui.search_business_map.SearchBusinessesMapActivity;
import com.eservia.booking.ui.search_business_map.SearchBusinessesMapExtra;
import com.eservia.booking.ui.suggest_business.SuggestBusinessActivity;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.LocationUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.overscroll.OverScrollDecoratorHelper;
import com.eservia.utils.KeyboardUtil;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import moxy.presenter.InjectPresenter;

public class SearchFragment extends BaseHomeFragment implements
        SearchAdapter.OnBusinessItemClickListener,
        SearchView,
        SearchSheetDialog.OnSheetDialogItemClickListener {

    private static final String KEY_START_FROM_SEARCH = "start_from_search";

    private static final String DIALOG_TYPE_SORT = "sort_type";
    private static final String DIALOG_TYPE_CITY = "search_city";

    public static final String TAG = "search_fragment";

    CoordinatorLayout fragmentContainer;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    SwipeRefreshLayout swipeContainer;
    RecyclerView rvBusinesses;
    RecyclerView rvCategories;
    ViewGroup layout_horizontal_recycler_view;
    RecyclerView rvHorizontalRecyclerView;
    TextView tvHorizontalRecyclerViewTitle;
    EditText etSearch;
    ImageView ivSearchIcon;
    LinearLayout llCommonFakeSpinner;
    TextView tvCommonFakeSpinnerTitle;
    RelativeLayout rlHorizontalLists;
    CardView cvHorizontalLists;
    TextView tvAllBusinessesTitle;
    RelativeLayout rlSearchForToolbar, rlSegmentedButtonLeft,
            rlSegmentedButtonRight;
    ImageView ivSearchCancel;

    @InjectPresenter
    SearchPresenter mPresenter;

    private HomeActivity mActivity;

    private SearchAdapter mAdapter;

    private CategoryAdapter mCategoryAdapter;

    private HorizontalBusinessesListAdapter mPopularBusinessesAdapter;

    private SearchSheetDialog mSheetDialog;

    public static SearchFragment newInstance(BusinessSector sector, boolean startFromSearch) {
        EventBus.getDefault().postSticky(sector);
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_START_FROM_SEARCH, startFromSearch);
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mActivity = (HomeActivity) getActivity();
        WindowUtils.setNormalStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        app_bar_layout = view.findViewById(R.id.app_bar_layout);
        toolbar = view.findViewById(R.id.toolbar);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        rvBusinesses = view.findViewById(R.id.rvBusinesses);
        rvCategories = view.findViewById(R.id.rvCategories);
        layout_horizontal_recycler_view = view.findViewById(R.id.layout_horizontal_recycler_view);
        rvHorizontalRecyclerView = view.findViewById(R.id.rvHorizontalRecyclerView);
        tvHorizontalRecyclerViewTitle = view.findViewById(R.id.tvHorizontalRecyclerViewTitle);
        etSearch = view.findViewById(R.id.etSearch);
        ivSearchIcon = view.findViewById(R.id.ivSearchIcon);
        llCommonFakeSpinner = view.findViewById(R.id.llCommonFakeSpinner);
        tvCommonFakeSpinnerTitle = view.findViewById(R.id.tvCommonFakeSpinnerTitle);
        rlHorizontalLists = view.findViewById(R.id.rlHorizontalLists);
        cvHorizontalLists = view.findViewById(R.id.cvHorizontalLists);
        tvAllBusinessesTitle = view.findViewById(R.id.tvAllBusinessesTitle);
        rlSearchForToolbar = view.findViewById(R.id.rlSearchForToolbar);
        rlSegmentedButtonLeft = view.findViewById(R.id.rlSegmentedButtonLeft);
        rlSegmentedButtonLeft.setOnClickListener(v -> onMapClick());
        rlSegmentedButtonRight = view.findViewById(R.id.rlSegmentedButtonRight);
        rlSegmentedButtonRight.setOnClickListener(v -> onFilterClick());
        llCommonFakeSpinner.setOnClickListener(v -> onCityClick());
        ivSearchCancel = view.findViewById(R.id.ivSearchCancel);
        ivSearchCancel.setOnClickListener(v -> onCancelSearchClick());
        initViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (getParentFragment() != null) {
                    goBack();
                }
                return true;
            }
            case R.id.item_search: {
                showSearch();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void refresh() {
        ViewUtil.scrollTop(app_bar_layout, rvBusinesses);
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setNormalStatusBar(mActivity);
        FragmentUtil.startFragmentTabSelectAnimation(getActivity(), fragmentContainer);
    }

    @Override
    public void willBeHidden() {
    }

    public void onMapClick() {
        mPresenter.onShowNearestMapClicked();
    }

    public void onFilterClick() {
        mPresenter.onShowSortDialog();
    }

    public void onCityClick() {
        mPresenter.onShowCitySelection();
    }

    public void onCancelSearchClick() {
        hideSearch();
    }

    @Override
    public void onBusinessReserveClick(BusinessAdapterItem item, int position) {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mPresenter.onReserveClick(item.getBusiness());
    }

    @Override
    public void onBusinessInfoClick(BusinessAdapterItem item, int position) {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mPresenter.onSelectBusiness(item.getBusiness());
    }

    @Override
    public void showBusinessBeautyActivity(Business business) {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        openBeautyBusinessPageActivity(business);
    }

    @Override
    public void onSuggestBusinessClick() {
        mPresenter.onSuggestBusinessClick();
    }

    @Override
    public void showProgress() {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void requiredArgs() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPresenter.onArgs(bundle.getBoolean(KEY_START_FROM_SEARCH, false));
        } else {
            mPresenter.onArgs(false);
        }
    }

    @Override
    public void requestFocus() {
        etSearch.requestFocus();
    }

    @Override
    public void onBusinessesLoadingSuccess(List<Business> businessList, BusinessSector sector) {
        mAdapter.replaceAll(mapToSearchAdapterItems(businessList, sector));
        revalidateBusinessesList();
    }

    @Override
    public void onBusinessesLoadingFailed(Throwable throwable) {
        if (businessesListIsFullyEmpty()) {
            mAdapter.addItem(new NothingFoundAdapterItem());
        }
        revalidateBusinessesList();
    }

    @Override
    public void onPopularBusinessesLoadingSuccess(List<Business> businessList) {
        mPopularBusinessesAdapter.replaceAll(businessList);
        revalidatePopularBusinessesList();
    }

    @Override
    public void onPopularBusinessesLoadingFailed(Throwable throwable) {
        revalidatePopularBusinessesList();
    }

    @Override
    public void showSelectedCity(@Nullable String city) {
        if (city != null) {
            llCommonFakeSpinner.setVisibility(View.VISIBLE);
            tvCommonFakeSpinnerTitle.setText(city);
        } else {
            llCommonFakeSpinner.setVisibility(View.INVISIBLE);
            tvCommonFakeSpinnerTitle.setText("");
        }
    }

    @Override
    public void onBusinessesPaginationStarted() {
        if (!businessesListIsFullyEmpty()) {
            mAdapter.addItem(new LoadMoreProgressAdapterItem());
        }
    }

    @Override
    public void onBusinessesPaginationFinished() {
        if (businessesListLastItemIsProgress()) {
            mAdapter.remove(mAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void onCategoriesLoadingSuccess(List<CategoryAdapterItem> categories) {
        mCategoryAdapter.replaceAll(categories);
    }

    @Override
    public void onCategoriesLoadingFailed(Throwable throwable) {
    }

    @Override
    public void showCategoriesProgress() {
    }

    @Override
    public void hideCategoriesProgress() {
    }

    @Override
    public void setSelectedCategory(boolean selected, Integer categoryId) {
        mCategoryAdapter.setSelected(selected, categoryId);
    }

    @Override
    public void showSortDialog(List<SheetAdapterItem> items) {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        openSortSheetDialog(items);
    }

    @Override
    public void showCitiesDialog(List<SheetAdapterItem> items) {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        openCitiesSheetDialog(items);
    }

    @Override
    public void onDialogItemClick(SheetAdapterItem item, String dialogType) {
        if (dialogType.equals(DIALOG_TYPE_SORT)) {
            mPresenter.onSortTypeSelected(item);
        } else if (dialogType.equals(DIALOG_TYPE_CITY)) {
            mPresenter.onCitySelected(item);
        }
    }

    @Override
    public void hideSheetDialog() {
        if (mSheetDialog != null) {
            mSheetDialog.dismiss();
        }
    }

    @Override
    public void requestFineLocationPermission() {
        LocationUtil.requestFineLocationPermission(mActivity, HomeActivity.CODE_REQUEST_FINE_LOCATION);
    }

    @Override
    public void showBookingBeautyActivity(Business business) {
        openBookingBeautyActivity(business);
    }

    @Override
    public void showSuggestBusinessActivity() {
        SuggestBusinessActivity.start(mActivity, false);
    }

    @Override
    public void openSearchBusinessesMap(List<Business> businesses, BusinessSector sector) {
        SearchBusinessesMapActivity.start(mActivity, new SearchBusinessesMapExtra(businesses, sector));
    }

    @Override
    public void goBack() {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mActivity.onBackPressed();
    }

    private void revalidatePopularBusinessesList() {
        TransitionManager.beginDelayedTransition(rlHorizontalLists, new Slide(Gravity.TOP));
        TransitionManager.beginDelayedTransition(layout_horizontal_recycler_view, new Slide(Gravity.TOP));
        if (mPopularBusinessesAdapter.getItemCount() > 0) {
            tvHorizontalRecyclerViewTitle.setVisibility(View.VISIBLE);
            rvHorizontalRecyclerView.setVisibility(View.VISIBLE);
            rlHorizontalLists.setVisibility(View.VISIBLE);
        } else {
            tvHorizontalRecyclerViewTitle.setVisibility(View.GONE);
            rvHorizontalRecyclerView.setVisibility(View.GONE);
            rlHorizontalLists.setVisibility(View.GONE);
        }
    }

    private void revalidateBusinessesList() {
        if (businessesListContainsOnlyNothingFoundItem()) {
            tvAllBusinessesTitle.setVisibility(View.INVISIBLE);
        } else {
            tvAllBusinessesTitle.setVisibility(View.VISIBLE);
        }
    }

    private List<SearchListItem> mapToSearchAdapterItems(List<Business> businesses,
                                                         BusinessSector sector) {

        List<SearchListItem> result = new ArrayList<>();

        if (businesses.isEmpty()) {
            result.add(new NothingFoundAdapterItem());
        } else {
            for (Business b : businesses) {
                result.add(new BusinessAdapterItem(b, sector));
            }
        }
        return result;
    }

    private void search(String query) {
        mPresenter.onQuery(query);
    }

    private boolean businessesListIsFullyEmpty() {
        return mAdapter.getItemCount() == 0;
    }

    private boolean businessesListContainsOnlyNothingFoundItem() {
        return mAdapter.getItemCount() == 1
                && mAdapter.getItem(0) instanceof NothingFoundAdapterItem;
    }

    private boolean businessesListLastItemIsProgress() {
        if (mAdapter.getItemCount() == 0) {
            return false;
        }
        int lastItemPosition = mAdapter.getItemCount() - 1;
        return mAdapter.getItem(lastItemPosition) instanceof LoadMoreProgressAdapterItem;
    }

    private void showSearch() {
        if (rlSearchForToolbar.getVisibility() != View.VISIBLE) {
            rlSearchForToolbar.setVisibility(View.VISIBLE);
            etSearch.requestFocus();
            KeyboardUtil.showSoftKeyboard(mActivity);
        }
    }

    private void hideSearch() {
        rlSearchForToolbar.setVisibility(View.GONE);
        etSearch.setText("");
        KeyboardUtil.hideSoftKeyboard(mActivity);
    }

    private void initViews() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);
        initSwipeRefresh();
        initList();
        initCategoriesList();
        initSearch();
        initMostPopularList();
        initOutlineProviders();
    }

    private void initOutlineProviders() {
        ViewUtil.setCardOutlineProviderStraightCorners(mActivity, rlHorizontalLists, cvHorizontalLists);
    }

    private void initSwipeRefresh() {
        swipeContainer.setOnRefreshListener(mPresenter::onRefresh);
        swipeContainer.setColorSchemeColors(ColorUtil.swipeRefreshColors(mActivity));
    }

    private void initList() {
        mAdapter = new SearchAdapter(mActivity, this, mPresenter);
        rvBusinesses.setAdapter(mAdapter);
        rvBusinesses.setHasFixedSize(true);
        rvBusinesses.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvBusinesses.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void initCategoriesList() {
        mCategoryAdapter = new CategoryAdapter(mActivity, mPresenter, null);
        rvCategories.setAdapter(mCategoryAdapter);
        rvCategories.setHasFixedSize(true);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(rvCategories.getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        rvCategories.setLayoutManager(layoutManager);
    }

    private void initMostPopularList() {
        mPopularBusinessesAdapter = new HorizontalBusinessesListAdapter(mActivity, mPresenter, mPresenter);
        rvHorizontalRecyclerView.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(rvHorizontalRecyclerView,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rvHorizontalRecyclerView.setAdapter(mPopularBusinessesAdapter);
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(etSearch.getText().toString());
                KeyboardUtil.hideSoftKeyboard(mActivity);
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

    private void openBeautyBusinessPageActivity(Business business) {
        BusinessPageBeautyActivity.start(mActivity, business);
    }

    private void openBookingBeautyActivity(Business business) {
        BookingBeautyActivity.start(mActivity, business, null, null);
    }

    private void openSortSheetDialog(List<SheetAdapterItem> items) {
        mSheetDialog = SearchSheetDialog.newInstance(DIALOG_TYPE_SORT);
        mSheetDialog.setSortClickListener(this);
        mSheetDialog.setAdapterItems(items);
        mSheetDialog.setTitle(mActivity.getResources().getString(R.string.sort_type));
        mSheetDialog.show(mActivity.getSupportFragmentManager(),
                SearchSheetDialog.class.getSimpleName());
    }

    private void openCitiesSheetDialog(List<SheetAdapterItem> items) {
        mSheetDialog = SearchSheetDialog.newInstance(DIALOG_TYPE_CITY);
        mSheetDialog.setSortClickListener(this);
        mSheetDialog.setAdapterItems(items);
        mSheetDialog.setTitle(mActivity.getResources().getString(R.string.choose_city));
        mSheetDialog.show(mActivity.getSupportFragmentManager(),
                SearchSheetDialog.class.getSimpleName());
    }
}
