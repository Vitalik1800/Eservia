package com.eservia.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.style.URLSpan
import android.text.util.Linkify
import android.view.View
import androidx.core.util.Pair
import io.reactivex.functions.Consumer

object SpanUtil {

    @JvmStatic
    fun initLinkableString(list: List<Pair<String, String?>>, action1: Consumer<String>): SpannableString {
        var spanStart: Int
        var spanEnd: Int
        val stringBuilder = StringBuilder()
        var linkSpan: LinkSpan

        val spannable = SpannableString(getFullText(list))
        Linkify.addLinks(spannable, Linkify.WEB_URLS)

        for (pair in list) {
            val link = pair.second ?: ""
            if (link.isNotEmpty()) {
                spanStart = stringBuilder.length
                stringBuilder.append(pair.first)
                spanEnd = stringBuilder.length
                linkSpan = LinkSpan(pair.second, action1)
                spannable.setSpan(linkSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                stringBuilder.append(pair.first)
            }
        }

        return spannable
    }

    private fun getFullText(list: List<Pair<String, String?>>): String {
        val stringBuilder = StringBuilder()
        list.map { stringBuilder.append(it.first) }

        return stringBuilder.toString()
    }
    

    private class LinkSpan(var url: String?, var action: Consumer<String>) : URLSpan(url) {
        override fun onClick(view: View) = action.accept(url)
    }
}
