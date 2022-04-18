package com.eservia.model.interactors.business

import com.eservia.model.entity.Business
import com.eservia.model.prefs.Profile

object BusinessSearchableHelper {

    private val userIdsWithSpecialAccess = arrayOf(
            "6c6b6a23-85d1-4ad8-8475-7ca717a51b0e", // maxym
            "d33aad2c-5a2f-44cb-a245-9c627ea6089e", // maxym stage
            "1916d01a-04e9-4a86-9bc8-c7f7827e0039", // sasha
            "86ea3842-893c-4c15-a073-1eded2345ea5", // sasha stage
            "b2de532a-24c4-4fe7-b85a-16f233702f02", // yulia
            "490d9d85-620c-448b-a1f7-cf385450f20e", // yulia frank
            "3618f6a2-39b8-4dd4-9e33-cac9ab7c6f62", // katia frank
            "1ea50f50-4c28-489c-a906-f0fe59306a6d"  // tanya
    )

    @JvmStatic
    fun getSearchableQuery(): Int? {
        return if (userIdsWithSpecialAccess.contains(Profile.getUserId()))
            null
        else
            Business.SEARCHABLE_TRUE
    }
}
