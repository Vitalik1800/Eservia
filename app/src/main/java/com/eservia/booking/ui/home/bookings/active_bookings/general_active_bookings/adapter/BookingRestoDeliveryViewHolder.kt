package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.R
import com.eservia.booking.util.BookingUtil
import com.eservia.booking.util.BusinessUtil
import com.eservia.booking.util.ViewUtil
import com.eservia.model.entity.RestoDelivery
import com.eservia.model.entity.RestoDeliveryStatus
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import kotlinx.android.synthetic.main.item_booking_beauty_decision.view.*
import kotlinx.android.synthetic.main.item_booking_beauty_header.view.*
import kotlinx.android.synthetic.main.item_booking_resto_delivery.view.*
import kotlinx.android.synthetic.main.item_booking_resto_delivery_details.view.*

class BookingRestoDeliveryViewHolder(itemView: View, val listener: BookingDeliveryListener)
    : RecyclerView.ViewHolder(itemView) {

    private val rlCardHolder = itemView.rlCardHolder
    private val cvContainer = itemView.cvContainer
    private val tvBusinessName = itemView.tvBusinessName
    private val tvAddress = itemView.tvAddress
    private val tvDate = itemView.tvDate
    private val tvTime = itemView.tvTime
    private val tvDecision = itemView.tvDecision
    private val include = itemView.include_decision
    private val tvPhone = itemView.tvPhone
    private val tvUserName = itemView.tvUserName
    private val tvPrice = itemView.tvPrice

    fun bind(item: GeneralBookingsResponseData) {
        itemView.setOnClickListener {
            listener.onDeliveryClick(item, adapterPosition)
        }

        val booking = item.body as RestoDelivery

        if (booking.business != null) {
            tvBusinessName.text = booking.business.name
        } else {
            tvBusinessName.text = ""
        }

        if (booking.address != null) {
            val city = booking.address.city
            val street = booking.address.street
            val number = booking.address.number
            tvAddress.text = BusinessUtil.getFullAddress(city, street, number)
        } else {
            tvAddress.text = ""
        }

        if (booking.expectedDeliveryDate != null) {
            val date = BookingUtil.restoBookingTimeFromFormatted(booking.expectedDeliveryDate)
            tvDate.text = date.toString("dd.MM.yyyy")
            tvTime.text = date.toString(BusinessUtil.FORMAT_HOUR_MINUTES)
        } else {
            tvDate.text = ""
            tvTime.text = ""
        }

        if (booking.clientName != null) {
            tvUserName.text = booking.clientName
        } else {
            tvUserName.text = ""
        }

        if (booking.clientPhone != null) {
            tvPhone.text = booking.clientPhone
        } else {
            tvPhone.text = ""
        }

        if (booking.price != null) {
            tvPrice.text = BookingUtil.formatRestoDeliveryPrice(itemView.context, booking.price)
        } else {
            tvPrice.text = ""
        }

        when (if (booking.status != null) booking.status.toInt() else -1) {
            RestoDeliveryStatus.CREATED, RestoDeliveryStatus.NOT_AGREED, RestoDeliveryStatus.WAITING,
            RestoDeliveryStatus.ACCEPTED, RestoDeliveryStatus.PENDING -> {
                include.visibility = View.VISIBLE
                tvDecision.text = itemView.context.resources.getString(R.string.booking_decision_pending)
                tvDecision.setTextColor(ContextCompat.getColor(itemView.context, R.color.gold_brick))
            }
            RestoDeliveryStatus.IN_PROGRESS -> {
                include.visibility = View.VISIBLE
                tvDecision.text = itemView.context.resources.getString(R.string.delivery_status_in_progress)
                tvDecision.setTextColor(ContextCompat.getColor(itemView.context, R.color.resto))
            }
            RestoDeliveryStatus.DONE -> {
                include.visibility = View.VISIBLE
                tvDecision.text = itemView.context.resources.getString(R.string.delivered)
                tvDecision.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
            }
            RestoDeliveryStatus.CANCELLED, RestoDeliveryStatus.OVERDUE -> {
                include.visibility = View.VISIBLE
                tvDecision.text = itemView.context.resources.getString(R.string.canceled)
                tvDecision.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            }
            else -> {
                include.visibility = View.GONE
                tvDecision.text = ""
            }
        }

        ViewUtil.setCardOutlineProvider(itemView.context, rlCardHolder, cvContainer)
    }
}
