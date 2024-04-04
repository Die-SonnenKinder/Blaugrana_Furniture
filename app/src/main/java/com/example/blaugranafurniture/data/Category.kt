package com.example.blaugranafurniture.data

sealed class Category(val category: String) {

    object Accessory: Category ("Accessory")
    object Bathroom: Category ("Bathroom")
    object Chair: Category ("Chair")
    object Cupboard: Category ("Cupboard")
    object Furniture: Category ("Furniture")
    object Table: Category ("Table")
}