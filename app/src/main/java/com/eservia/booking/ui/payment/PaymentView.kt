package com.eservia.booking.ui.payment

import com.eservia.booking.common.view.LoadingView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PaymentView : LoadingView {

    @StateStrategyType(SkipStrategy::class)
    fun goBack()
}
