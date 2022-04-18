package com.eservia.booking.common.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.eservia.booking.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_dialog_sheet_two_buttons.*

class CommonInfoSheetDialog : BottomSheetDialogFragment() {

    interface Listener {
        fun onCommonInfoDialogAccepted()
        fun onCommonInfoDialogDismissed()
    }

    private var mListener: Listener? = null

    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"
        private const val BUTTON_TEXT = "button_text"

        @JvmStatic
        fun newInstance(title: String, message: String, buttonText: String): CommonInfoSheetDialog {
            val f = CommonInfoSheetDialog()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putString(BUTTON_TEXT, buttonText)
            f.arguments = args
            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_sheet_two_buttons, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (mListener != null) {
            mListener?.onCommonInfoDialogDismissed()
        }
        super.onDismiss(dialog)
    }

    fun setListener(listener: Listener?) {
        mListener = listener
    }

    private fun initViews() {
        tvTitle.text = arguments?.getString(TITLE) ?: ""
        tvSubTitle.text = arguments?.getString(MESSAGE) ?: ""
        rlCancel.visibility = View.GONE
        btnAccept.visibility = View.VISIBLE
        btnAccept.text = arguments?.getString(BUTTON_TEXT) ?: tvTitle.context.resources.getString(R.string.all_ok)
        btnAccept.background = ContextCompat.getDrawable(btnAccept.context, R.drawable.background_button_try_again)
        btnAccept.setOnClickListener {
            mListener?.onCommonInfoDialogAccepted()
            dismiss()
        }
    }
}
