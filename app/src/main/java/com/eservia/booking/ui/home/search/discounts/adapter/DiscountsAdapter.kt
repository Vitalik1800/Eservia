package com.eservia.booking.ui.home.search.discounts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.R
import com.eservia.booking.common.adapter.BaseRecyclerAdapter
import com.eservia.booking.ui.home.search.points.adapter.BaseItem
import com.eservia.booking.ui.home.search.points.adapter.BaseItemTypeEnum
import com.eservia.booking.ui.home.search.points.adapter.NothingFoundAdapterItem
import com.eservia.booking.ui.home.search.points.adapter.NothingFoundViewHolder

class DiscountsAdapter(val listener: DiscountsViewHolder.DiscountActionListener)
    : BaseRecyclerAdapter<BaseItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BaseItemTypeEnum.DISCOUNT -> {
                val itemView = inflater.inflate(R.layout.item_discount, parent, false)
                DiscountsViewHolder(itemView, listener)
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
            BaseItemTypeEnum.DISCOUNT -> (holder as DiscountsViewHolder)
                    .bind(item as DiscountsAdapterItem)
            BaseItemTypeEnum.NOTHING_FOUND -> (holder as NothingFoundViewHolder)
                    .bind(item as NothingFoundAdapterItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}
