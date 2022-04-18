package com.eservia.booking.util;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eservia.booking.R;
import com.eservia.booking.ui.booking.beauty.basket_sort.BasketSortFragment;
import com.eservia.booking.ui.booking.beauty.booking.BookingFragment;
import com.eservia.booking.ui.booking.beauty.service_group.ServiceGroupFragment;
import com.eservia.booking.ui.booking.beauty.thank_you.ThankYouFragment;
import com.eservia.booking.ui.booking.resto.date_time.BookingDateTimeFragment;
import com.eservia.booking.ui.booking.resto.placement.BookingRestoPlacementFragment;
import com.eservia.booking.ui.booking.resto.thank_you.ThankYouRestoFragment;
import com.eservia.booking.ui.delivery.resto.address.DeliveryAddressFragment;
import com.eservia.booking.ui.delivery.resto.address.DeliveryAddressMode;
import com.eservia.booking.ui.delivery.resto.basket.DeliveryBasketFragment;
import com.eservia.booking.ui.delivery.resto.date.DeliveryDateFragment;
import com.eservia.booking.ui.delivery.resto.thank_you.ThankYouRestoDeliveryFragment;
import com.eservia.booking.ui.home.bookings.active_bookings.ActiveBookingsFragment;
import com.eservia.booking.ui.home.bookings.archive_bookings.ArchiveBookingsFragment;
import com.eservia.booking.ui.home.bookings.delivery_info.DeliveryInfoFragment;
import com.eservia.booking.ui.home.favorite.favorite.FavoriteFragment;
import com.eservia.booking.ui.home.menu.menu.MenuFragment;
import com.eservia.booking.ui.home.news.news.NewsFragment;
import com.eservia.booking.ui.home.search.discounts.DiscountsFragment;
import com.eservia.booking.ui.home.search.points.PointsFragment;
import com.eservia.booking.ui.home.search.points_details.PointsDetailsFragment;
import com.eservia.booking.ui.home.search.search.SearchFragment;
import com.eservia.booking.ui.suggest_business.modal.SuggestBusinessModalFragment;
import com.eservia.booking.ui.suggest_business.standart.SuggestBusinessStandartFragment;
import com.eservia.booking.ui.suggest_business.thank_you.SuggestBusinessThankYouFragment;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.entity.RestoDelivery;

public class FragmentUtil {

    public static void popAllBackStack(FragmentManager fragmentManager) {

        int lastEntryIndex = fragmentManager.getBackStackEntryCount() - 1;

        for (int i = lastEntryIndex; i >= 0; i--) {

            fragmentManager.popBackStack();
        }
    }

    public static void applyCommonFragmentAnimations(FragmentTransaction transaction) {
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
    }

    public static void startFragmentTabSelectAnimation(Context context, View container) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (container == null) {
            return;
        }
        Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        container.startAnimation(fadeIn);
    }

    public static void openBookingBeautyThankYouFragment(FragmentManager fragmentManager,
                                                         @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(ThankYouFragment.TAG);
        if (fragment == null) {
            fragment = ThankYouFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, ThankYouFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openSuggestBusinessThankYouFragment(FragmentManager fragmentManager,
                                                           @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(SuggestBusinessThankYouFragment.TAG);
        if (fragment == null) {
            fragment = SuggestBusinessThankYouFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, SuggestBusinessThankYouFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openBookingBeautyBasketSortFragment(FragmentManager fragmentManager,
                                                           @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(BasketSortFragment.TAG);
        if (fragment == null) {
            fragment = BasketSortFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, BasketSortFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openBookingBeautyFragment(FragmentManager fragmentManager,
                                                 @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(BookingFragment.TAG);
        if (fragment == null) {
            fragment = BookingFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, BookingFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openFavoriteFragment(FragmentManager fragmentManager,
                                            @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(FavoriteFragment.TAG);
        if (fragment == null) {
            fragment = FavoriteFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, FavoriteFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openActiveBookingsFragment(FragmentManager fragmentManager,
                                                  @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(ActiveBookingsFragment.TAG);
        if (fragment == null) {
            fragment = ActiveBookingsFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, ActiveBookingsFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showServiceGroupBeautyFragment(FragmentManager fragmentManager,
                                                      @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(ServiceGroupFragment.TAG);
        if (fragment == null) {
            fragment = ServiceGroupFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, ServiceGroupFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showSuggestBusinessStandartFragment(FragmentManager fragmentManager,
                                                           @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(SuggestBusinessStandartFragment.TAG);
        if (fragment == null) {
            fragment = SuggestBusinessStandartFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, SuggestBusinessStandartFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showSuggestBusinessModalFragment(FragmentManager fragmentManager,
                                                        @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(SuggestBusinessModalFragment.TAG);
        if (fragment == null) {
            fragment = SuggestBusinessModalFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, SuggestBusinessModalFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openMenuFragment(FragmentManager fragmentManager,
                                        @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(MenuFragment.TAG);
        if (fragment == null) {
            fragment = MenuFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, MenuFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openNewsFragment(FragmentManager fragmentManager,
                                        @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(NewsFragment.TAG);
        if (fragment == null) {
            fragment = NewsFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, NewsFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openArchiveBookingsFragment(FragmentManager fragmentManager,
                                                   @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(ArchiveBookingsFragment.TAG);
        if (fragment == null) {
            fragment = ArchiveBookingsFragment.newInstance();
            fragmentTransaction.addToBackStack(ArchiveBookingsFragment.TAG);
        }
        fragmentTransaction.replace(containerViewId, fragment, ArchiveBookingsFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showRestoMenuFragment(FragmentManager fragmentManager,
                                             @IdRes int containerViewId,
                                             Long categoryId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = com.eservia.booking.ui.business_page.restaurant.menu.MenuFragment.newInstance(categoryId);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(containerViewId, fragment, null);
        fragmentTransaction.commit();
    }

    public static void showRestoBookingDateTimeFragment(FragmentManager fragmentManager,
                                                        @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(BookingDateTimeFragment.TAG);
        if (fragment == null) {
            fragment = BookingDateTimeFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, BookingDateTimeFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showRestoBookingPlacementFragment(FragmentManager fragmentManager,
                                                         @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(BookingRestoPlacementFragment.TAG);
        if (fragment == null) {
            fragment = BookingRestoPlacementFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, BookingRestoPlacementFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openBookingRestoThankYouFragment(FragmentManager fragmentManager,
                                                        @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(ThankYouRestoFragment.TAG);
        if (fragment == null) {
            fragment = ThankYouRestoFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, ThankYouRestoFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showRestoDeliveryBasketFragment(FragmentManager fragmentManager,
                                                       @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(DeliveryBasketFragment.TAG);
        if (fragment == null) {
            fragment = DeliveryBasketFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, DeliveryBasketFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showRestoDeliveryAddressFragment(FragmentManager fragmentManager,
                                                        @IdRes int containerViewId,
                                                        @DeliveryAddressMode int mode) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(DeliveryAddressFragment.TAG);
        if (fragment == null) {
            fragment = DeliveryAddressFragment.newInstance(mode);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, DeliveryAddressFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showRestoDeliveryDateFragment(FragmentManager fragmentManager,
                                                     @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(DeliveryDateFragment.TAG);
        if (fragment == null) {
            fragment = DeliveryDateFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, DeliveryDateFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void openDeliveryRestoThankYouFragment(FragmentManager fragmentManager,
                                                         @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(ThankYouRestoDeliveryFragment.TAG);
        if (fragment == null) {
            fragment = ThankYouRestoDeliveryFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, ThankYouRestoDeliveryFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showRestoDeliveryInfoFragment(FragmentManager fragmentManager,
                                                     @IdRes int containerViewId,
                                                     RestoDelivery delivery) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(DeliveryInfoFragment.TAG);
        if (fragment == null) {
            fragment = DeliveryInfoFragment.newInstance(delivery);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerViewId, fragment, DeliveryInfoFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showSearchFragment(FragmentManager fragmentManager,
                                          @IdRes int containerViewId,
                                          BusinessSector sector,
                                          boolean startFromSearch) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(SearchFragment.TAG);
        if (fragment == null) {
            fragment = SearchFragment.newInstance(sector, startFromSearch);
            fragmentTransaction.addToBackStack(SearchFragment.TAG);
        }
        fragmentTransaction.replace(containerViewId, fragment, SearchFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showPointsFragment(FragmentManager fragmentManager,
                                          @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(PointsFragment.TAG);
        if (fragment == null) {
            fragment = PointsFragment.newInstance();
            fragmentTransaction.addToBackStack(PointsFragment.TAG);
        }
        fragmentTransaction.replace(containerViewId, fragment, PointsFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showPointsDetailsFragment(FragmentManager fragmentManager,
                                                 @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(PointsDetailsFragment.TAG);
        if (fragment == null) {
            fragment = PointsDetailsFragment.newInstance();
            fragmentTransaction.addToBackStack(PointsDetailsFragment.TAG);
        }
        fragmentTransaction.replace(containerViewId, fragment, PointsDetailsFragment.TAG);
        fragmentTransaction.commit();
    }

    public static void showDiscountsFragment(FragmentManager fragmentManager,
                                             @IdRes int containerViewId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(DiscountsFragment.TAG);
        if (fragment == null) {
            fragment = DiscountsFragment.newInstance();
            fragmentTransaction.addToBackStack(DiscountsFragment.TAG);
        }
        fragmentTransaction.replace(containerViewId, fragment, DiscountsFragment.TAG);
        fragmentTransaction.commit();
    }
}
