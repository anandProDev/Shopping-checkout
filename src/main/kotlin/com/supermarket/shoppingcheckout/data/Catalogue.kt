package com.supermarket.shoppingcheckout.data

import org.springframework.stereotype.Component

data class Item(val name: String, val quantity: Int)

enum class CatalogueItem(val unitPrice: Int, val unitsEligibleForOffer: Int, val offerPrice: Int) {
    A(50, 3, 130),
    B(30, 2, 45),
    C(20, 1, 20),
    D(15, 1, 15),
}

@Component
class Catalogue {
    companion object {
        private var customerShoppingCartHolder = mutableMapOf<String, HashMap<String, Int>>()
        private var updatedProductPrice = mutableMapOf<String, ProductPriceAndOfferDetails>()
    }

    fun clear(){
        customerShoppingCartHolder = mutableMapOf<String, HashMap<String, Int>>()
        updatedProductPrice = mutableMapOf<String, ProductPriceAndOfferDetails>()
    }

    fun getCustomerShoppingCart(key: String) = customerShoppingCartHolder[key]

    fun addToShoppingCart(key: String, hashMap: java.util.HashMap<String, Int>) {
        customerShoppingCartHolder[key] = hashMap
    }

    fun getUpdatedProductPrice(key: String) = updatedProductPrice[key]

    fun addUpdatedProductPrice(key: String, productPriceAndOfferDetails: ProductPriceAndOfferDetails) {
        updatedProductPrice[key] = productPriceAndOfferDetails
    }


}

data class ProductPriceAndOfferDetails(val unitPrice: Int, val unitsEligibleForOffer: Int, val offerPrice: Int)

data class Product(val name: String, val productPriceAndOfferDetails: ProductPriceAndOfferDetails)

data class ProductPriceOverride(val products: List<Product>, val priceOverride: PriceOverrideType)


enum class PriceOverrideType {
    ONLY_FOR_THIS_TRANSACTION,
    FOR_ALL_FUTURE_TRANSACTIONS
}
