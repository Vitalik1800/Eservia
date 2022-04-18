package com.eservia.booking.ui.home.favorite.favorite.favorites_sector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eservia.booking.R
import com.eservia.booking.common.view.CommonPlaceHolder
import com.eservia.booking.common.view.SpeedyLinearLayoutManager
import com.eservia.booking.ui.booking.beauty.BookingBeautyActivity
import com.eservia.booking.ui.business_page.beauty.BusinessPageBeautyActivity
import com.eservia.booking.ui.home.BaseHomeFragment
import com.eservia.booking.ui.home.HomeActivity
import com.eservia.booking.ui.home.favorite.favorite.common.FavoriteAdapter
import com.eservia.booking.ui.home.favorite.favorite.common.FavoriteRecommendedAdapter
import com.eservia.booking.ui.suggest_business.SuggestBusinessActivity
import com.eservia.booking.util.ColorUtil
import com.eservia.booking.util.MessageUtil
import com.eservia.booking.util.ViewUtil
import com.eservia.model.entity.Business
import kotlinx.android.synthetic.main.fragment_favorite_beauty.*
import kotlinx.android.synthetic.main.layout_favorite_not_found_recommended.*
import moxy.presenter.InjectPresenter

class FavoritesSectorFragment : BaseHomeFragment(), FavoritesSectorView {

    @InjectPresenter
    lateinit var mPresenter: FavoritesSectorPresenter

    lateinit var mActivity: HomeActivity
    private lateinit var mFavoriteAdapter: FavoriteAdapter
    private lateinit var mRecommendedAdapter: FavoriteRecommendedAdapter

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesSectorFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_beauty, container, false)
        mActivity = activity as HomeActivity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun refresh() {
        ViewUtil.scrollTop(nsvContentHolder)
    }

    override fun willBeDisplayed() {}

    override fun willBeHidden() {}

    override fun showProgress() {
        swipeContainer.isRefreshing = true
    }

    override fun hideProgress() {
        swipeContainer.isRefreshing = false
    }

    override fun onFavoritesLoadingSuccess(businessList: List<Business>) {
        mFavoriteAdapter.replaceAll(businessList)
        revalidatePlaceHolder()
    }

    override fun onFavoritesLoadingFailed(throwable: Throwable) {
        revalidatePlaceHolder()
    }

    override fun showBusinessActivity(business: Business) {
        openBusinessPageActivity(business)
    }

    override fun showBookingActivity(business: Business) {
        openBookingActivity(business)
    }

    override fun showSuggestBusinessActivity() {
        SuggestBusinessActivity.start(mActivity, false)
    }

    override fun onRecommendedLoadingSuccess(businessList: List<Business>) {
        if (businessList.isEmpty()) {
            rlPlaceHolderRecommended.visibility = View.GONE
            revalidatePlaceHolder()
        } else {
            rlPlaceHolderRecommended.visibility = View.VISIBLE
            phlPlaceholder.state = CommonPlaceHolder.STATE_HIDE
        }
        mRecommendedAdapter.replaceAll(businessList)
    }

    override fun onRecommendedLoadingFailed(throwable: Throwable) {
        revalidatePlaceHolder()
    }

    override fun onAddBusinessToFavoriteSuccess() {
        MessageUtil.showToast(mActivity, R.string.added_to_favorite)
    }

    override fun onAddBusinessToFavoriteFailed(throwable: Throwable) {
        MessageUtil.showSnackbar(swipeContainer, throwable)
    }

    override fun onDeleteFavoriteSuccess() {
        MessageUtil.showToast(mActivity, R.string.deleted_from_favorite)
    }

    override fun onDeleteFavoriteBusinessFailed(throwable: Throwable) {
        MessageUtil.showSnackbar(swipeContainer, throwable)
    }

    private fun openBusinessPageActivity(business: Business) {
        BusinessPageBeautyActivity.start(mActivity, business)
    }

    private fun openBookingActivity(business: Business) {
        BookingBeautyActivity.start(mActivity, business, null, null)
    }

    private fun revalidatePlaceHolder() {
        val isRecommendedEmpty = mRecommendedAdapter.itemCount == 0
        val isEmpty = mFavoriteAdapter.itemCount == 0
        phlPlaceholder.state =
                if (isEmpty && isRecommendedEmpty)
                    CommonPlaceHolder.STATE_EMPTY
                else
                    CommonPlaceHolder.STATE_HIDE
    }

    private fun initRecommendedList() {
        mRecommendedAdapter = FavoriteRecommendedAdapter(mActivity, mPresenter)
        rvRecommended.setHasFixedSize(true)
        rvRecommended.layoutManager = SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false)
        rvRecommended.adapter = mRecommendedAdapter
    }

    fun initViews() {
        initSwipeRefresh()
        initList()
        initRecommendedList()
        phlPlaceholder.setButtonClickListener { mPresenter.onSuggestBusinessClick() }
    }

    fun initSwipeRefresh() {
        swipeContainer.setOnRefreshListener { mPresenter.refreshFavorites() }
        swipeContainer.setColorSchemeColors(*ColorUtil.swipeRefreshColors(mActivity))
    }

    fun initList() {
        mFavoriteAdapter = FavoriteAdapter(mActivity, mPresenter, mPresenter)
        rvFavorites.setHasFixedSize(true)
        rvFavorites.layoutManager = SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false)
        rvFavorites.adapter = mFavoriteAdapter
    }
}
