package com.eservia.booking.ui.home.search.points.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.R
import com.eservia.booking.common.adapter.BaseRecyclerAdapter

class PointsAdapter(val listener: PointsViewHolder.PointsActionListener)
    : BaseRecyclerAdapter<BaseItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BaseItemTypeEnum.POINTS -> {
                val itemView = inflater.inflate(R.layout.item_points, parent, false)
                PointsViewHolder(itemView, listener)
            }
            else -> {
                val itemView = inflater.inflate(R.layout.item_search_not_found_place_holder, parent, false)
                NothingFoundViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item.type) {
            BaseItemTypeEnum.POINTS -> (holder as PointsViewHolder)
                    .bind(item as PointsAdapterItem)
            BaseItemTypeEnum.NOTHING_FOUND -> (holder as NothingFoundViewHolder)
                    .bind(item as NothingFoundAdapterItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}
