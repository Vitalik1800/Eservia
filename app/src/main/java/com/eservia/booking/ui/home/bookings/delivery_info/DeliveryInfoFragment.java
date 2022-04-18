package com.eservia.booking.ui.home.bookings.delivery_info;

import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.util.DishUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.BindView;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.OrderRestoItem;
import com.eservia.model.entity.RestoDelivery;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class DeliveryInfoFragment extends BaseHomeFragment implements DeliveryInfoView {

    public static final String TAG = "resto_delivery_info_fragment";

    @BindView(R.id.fragment_container)
    CoordinatorLayout fragmentContainer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rlDeliveryBasketOrder)
    RelativeLayout rlDeliveryBasketOrder;

    @BindView(R.id.cvDeliveryBasketOrder)
    CardView cvDeliveryBasketOrder;

    @BindView(R.id.rlCardHolderOrderInfo)
    RelativeLayout rlCardHolderOrderInfo;

    @BindView(R.id.cvOrderInfoContainer)
    CardView cvOrderInfoContainer;

    @BindView(R.id.tvOrderInfo)
    TextView tvOrderInfo;

    @BindView(R.id.rvDeliveryOrders)
    RecyclerView rvDeliveryOrders;

    @BindView(R.id.phlPlaceholder)
    CommonPlaceHolder phlPlaceholder;

    @BindView(R.id.llTotalPricesList)
    LinearLayout llTotalPricesList;

    @InjectPresenter
    DeliveryInfoPresenter mPresenter;

    private DeliveryOrderAdapter mOrdersAdapter;

    private HomeActivity mActivity;

    public static DeliveryInfoFragment newInstance(RestoDelivery delivery) {
        EventBus.getDefault().postSticky(new DeliveryInfoExtra(delivery));
        return new DeliveryInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_info, container, false);
        mActivity = (HomeActivity) getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        initViews();
        return view;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setLightStatusBar(mActivity);
        FragmentUtil.startFragmentTabSelectAnimation(getActivity(), fragmentContainer);
    }

    @Override
    public void willBeHidden() {
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onOrderItemsLoaded(List<OrderRestoItem> adapterItems) {
        TransitionManager.beginDelayedTransition(rvDeliveryOrders, new Slide(Gravity.LEFT));
        mOrdersAdapter.replaceAll(adapterItems);
        revalidatePlaceHolder();
        revalidateTotalPrice();
    }

    @Override
    public void onOrderItemsLoadingFailed(Throwable error) {
        revalidatePlaceHolder();
    }

    @Override
    public void bindOrderInformation(String clientName, String phone, String location,
                                     @Nullable String comment) {
        tvOrderInfo.setText(formatOrderInfo(clientName, phone, location, comment));
    }

    private String formatOrderInfo(String clientName, String phone, String location,
                                   @Nullable String comment) {
        String namePhoneFormat = "%s %s";
        String locationFormat = "\n\n%s";
        String commentFormat = !StringUtil.isEmpty(comment)
                ? ("\n\n" + mActivity.getResources().getString(R.string.your_comment) + "\n%s")
                : "";
        return String.format(namePhoneFormat, clientName, phone)
                + String.format(locationFormat, location)
                + String.format(commentFormat, comment);
    }

    private void revalidatePlaceHolder() {
        boolean ordersEmpty = mOrdersAdapter.getItemCount() == 0;
        if (ordersEmpty) {
            rlDeliveryBasketOrder.setVisibility(View.GONE);
            rlCardHolderOrderInfo.setVisibility(View.GONE);
        } else {
            rlDeliveryBasketOrder.setVisibility(View.VISIBLE);
            rlCardHolderOrderInfo.setVisibility(View.VISIBLE);
        }
        phlPlaceholder.setState(ordersEmpty ? CommonPlaceHolder.STATE_EMPTY
                : CommonPlaceHolder.STATE_HIDE);
    }

    public void revalidateTotalPrice() {
        revalidateTotalLayout(mOrdersAdapter.getAdapterItems());
    }

    private void revalidateTotalLayout(List<OrderRestoItem> orderItems) {
        llTotalPricesList.removeAllViews();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_currency_price, null);
        TextView tvPrice = view.findViewById(R.id.tvFilledPreparationsTotalCurrency);
        tvPrice.setText(getFormattedTotalPrice(orderItems));
        llTotalPricesList.addView(view);
    }

    private String getFormattedTotalPrice(List<OrderRestoItem> orderItems) {
        double totalPrice = 0.0;
        for (OrderRestoItem item : orderItems) {
            totalPrice += item.getPrice() * item.getAmount();
        }
        return DishUtil.INSTANCE.getFormattedPrice(mActivity, totalPrice);
    }

    private void initViews() {
        initToolbar();
        setOutlineProviders();
        initList();
    }

    private void initList() {
        mOrdersAdapter = new DeliveryOrderAdapter(mActivity);
        rvDeliveryOrders.setAdapter(mOrdersAdapter);
        rvDeliveryOrders.setHasFixedSize(true);
        rvDeliveryOrders.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(view -> {
            if (getParentFragment() != null) {
                KeyboardUtil.hideSoftKeyboard(mActivity);
                mActivity.onBackPressed();
            }
        });
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(mActivity, rlDeliveryBasketOrder, cvDeliveryBasketOrder);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderOrderInfo, cvOrderInfoContainer);
    }
}
