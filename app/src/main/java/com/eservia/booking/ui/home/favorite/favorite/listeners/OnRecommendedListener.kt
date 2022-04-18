package com.eservia.booking.ui.home.favorite.favorite.listeners

import com.eservia.model.entity.Business

interface OnRecommendedListener {

    fun onRecommendedBusinessClick(business: Business, position: Int)

    fun onRecommendedBusinessLikeClick(business: Business, position: Int)
}
