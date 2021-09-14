package com.supermarket.shoppingcheckout.checkout

import com.supermarket.shoppingcheckout.data.Catalogue
import com.supermarket.shoppingcheckout.data.CatalogueItem
import com.supermarket.shoppingcheckout.data.PriceOverrideType
import com.supermarket.shoppingcheckout.data.ProductPriceAndOfferDetails
import com.supermarket.shoppingcheckout.data.ProductPriceOverride
import org.springframework.stereotype.Service

@Service
class CheckoutService(val catalogue: Catalogue) {

    fun getPriceAndOfferDetails(catalogueItem: CatalogueItem, productPriceOverride: ProductPriceOverride?): ProductPriceAndOfferDetails {

        val product = productPriceOverride?.products?.firstOrNull { it.name == catalogueItem.name }
        val priceOverrideType = productPriceOverride?.priceOverride

        if(product != null) {
            return if(priceOverrideType == PriceOverrideType.ONLY_FOR_THIS_TRANSACTION) {
                product.productPriceAndOfferDetails
            } else {

                catalogue.addUpdatedProductPrice(catalogueItem.name, product.productPriceAndOfferDetails)
                product.productPriceAndOfferDetails
            }
        }

        return catalogue.getUpdatedProductPrice(catalogueItem.name)
            ?: ProductPriceAndOfferDetails(
                catalogueItem.unitPrice,
                catalogueItem.unitsEligibleForOffer,
                catalogueItem.offerPrice
            )
    }

    fun calculatePrice(quantity: Int, productPriceAndOfferDetails: ProductPriceAndOfferDetails) : Int{
        val offerPrice = (quantity / productPriceAndOfferDetails.unitsEligibleForOffer) * productPriceAndOfferDetails.offerPrice
        val unitPrice = (quantity % productPriceAndOfferDetails.unitsEligibleForOffer) * productPriceAndOfferDetails.unitPrice
        return offerPrice + unitPrice
    }

    fun getTotalPrice(key: String, productPriceOverride: ProductPriceOverride?): Int {

        var totalPrice = 0

        catalogue.getCustomerShoppingCart(key)?.forEach { (itemName, quantity) ->
            val productPriceAndOfferDetails: ProductPriceAndOfferDetails = getPriceAndOfferDetails(
                CatalogueItem.valueOf(itemName),
                productPriceOverride)

            totalPrice += calculatePrice(quantity, productPriceAndOfferDetails);
        }
        return totalPrice
    }
}
