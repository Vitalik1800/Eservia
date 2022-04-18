package com.eservia.booking.ui.home.search.points_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.eservia.booking.R
import com.eservia.booking.common.view.SpeedyLinearLayoutManager
import com.eservia.booking.ui.home.BaseHomeFragment
import com.eservia.booking.ui.home.HomeActivity
import com.eservia.booking.ui.home.search.points_details.adapter.PointsDetailsAdapter
import com.eservia.booking.util.ColorUtil
import com.eservia.booking.util.FragmentUtil
import com.eservia.booking.util.ViewUtil
import com.eservia.booking.util.WindowUtils
import com.eservia.utils.KeyboardUtil
import kotlinx.android.synthetic.main.fragment_points_details.*
import kotlinx.android.synthetic.main.layout_points_header.*
import moxy.presenter.InjectPresenter

class PointsDetailsFragment : BaseHomeFragment(), PointsDetailsView {

    @InjectPresenter
    lateinit var mPresenter: PointsDetailsPresenter

    lateinit var mActivity: HomeActivity

    lateinit var mAdapter: PointsDetailsAdapter

    companion object {
        const val TAG = "PointsDetailsFragment"

        @JvmStatic
        fun newInstance() = PointsDetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_points_details, container, false)
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

    private fun initViews() {
        mActivity.setSupportActionBar(toolbar)
        mActivity.supportActionBar?.title = ""
        mActivity.supportActionBar?.elevation = 0f
        initSwipeRefresh()
        initList()
        initOutlineProviders()
        initListTitle()
    }

    private fun initListTitle() {
        tvPointsListTitle.text = mActivity.resources.getString(R.string.accruals_history)
    }

    private fun initOutlineProviders() {
        ViewUtil.setCardOutlineProviderStraightCorners(mActivity, rlPointsHeader, cvPointsHeader)
    }

    private fun initSwipeRefresh() {
        swipeContainer.setOnRefreshListener { mPresenter.refresh() }
        swipeContainer.setColorSchemeColors(*ColorUtil.swipeRefreshColors(mActivity))
    }

    private fun initList() {
        mAdapter = PointsDetailsAdapter()
        rvPoints.adapter = mAdapter
        rvPoints.setHasFixedSize(true)
        rvPoints.layoutManager = SpeedyLinearLayoutManager(mActivity,
                SpeedyLinearLayoutManager.VERTICAL, false)
    }
}
