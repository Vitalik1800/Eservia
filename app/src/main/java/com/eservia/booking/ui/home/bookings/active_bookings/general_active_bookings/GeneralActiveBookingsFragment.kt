package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eservia.booking.R
import com.eservia.booking.common.view.SpeedyLinearLayoutManager
import com.eservia.booking.ui.home.BaseHomeFragment
import com.eservia.booking.ui.home.HomeActivity
import com.eservia.booking.ui.home.bookings.active_bookings.OnBookingsRestoListener
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter.GeneralActiveBookingsAdapter
import com.eservia.booking.util.ColorUtil
import com.eservia.booking.util.MessageUtil
import com.eservia.model.entity.RestoDelivery
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import kotlinx.android.synthetic.main.fragment_active_bookings_beauty.*
import moxy.presenter.InjectPresenter

class GeneralActiveBookingsFragment : BaseHomeFragment(), GeneralActiveBookingsView {

    @InjectPresenter
    lateinit var mPresenter: GeneralActiveBookingsPresenter

    lateinit var mActivity: HomeActivity

    lateinit var mAdapter: GeneralActiveBookingsAdapter

    private var mParentListener: OnBookingsRestoListener? = null

    private var mCancelBookingDialog: CancelBookingDialogSheet? = null

    companion object {
        const val TAG = "GeneralActiveBookingsFragment"

        @JvmStatic
        fun newInstance() = GeneralActiveBookingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_active_bookings_beauty, container, false)
        mActivity = activity as HomeActivity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OnBookingsRestoListener) {
            mParentListener = parentFragment as OnBookingsRestoListener
        } else {
            throw RuntimeException("The parent fragment must implement OnBookingsRestoListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mParentListener = null
    }

    override fun onDestroy() {
        hideCancelBookingDialog()
        super.onDestroy()
    }

    override fun refresh() {
        if (rvBookings != null) {
            rvBookings.post { rvBookings.smoothScrollToPosition(0) }
        }
    }

    override fun willBeDisplayed() {
    }

    override fun willBeHidden() {
    }

    override fun showProgress() {
        swipeContainer.isRefreshing = true
    }

    override fun hideProgress() {
        swipeContainer.isRefreshing = false
    }

    override fun onBookingsLoadingSuccess(bookings: List<GeneralBookingsResponseData>) {
        mAdapter.replaceAll(bookings)
        revalidatePlaceHolder()
    }

    override fun onBookingsLoadingFailed(throwable: Throwable) {
        revalidatePlaceHolder()
    }

    override fun showCancelBookingDialog() {
        openCancelBookingDialog()
    }

    override fun hideCancelBookingDialog() {
        mCancelBookingDialog?.dismiss()
    }

    override fun onBookingCancelSuccess() {
        MessageUtil.showToast(context, R.string.cancel_success)
    }

    override fun onBookingCancelFailed(throwable: Throwable) {
        MessageUtil.showSnackbar(swipeContainer, throwable)
    }

    override fun openDeliveryDetails(delivery: RestoDelivery) {
        mParentListener?.onOpenDeliveryInfo(delivery)
    }

    private fun initViews() {
        initSwipeRefresh()
        initList()
    }

    private fun revalidatePlaceHolder() {
        phlPlaceholder.isEmpty = mAdapter.itemCount == 0
    }

    private fun initSwipeRefresh() {
        swipeContainer.setOnRefreshListener { mPresenter.refreshBookings() }
        swipeContainer.setColorSchemeColors(*ColorUtil.swipeRefreshColors(mActivity))
    }

    private fun initList() {
        mAdapter = GeneralActiveBookingsAdapter(mPresenter, mPresenter, mPresenter, mPresenter, mPresenter)
        rvBookings.setHasFixedSize(true)
        rvBookings.layoutManager = SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false)
        rvBookings.adapter = mAdapter
    }

    private fun openCancelBookingDialog() {
        mCancelBookingDialog = CancelBookingDialogSheet.newInstance()
        mCancelBookingDialog?.setListener(mPresenter)
        mCancelBookingDialog?.show(mActivity.supportFragmentManager, CancelBookingDialogSheet::class.java.simpleName)
    }
}
