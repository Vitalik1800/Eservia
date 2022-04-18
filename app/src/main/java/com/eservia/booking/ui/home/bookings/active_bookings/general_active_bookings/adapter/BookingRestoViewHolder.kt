package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eservia.booking.R
import com.eservia.booking.util.BookingUtil
import com.eservia.booking.util.BusinessUtil
import com.eservia.booking.util.ViewUtil
import com.eservia.model.entity.RestoBooking
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import kotlinx.android.synthetic.main.item_booking_beauty_decision.view.*
import kotlinx.android.synthetic.main.item_booking_beauty_header.view.*
import kotlinx.android.synthetic.main.item_booking_resto.view.*
import kotlinx.android.synthetic.main.item_booking_resto_details.view.*

class BookingRestoViewHolder(itemView: View, val listener: BookingRestoListener)
    : RecyclerView.ViewHolder(itemView) {

    private val rlCardHolder = itemView.rlCardHolder
    private val cvContainer = itemView.cvContainer
    private val tvBusinessName = itemView.tvBusinessName
    private val tvAddress = itemView.tvAddress
    private val tvDate = itemView.tvDate
    private val tvTime = itemView.tvTime
    private val include = itemView.include_decision
    private val tvDecision = itemView.tvDecision
    private val tvNumberOfPersons = itemView.tvNumberOfPersons
    private val tvVisitDuration = itemView.tvVisitDuration
    private val tvDepartment = itemView.tvDepartment

    fun bind(item: GeneralBookingsResponseData) {
        itemView.setOnClickListener {
            listener.onBookingRestoClick(item, adapterPosition)
        }
        itemView.setOnClickListener {
            listener.onBookingRestoDeleteClick(item, adapterPosition)
        }

        val booking = item.body as RestoBooking

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

        if (booking.bookingDateTime != null) {
            val date = BookingUtil.restoBookingTimeFromFormatted(booking.bookingDateTime)
            tvDate.text = date.toString("dd.MM.yyyy")
            tvTime.text = date.toString(BusinessUtil.FORMAT_HOUR_MINUTES)
        } else {
            tvDate.text = ""
            tvTime.text = ""
        }

        if (booking.peopleCount != null) {
            tvNumberOfPersons.text = BookingUtil.formatRestoPeopleCount(itemView.context,
                    booking.peopleCount)
        } else {
            tvNumberOfPersons.text = ""
        }

        if (booking.bookingDateTime != null && booking.bookingEndTime != null) {
            tvVisitDuration.text = BookingUtil.formatRestoVisitDuration(itemView.context,
                    booking.bookingDateTime, booking.bookingEndTime)
        } else {
            tvVisitDuration.text = ""
        }

        if (booking.department != null) {
            tvDepartment.text = BookingUtil.formatRestoDepartment(itemView.context,
                    booking.department.name)
        } else {
            tvDepartment.text = ""
        }

        if (booking.statusId != null && (booking.statusId == RestoBooking.STATUS_APPROVED
                        || booking.statusId == RestoBooking.STATUS_NEW
                        || booking.statusId == RestoBooking.STATUS_MODIFIED)) {

            include.visibility = View.VISIBLE
            tvDecision.text = itemView.context.resources.getString(R.string.booking_decision_approved)
            tvDecision.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
        } else {
            include.visibility = View.GONE
            tvDecision.text = ""
        }

        ViewUtil.setCardOutlineProvider(itemView.context, rlCardHolder, cvContainer)
    }
}
