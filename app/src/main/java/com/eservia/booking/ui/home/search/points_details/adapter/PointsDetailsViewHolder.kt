package com.eservia.booking.ui.home.search.points_details.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.util.ViewUtil
import kotlinx.android.synthetic.main.item_points_details.view.*

class PointsDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val clPointsDetailsItem = itemView.clPointsDetailsItem
    private val cvPointsDetailsItem = itemView.cvPointsDetailsItem
    private val tvDate = itemView.tvDate
    private val tvTime = itemView.tvTime
    private val tvService = itemView.tvService
    private val tvPoints = itemView.tvPoints

    @SuppressLint("SetTextI18n")
    fun bind(item: PointsDetailsAdapterItem) {
        ViewUtil.setCardOutlineProvider(itemView.context, clPointsDetailsItem, cvPointsDetailsItem)
        tvDate.text = "12.06.2019"
        tvTime.text = "13:40"
        tvService.text = "Уход для рук с маникюром и покрытием"
        tvPoints.text = "666"
    }
}
