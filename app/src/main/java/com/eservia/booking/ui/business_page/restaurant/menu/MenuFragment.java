package com.eservia.booking.ui.business_page.restaurant.menu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.BaseMenuItem;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.CategoryItem;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.DishItem;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.ColorUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.OrderRestoNomenclature;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class MenuFragment extends BaseHomeFragment implements MenuView,
        MenuAdapter.OnMenuClickListener {

    private static final String KEY_CATEGORY_ID = "categoryId";

    SwipeRefreshLayout swipeContainer;
    RecyclerView rvMenu;
    CommonPlaceHolder mPlaceHolder;

    @InjectPresenter
    MenuPresenter mPresenter;

    private Activity mActivity;

    private MenuAdapter mAdapter;

    private RestoMenuFragmentListener mListener;

    public static MenuFragment newInstance(Long categoryId) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        if (categoryId != null) {
            args.putLong(KEY_CATEGORY_ID, categoryId);
        } else {
            args.putLong(KEY_CATEGORY_ID, 0);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_page_restaurant_menu,
                container, false);
        mActivity = getActivity();
        if (mActivity instanceof RestoMenuFragmentListener) {
            mListener = (RestoMenuFragmentListener) mActivity;
        } else {
            throw new IllegalArgumentException(
                    "Activity must implement RestoMenuFragmentListener interface");
        }
        setUnbinder(ButterKnife.bind(this, view));
        swipeContainer = view.findViewById(R.id.swipeContainer);
        rvMenu = view.findViewById(R.id.rvMenu);
        mPlaceHolder = view.findViewById(R.id.phlPlaceholder);
        initViews();
        return view;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
    }

    @Override
    public void willBeHidden() {
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
    public void onMenuLoaded(Pair<List<OrderRestoCategory>, List<OrderRestoNomenclature>> menu) {
        mAdapter.replaceAll(convertMenuToItems(menu));
        revalidatePlaceHolder();
    }

    @Override
    public void onMenuLoadingFailed(Throwable throwable) {
        revalidatePlaceHolder();
    }

    @Override
    public void initCategoryId() {
        if (getArguments() != null) {
            long categoryId = getArguments().getLong(KEY_CATEGORY_ID, 0L);
            mPresenter.onCategoryId(categoryId != 0L ? categoryId : null);
        } else {
            mPresenter.onCategoryId(null);
        }
    }

    @NonNull
    private List<BaseMenuItem> convertMenuToItems(Pair<List<OrderRestoCategory>,
            List<OrderRestoNomenclature>> menuItems) {

        List<BaseMenuItem> baseItems = new ArrayList<>();

        for (OrderRestoCategory category : menuItems.first) {
            baseItems.add(new CategoryItem(category));
        }
        for (OrderRestoNomenclature dish : menuItems.second) {
            baseItems.add(new DishItem(dish));
        }

        return baseItems;
    }

    @Override
    public void onCategoryClicked(CategoryItem categoryItem) {
        mListener.onCategoryClicked(categoryItem.getCategory());
    }

    @Override
    public void onDishClicked(DishItem dishItem) {
        mListener.onDishClicked(dishItem.getNomenclature());
    }

    private void revalidatePlaceHolder() {
        boolean isEmpty = mAdapter.getItemCount() == 0;
        mPlaceHolder.setState(isEmpty ? CommonPlaceHolder.STATE_EMPTY : CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        initSwipeRefresh();
        initList();
    }

    private void initSwipeRefresh() {
        swipeContainer.setOnRefreshListener(mPresenter::refreshMenu);
        swipeContainer.setColorSchemeColors(ColorUtil.swipeRefreshColors(mActivity));
    }

    private void initList() {
        mAdapter = new MenuAdapter(mActivity, this);
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvMenu.setAdapter(mAdapter);
    }
}
