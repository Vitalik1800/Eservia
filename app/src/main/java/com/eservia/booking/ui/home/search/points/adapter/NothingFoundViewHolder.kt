package com.eservia.booking.ui.home.search.points.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.R
import kotlinx.android.synthetic.main.item_search_not_found_place_holder.view.*

class NothingFoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivPlaceHolderLogoSearch = itemView.ivPlaceHolderLogoSearch
    private val tvPlaceHolderLabelSearch = itemView.tvPlaceHolderLabelSearch
    private val btnPlaceHolderButton = itemView.btnPlaceHolderButton

    fun bind(item: NothingFoundAdapterItem) {
        btnPlaceHolderButton.visibility = View.INVISIBLE
        tvPlaceHolderLabelSearch.text = itemView.context.resources.getString(R.string.placeholder_message_not_found)
    }
}
