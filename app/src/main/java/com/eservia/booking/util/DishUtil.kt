package com.eservia.booking.util

import android.content.Context
import com.eservia.booking.R
import com.eservia.model.entity.OrderRestoNomenclature
import com.eservia.model.entity.OrderRestoSaleMethod
import java.text.DecimalFormat
import java.util.*
import kotlin.math.roundToInt

object DishUtil {

    const val MAX_DELIVERY_DISH_PORTIONS_COUNT: Int = 15

    private fun getMinSize(dbDish: OrderRestoNomenclature): Double {

        return when (dbDish.saleMethodId) {
            OrderRestoSaleMethod.SIZE_PRICE -> {
                dbDish.sizePrices.minByOrNull { it.size }?.size ?: 0.0
            }
            OrderRestoSaleMethod.PORTION -> {
                dbDish.portionGradation.target?.minimum ?: 0.0
            }
            OrderRestoSaleMethod.BY_WEIGHT -> {
                0.0
            }
            else -> {
                0.0
            }
        }
    }

    private fun getMinPrice(dbDish: OrderRestoNomenclature): Double {

        return when (dbDish.saleMethodId) {
            OrderRestoSaleMethod.SIZE_PRICE -> {
                dbDish.sizePrices.minByOrNull { it.price }?.price ?: 0.0
            }
            OrderRestoSaleMethod.PORTION -> {
                dbDish.portionGradation.target?.minimumPrice ?: 0.0
            }
            OrderRestoSaleMethod.BY_WEIGHT -> {
                0.0
            }
            else -> {
                0.0
            }
        }
    }

    private fun getMinSizeWithDimension(dbDish: OrderRestoNomenclature): Pair<Double, String> {
        val dimension: String = dbDish.dimension.target?.name?.lowercase(Locale.ROOT) ?: String()
        return Pair(getMinSize(dbDish), dimension)
    }

    private fun getSizeWithDimension(dbDish: OrderRestoNomenclature, size: Double): Pair<Double, String> {
        val dimension: String = dbDish.dimension.target?.name?.lowercase(Locale.ROOT) ?: String()
        return Pair(size, dimension)
    }

    fun getFormattedMinWeight(dish: OrderRestoNomenclature): String {
        val weight = getMinSizeWithDimension(dish)
        val formattedWeight = DecimalFormat("#.##").format(weight.first)
        return String.format(Locale.US, "%s %s", formattedWeight, weight.second)
    }

    fun getFormattedWeight(dish: OrderRestoNomenclature, size: Double): String {
        val weight = getSizeWithDimension(dish, size)
        val formattedWeight = DecimalFormat("#.##").format(weight.first)
        return String.format(Locale.US, "%s %s", formattedWeight, weight.second)
    }

    fun getFormattedWeight(dimension: String, size: Double): String {
        val formattedWeight = DecimalFormat("#.##").format(size)
        return String.format(Locale.US, "%s %s", formattedWeight, dimension)
    }

    fun getFormattedWeightLong(context: Context, dish: OrderRestoNomenclature, size: Double): String {
        val weight: String = getFormattedWeight(dish, size)
        return String.format(Locale.US, "%s: %s", context.getString(R.string.total_weight), weight)
    }

    fun getFormattedMinWeightLong(context: Context, dish: OrderRestoNomenclature): String {
        val weight: String = getFormattedMinWeight(dish)
        return String.format(Locale.US, "%s: %s", context.getString(R.string.total_weight), weight)
    }

    fun getFormattedCookingTime(context: Context, dish: OrderRestoNomenclature): String {
        val min = dish.cookingTime!! / 1000 / 60
        return String.format(Locale.US, "%d %s", min, context.getString(R.string.minutes))
    }

    fun getFormattedCookingTimeLong(context: Context, dish: OrderRestoNomenclature): String {
        val cookingTime: String = getFormattedCookingTime(context, dish)
        return String.format(Locale.US, "%s: %s", context.getString(R.string.cooking_time), cookingTime)
    }

    fun getFormattedMinPrice(context: Context, dish: OrderRestoNomenclature): String {
        val price = getMinPrice(dish).roundToInt().toString()
        return String.format(Locale.US, "%s %s", price, context.getString(R.string.uah))
    }

    fun getFormattedPrice(context: Context, price: Double): String {
        val formattedPrice = DecimalFormat("#.##").format(price)
        return String.format(Locale.US, "%s %s", formattedPrice, context.getString(R.string.uah))
    }

    fun isDrink(dish: OrderRestoNomenclature): Boolean {
        return dish.dishTypeId != null && dish.dishTypeId == OrderRestoNomenclature.TYPE_DRINK
    }
}
