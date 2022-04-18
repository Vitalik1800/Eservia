package com.eservia.booking.ui.home.search.points

import com.eservia.booking.common.view.LoadingView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PointsView : LoadingView {

    @StateStrategyType(SkipStrategy::class)
    fun goBack()

    @StateStrategyType(SkipStrategy::class)
    fun showPointsDetails()
}
