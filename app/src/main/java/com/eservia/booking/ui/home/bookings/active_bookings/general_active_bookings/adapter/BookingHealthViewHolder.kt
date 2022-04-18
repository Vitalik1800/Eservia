package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.R
import com.eservia.booking.util.BookingUtil
import com.eservia.booking.util.BusinessUtil
import com.eservia.booking.util.ImageUtil
import com.eservia.booking.util.ViewUtil
import com.eservia.model.entity.BeautyBooking
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import kotlinx.android.synthetic.main.item_booking_beauty.view.*
import kotlinx.android.synthetic.main.item_booking_beauty_decision.view.*
import kotlinx.android.synthetic.main.item_booking_beauty_details.view.*
import kotlinx.android.synthetic.main.item_booking_beauty_header.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class BookingHealthViewHolder(itemView: View, val listener: BookingHealthListener)
    : RecyclerView.ViewHolder(itemView) {

    private val rlCardHolder = itemView.rlCardHolder
    private val cvContainer = itemView.cvContainer
    private val tvBusinessName = itemView.tvBusinessName
    private val tvAddress = itemView.tvAddress
    private val tvDate = itemView.tvDate
    private val tvTime = itemView.tvTime
    private val ivStaffImage = itemView.ivStaffImage
    private val tvStaffName = itemView.tvStaffName
    private val tvService = itemView.tvService
    private val ivDelete = itemView.ivDelete
    private val tvPrice = itemView.tvPrice
    private val tvCurrency = itemView.tvCurrency
    private val tvDecision = itemView.tvDecision
    private val include = itemView.include_decision

    fun bind(item: GeneralBookingsResponseData) {
        itemView.setOnClickListener {
            listener.onBookingHealthClick(item, adapterPosition)
        }
        ivDelete.setOnClickListener {
            listener.onBookingHealthDeleteClick(item, adapterPosition)
        }

        val booking = item.body as BeautyBooking

        if (booking.staff != null) {
            ImageUtil.displayStaffImageRound(itemView.context, ivStaffImage,
                    booking.staff.photo, R.drawable.user_man_big)

            tvStaffName.text = BusinessUtil.getStaffFullName(
                    booking.staff.firstName, booking.staff.lastName)
        } else {
            tvStaffName.text = ""
        }

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

        if (booking.date != null) {
            val pattern = BookingUtil.TIME_SLOT_PATTERN
            val date = DateTime.parse(booking.date, DateTimeFormat.forPattern(pattern))
            tvDate.text = date.toString("dd.MM.yyyy")
            tvTime.text = date.toString(BusinessUtil.FORMAT_HOUR_MINUTES)
        } else {
            tvDate.text = ""
            tvTime.text = ""
        }

        if (booking.service != null) {
            val service = booking.service

            tvService.text = BookingUtil.bookingServiceDescription(itemView.context,
                    service.name, booking.duration)

            val price = booking.amount
            if (!BookingUtil.servicePriceIsEmpty(price)) {
                tvPrice.text = BookingUtil.formatPrice(price)

                val currency = booking.currency
                if (currency != null && currency.isNotEmpty()) {
                    tvCurrency.text = currency
                } else {
                    tvCurrency.text = ""
                }
            } else {
                tvPrice.text = ""
                tvCurrency.text = ""
            }

        } else {
            tvService.text = ""
            tvPrice.text = ""
            tvCurrency.text = ""
        }

        if (booking.decision != null && booking.decision == BeautyBooking.DECISION_APPROVED) {

            include.visibility = View.VISIBLE
            tvDecision.text = itemView.context.resources.getString(R.string.booking_decision_approved)
            tvDecision.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))

        } else if (booking.decision != null && booking.decision == BeautyBooking.DECISION_PENDING) {

            include.visibility = View.VISIBLE
            tvDecision.text = itemView.context.resources.getString(R.string.booking_decision_pending)
            tvDecision.setTextColor(ContextCompat.getColor(itemView.context, R.color.gold_brick))

        } else {
            include.visibility = View.GONE
            tvDecision.text = ""
        }

        ViewUtil.setCardOutlineProvider(itemView.context, rlCardHolder, cvContainer)
    }
}
