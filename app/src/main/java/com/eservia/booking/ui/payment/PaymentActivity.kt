package com.eservia.booking.ui.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.eservia.booking.R
import com.eservia.booking.common.view.BaseActivity
import com.eservia.booking.util.WindowUtils
import com.eservia.utils.KeyboardUtil
import moxy.presenter.InjectPresenter

class PaymentActivity : BaseActivity(), PaymentView {

    @InjectPresenter
    lateinit var mPresenter: PaymentPresenter

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, PaymentActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        WindowUtils.setLightStatusBar(this)
        initViews()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun goBack() {
        KeyboardUtil.hideSoftKeyboard(this)
        finish()
    }

    private fun initViews() {
    }
}
