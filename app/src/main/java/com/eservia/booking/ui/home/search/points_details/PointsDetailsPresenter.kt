package com.eservia.booking.ui.home.search.points_details

import com.eservia.booking.common.presenter.BasePresenter
import moxy.InjectViewState


@InjectViewState
class PointsDetailsPresenter : BasePresenter<PointsDetailsView>() {

    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun refresh() {
    }

    fun onCloseClick() {
        viewState.goBack()
    }
}