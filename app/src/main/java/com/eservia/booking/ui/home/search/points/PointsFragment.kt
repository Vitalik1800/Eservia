package com.eservia.booking.ui.home.search.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.eservia.booking.R
import com.eservia.booking.common.view.SpeedyLinearLayoutManager
import com.eservia.booking.ui.home.BaseHomeFragment
import com.eservia.booking.ui.home.HomeActivity
import com.eservia.booking.ui.home.search.points.adapter.PointsAdapter
import com.eservia.booking.util.ColorUtil
import com.eservia.booking.util.FragmentUtil
import com.eservia.booking.util.ViewUtil
import com.eservia.booking.util.WindowUtils
import com.eservia.utils.KeyboardUtil
import kotlinx.android.synthetic.main.fragment_points.*
import kotlinx.android.synthetic.main.layout_points_header.*
import moxy.presenter.InjectPresenter

class PointsFragment : BaseHomeFragment(), PointsView {

    @InjectPresenter
    lateinit var mPresenter: PointsPresenter

    lateinit var mActivity: HomeActivity

    lateinit var mAdapter: PointsAdapter

    companion object {
        const val TAG = "PointsFragment"

        @JvmStatic
        fun newInstance() = PointsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_points, container, false)
        mActivity = activity as HomeActivity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (parentFragment != null) {
                    mPresenter.onCloseClick()
                }
                return true
            }
        }
        return false
    }

    override fun refresh() {
        ViewUtil.scrollTop(app_bar_layout, rvPoints)
    }

    override fun willBeDisplayed() {
        WindowUtils.setNormalStatusBar(mActivity)
        FragmentUtil.startFragmentTabSelectAnimation(activity, fragment_container)
    }

    override fun willBeHidden() {
    }

    override fun showProgress() {
        swipeContainer.isRefreshing = true
    }

    override fun hideProgress() {
        swipeContainer.isRefreshing = false
    }

    override fun goBack() {
        KeyboardUtil.hideSoftKeyboard(mActivity)
        mActivity.onBackPressed()
    }

    override fun showPointsDetails() {
        parentFragment?.let {
            FragmentUtil.showPointsDetailsFragment(parentFragment?.childFragmentManager, R.id.fragSearchContainer)
        }
    }

    private fun initViews() {
        mActivity.setSupportActionBar(toolbar)
        mActivity.supportActionBar?.title = ""
        mActivity.supportActionBar?.elevation = 0f
        initSwipeRefresh()
        initList()
        initOutlineProviders()
    }

    private fun initOutlineProviders() {
        ViewUtil.setCardOutlineProviderStraightCorners(mActivity, rlPointsHeader, cvPointsHeader)
    }

    private fun initSwipeRefresh() {
        swipeContainer.setOnRefreshListener { mPresenter.refresh() }
        swipeContainer.setColorSchemeColors(*ColorUtil.swipeRefreshColors(mActivity))
    }

    private fun initList() {
        mAdapter = PointsAdapter(mPresenter)
        rvPoints.adapter = mAdapter
        rvPoints.setHasFixedSize(true)
        rvPoints.layoutManager = SpeedyLinearLayoutManager(mActivity,
                SpeedyLinearLayoutManager.VERTICAL, false)
    }
}
