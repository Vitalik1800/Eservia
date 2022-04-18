package com.eservia.booking.ui.home.favorite.favorite.listeners

import com.eservia.model.entity.Business

interface OnBusinessItemClickListener {

    fun onBusinessReserveClick(item: Business, position: Int)

    fun onBusinessInfoClick(item: Business, position: Int)
}
