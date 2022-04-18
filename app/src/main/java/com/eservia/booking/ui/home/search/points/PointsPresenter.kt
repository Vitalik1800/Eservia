package com.eservia.booking.ui.home.search.points

import com.eservia.booking.common.presenter.BasePresenter
import com.eservia.booking.ui.home.search.points.adapter.PointsAdapterItem
import com.eservia.booking.ui.home.search.points.adapter.PointsViewHolder
import moxy.InjectViewState

@InjectViewState
class PointsPresenter : BasePresenter<PointsView>(), PointsViewHolder.PointsActionListener {

    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onPointsItemClick(item: PointsAdapterItem) {
        viewState.showPointsDetails()
    }

    fun refresh() {
    }

    fun onCloseClick() {
        viewState.goBack()
    }
}
