package com.eservia.booking.ui.home.search.discounts.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.util.ViewUtil
import kotlinx.android.synthetic.main.item_discount.view.*

class DiscountsViewHolder(itemView: View, val listener: DiscountActionListener)
    : RecyclerView.ViewHolder(itemView) {

    interface DiscountActionListener {
        fun onDiscountItemClick(item: DiscountsAdapterItem)
    }

    private val clDiscountItem = itemView.clDiscountItem
    private val cvDiscountItem = itemView.cvDiscountItem
    private val tvBusinessName = itemView.tvBusinessName
    private val tvDate = itemView.tvDate
    private val tvDiscount = itemView.tvDiscount

    @SuppressLint("SetTextI18n")
    fun bind(item: DiscountsAdapterItem) {
        clDiscountItem.setOnClickListener { listener.onDiscountItemClick(item) }
        ViewUtil.setCardOutlineProvider(itemView.context, clDiscountItem, cvDiscountItem)
        tvBusinessName.text = "test"
        tvDate.text = "10.22.2222"
        tvDiscount.text = "666%"
    }
}
