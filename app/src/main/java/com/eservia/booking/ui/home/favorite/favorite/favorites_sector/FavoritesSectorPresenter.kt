package com.eservia.booking.ui.home.favorite.favorite.favorites_sector

import android.content.Context
import com.eservia.booking.App
import com.eservia.booking.common.presenter.BasePresenter
import com.eservia.booking.model.event_bus.BusinessFavoriteAdded
import com.eservia.booking.model.event_bus.BusinessFavoriteRemoved
import com.eservia.booking.ui.home.favorite.favorite.listeners.OnBusinessItemClickListener
import com.eservia.booking.ui.home.favorite.favorite.listeners.OnBusinessPaginationListener
import com.eservia.booking.ui.home.favorite.favorite.listeners.OnRecommendedListener
import com.eservia.booking.util.AnalyticsHelper
import com.eservia.booking.util.BusinessUtil
import com.eservia.booking.util.ObjectTypeUtil
import com.eservia.model.entity.Business
import com.eservia.model.interactors.business.BusinessInteractor
import com.eservia.model.prefs.Profile
import com.eservia.model.remote.rest.RestManager
import com.eservia.utils.SortUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@InjectViewState
class FavoritesSectorPresenter : BasePresenter<FavoritesSectorView>(),
        OnBusinessItemClickListener,
        OnBusinessPaginationListener,
        OnRecommendedListener {

    private val mFavorites = mutableListOf<Business>()
    private val mRecommended = mutableListOf<Business>()

    @Inject
    lateinit var mRestManager: RestManager

    @Inject
    lateinit var mBusinessInteractor: BusinessInteractor

    @Inject
    lateinit var mContext: Context

    private var mFavoritePaginationDisposable: Disposable? = null
    private var mFavoriteDisposable: Disposable? = null

    private var mIsAllFavoritesLoaded = false

    init {
        App.getAppComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        EventBus.getDefault().register(this)
        refreshFavorites()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun loadMoreFavorites() {
        if (!paginationInProgress(mFavoritePaginationDisposable) && !mIsAllFavoritesLoaded) {
            makeFavoritePagination()
        }
    }

    override fun onBusinessReserveClick(item: Business, position: Int) {
        if (item.addresses.isEmpty()) {
            return
        }
        viewState.showBookingActivity(item)
    }

    override fun onBusinessInfoClick(item: Business, position: Int) {
        viewState.showBusinessActivity(item)
    }

    override fun onRecommendedBusinessClick(business: Business, position: Int) {
        viewState.showBusinessActivity(business)
    }

    override fun onRecommendedBusinessLikeClick(business: Business, position: Int) {
        if (paginationInProgress(mFavoriteDisposable)) {
            return
        }
        if (business.getIs().favorited) {
            deleteFavoriteBusiness(business)
        } else {
            addBusinessToFavorite(business)
        }
    }

    fun onSuggestBusinessClick() {
        AnalyticsHelper.logAddSalonButton(mContext)
        viewState.showSuggestBusinessActivity()
    }

    fun refreshFavorites() {
        cancelPagination(mFavoritePaginationDisposable)
        mFavorites.clear()
        mIsAllFavoritesLoaded = false
        makeFavoritePagination()
    }

    private fun refreshRecommended() {
        cancelPagination(mFavoritePaginationDisposable)
        mRecommended.clear()
        makeRecommendedPagination()
    }

    private fun makeFavoritePagination() {
        if (mFavorites.isEmpty()) {
            viewState.showProgress()
        }
        cancelPagination(mFavoritePaginationDisposable)
        val observable = mBusinessInteractor
                .getFavoritesByUser(Profile.getUserId(),
                        ObjectTypeUtil.business(),
                        SortUtil.dateDesc(),
                        null,
                        null,
                        null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        mFavoritePaginationDisposable = observable.subscribe({ this.onFavoritesLoadingSuccess(it) },
                { this.onFavoritesLoadingFailed(it) })
        addSubscription(mFavoritePaginationDisposable)
    }

    private fun makeRecommendedPagination() {
        if (mRecommended.isEmpty()) {
            viewState.showProgress()
        }
        cancelPagination(mFavoritePaginationDisposable)
        val observable = mBusinessInteractor
                .recommendedFavoriteBusinesses
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        mFavoritePaginationDisposable = observable.subscribe({ this.onRecommendedLoadingSuccess(it) },
                { this.onRecommendedLoadingFailed(it) })
        addSubscription(mFavoritePaginationDisposable)
    }

    private fun addBusinessToFavorite(business: Business) {
        mFavoriteDisposable = mRestManager
                .businessAddFavorite(business.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onAddBusinessToFavoriteSuccess(business) },
                        { this.onAddBusinessToFavoriteFailed(it) })
        addSubscription(mFavoriteDisposable)
    }

    private fun deleteFavoriteBusiness(business: Business) {
        mFavoriteDisposable = mRestManager
                .businessDeleteFavorite(business.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onDeleteFavoriteSuccess(business) },
                        { this.onDeleteFavoriteBusinessFailed(it) })
        addSubscription(mFavoriteDisposable)
    }

    private fun onFavoritesLoadingSuccess(businesses: List<Business>) {
        mFavorites.addAll(businesses)
        viewState.hideProgress()
        mFavoritePaginationDisposable = null
        mIsAllFavoritesLoaded = true
        if (mFavorites.isEmpty()) {
            refreshRecommended()
        } else {
            viewState.onFavoritesLoadingSuccess(mFavorites)
            mRecommended.clear()
            viewState.onRecommendedLoadingSuccess(mRecommended)
        }
    }

    private fun onFavoritesLoadingFailed(throwable: Throwable) {
        viewState.hideProgress()
        viewState.onFavoritesLoadingFailed(throwable)
        mFavoritePaginationDisposable = null
    }

    private fun onRecommendedLoadingSuccess(businesses: List<Business>) {
        mRecommended.addAll(businesses)
        viewState.hideProgress()
        viewState.onRecommendedLoadingSuccess(mRecommended)
        mFavoritePaginationDisposable = null
    }

    private fun onRecommendedLoadingFailed(throwable: Throwable) {
        viewState.hideProgress()
        viewState.onRecommendedLoadingFailed(throwable)
        mFavoritePaginationDisposable = null
    }

    private fun onAddBusinessToFavoriteSuccess(business: Business) {
        BusinessUtil.favoriteAdded(business)
        viewState.onAddBusinessToFavoriteSuccess()
        viewState.onRecommendedLoadingSuccess(mRecommended)
        EventBus.getDefault().post(BusinessFavoriteAdded(business, null))
        mFavoriteDisposable = null
    }

    private fun onAddBusinessToFavoriteFailed(throwable: Throwable) {
        viewState.onAddBusinessToFavoriteFailed(throwable)
        mFavoriteDisposable = null
    }

    private fun onDeleteFavoriteSuccess(business: Business) {
        BusinessUtil.favoriteRemoved(business)
        viewState.onDeleteFavoriteSuccess()
        viewState.onRecommendedLoadingSuccess(mRecommended)
        EventBus.getDefault().post(BusinessFavoriteRemoved(business, null))
        mFavoriteDisposable = null
    }

    private fun onDeleteFavoriteBusinessFailed(throwable: Throwable) {
        viewState.onDeleteFavoriteBusinessFailed(throwable)
        mFavoriteDisposable = null
    }

}
