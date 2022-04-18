package com.eservia.booking.ui.home.favorite.favorite.favorites_sector

import com.eservia.booking.common.view.LoadingView
import com.eservia.model.entity.Business
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface FavoritesSectorView : LoadingView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onFavoritesLoadingSuccess(businessList: List<Business>)

    @StateStrategyType(SkipStrategy::class)
    fun onFavoritesLoadingFailed(throwable: Throwable)

    @StateStrategyType(SkipStrategy::class)
    fun showBusinessActivity(business: Business)

    @StateStrategyType(value = SkipStrategy::class)
    fun showBookingActivity(business: Business)

    @StateStrategyType(SkipStrategy::class)
    fun showSuggestBusinessActivity()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onRecommendedLoadingSuccess(businessList: List<Business>)

    @StateStrategyType(SkipStrategy::class)
    fun onRecommendedLoadingFailed(throwable: Throwable)

    @StateStrategyType(SkipStrategy::class)
    fun onAddBusinessToFavoriteSuccess()

    @StateStrategyType(SkipStrategy::class)
    fun onAddBusinessToFavoriteFailed(throwable: Throwable)

    @StateStrategyType(SkipStrategy::class)
    fun onDeleteFavoriteSuccess()

    @StateStrategyType(SkipStrategy::class)
    fun onDeleteFavoriteBusinessFailed(throwable: Throwable)
}
