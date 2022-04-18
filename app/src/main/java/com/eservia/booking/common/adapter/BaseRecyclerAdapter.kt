package com.eservia.booking.common.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val THRESHHOLD = 25
    }

    protected var listItems: MutableList<T> = ArrayList()

    override fun getItemCount(): Int {
        return this.listItems.size
    }

    fun getItem(position: Int): T {
        return this.listItems[position]
    }

    fun getAdapterItems(): List<T> {
        return ArrayList(this.listItems)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(items: List<T>) {
        this.listItems.addAll(items)
        notifyDataSetChanged()
    }

    fun replaceAll(items: List<T>) {
        this.listItems.clear()
        addAll(items)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: T) {
        this.listItems.add(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(item: T) {
        this.listItems.remove(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(id: Int) {
        this.listItems.removeAt(id)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAll() {
        this.listItems.clear()
        notifyDataSetChanged()
    }
}
