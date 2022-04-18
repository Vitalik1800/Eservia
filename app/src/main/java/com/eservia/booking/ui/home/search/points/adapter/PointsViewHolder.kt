package com.eservia.booking.ui.home.search.points.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.util.ViewUtil
import kotlinx.android.synthetic.main.item_points.view.*

class PointsViewHolder(itemView: View, val listener: PointsActionListener)
    : RecyclerView.ViewHolder(itemView) {

    interface PointsActionListener {
        fun onPointsItemClick(item: PointsAdapterItem)
    }

    private val clPointsItem = itemView.clPointsItem
    private val cvPointsItem = itemView.cvPointsItem
    private val tvBusinessName = itemView.tvBusinessName
    private val tvPoints = itemView.tvPoints

    @SuppressLint("SetTextI18n")
    fun bind(item: PointsAdapterItem) {
        clPointsItem.setOnClickListener { listener.onPointsItemClick(item) }
        ViewUtil.setCardOutlineProvider(itemView.context, clPointsItem, cvPointsItem)
        tvBusinessName.text = "test"
        tvPoints.text = "666"
    }
}
