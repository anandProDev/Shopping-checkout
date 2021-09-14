package com.supermarket.shoppingcheckout.helper

fun getKey(tillId: String, customerId: String) =
    "${tillId.replace("\\s".toRegex(), "")}_${customerId.replace("\\s".toRegex(), "")}"