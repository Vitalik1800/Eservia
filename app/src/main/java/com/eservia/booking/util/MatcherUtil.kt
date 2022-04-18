package com.eservia.booking.util

object MatcherUtil {

    @JvmStatic
    fun getConfirmCode(message: String): String? {
        return PatternsUtil.CONFIRM_CODE_MATCHER
                .toRegex()
                .find(PatternsUtil.CONFIRM_SMS_CODE_MATCHER
                        .toRegex()
                        .find(message)
                        ?.value ?: "")
                ?.value
    }
}
