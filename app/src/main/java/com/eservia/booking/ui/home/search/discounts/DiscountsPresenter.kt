package com.eservia.booking.ui.home.search.discounts

import com.eservia.booking.common.presenter.BasePresenter
import com.eservia.booking.ui.home.search.discounts.adapter.DiscountsAdapterItem
import com.eservia.booking.ui.home.search.discounts.adapter.DiscountsViewHolder
import moxy.InjectViewState

@InjectViewState
class DiscountsPresenter : BasePresenter<DiscountsView>(), DiscountsViewHolder.DiscountActionListener {

    override fun onDiscountItemClick(item: DiscountsAdapterItem) {
        viewState.showDiscountDetails()
    }

    fun refresh() {
    }

    fun onCloseClick() {
        viewState.goBack()
    }
}
