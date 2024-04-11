package com.example.blaugranafurniture.helper

fun Float?.getProductPrice(price: Float): Float {
    //this -> percentage
    if (this == null)
        return price
    val offerPercentageDecimal = this / 100.0
    val priceAfterOffer = price - (price * offerPercentageDecimal)

    return priceAfterOffer.toFloat()
}