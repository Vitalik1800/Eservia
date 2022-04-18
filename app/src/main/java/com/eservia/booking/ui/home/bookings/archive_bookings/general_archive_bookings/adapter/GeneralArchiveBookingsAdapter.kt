package com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.R
import com.eservia.booking.common.adapter.BaseRecyclerAdapter
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter.BookingDeliveryListener
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter.BookingPaginationListener
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter.BookingRestoDeliveryViewHolder
import com.eservia.model.entity.BookingKindEnum
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData

class GeneralArchiveBookingsAdapter(private val paginationListener: BookingPaginationListener,
                                    private val beautyListener: BookingBeautyListener,
                                    private val healthListener: BookingHealthListener,
                                    private val restoListener: BookingRestoListener,
                                    private val deliveryListener: BookingDeliveryListener)
    : BaseRecyclerAdapter<GeneralBookingsResponseData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BookingKindEnum.BEAUTY -> {
                val itemView = inflater.inflate(R.layout.item_booking_beauty_archive, parent, false)
                BookingBeautyViewHolder(itemView, beautyListener)
            }
            BookingKindEnum.HEALTH -> {
                val itemView = inflater.inflate(R.layout.item_booking_beauty_archive, parent, false)
                BookingHealthViewHolder(itemView, healthListener)
            }
            BookingKindEnum.BOOKING -> {
                val itemView = inflater.inflate(R.layout.item_booking_resto, parent, false)
                BookingRestoViewHolder(itemView, restoListener)
            }
            else -> {
                val itemView = inflater.inflate(R.layout.item_booking_resto_delivery, parent, false)
                BookingRestoDeliveryViewHolder(itemView, deliveryListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= itemCount - THRESHHOLD) {
            paginationListener.loadMoreBookings()
        }
        val item = getItem(position)
        when (item.kind) {
            BookingKindEnum.BEAUTY -> (holder as BookingBeautyViewHolder).bind(item)
            BookingKindEnum.HEALTH -> (holder as BookingHealthViewHolder).bind(item)
            BookingKindEnum.BOOKING -> (holder as BookingRestoViewHolder).bind(item)
            BookingKindEnum.DELIVERY -> (holder as BookingRestoDeliveryViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).kind
    }
}
