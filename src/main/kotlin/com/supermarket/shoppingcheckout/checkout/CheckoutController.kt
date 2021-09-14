package com.supermarket.shoppingcheckout.checkout


import com.supermarket.shoppingcheckout.data.*
import com.supermarket.shoppingcheckout.helper.getKey
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/checkout")
class CheckoutController(val catalogue: Catalogue) {

    @PostMapping("/{tillId}/{customerId}")
    fun checkout(@PathVariable("tillId") tillId: String, @PathVariable ("customerId") customerId: String,
                                  @RequestBody productPriceOverride: ProductPriceOverride?): Int {
        val key = getKey(tillId, customerId)
        var totalPrice = 0

        catalogue.getCustomerShoppingCart(key)?.forEach { (itemName, quantity) ->
            val productPriceAndOfferDetails: ProductPriceAndOfferDetails = getPriceAndOfferDetails(
                CatalogueItem.valueOf(itemName),
                productPriceOverride)

            totalPrice += calculatePrice(quantity, productPriceAndOfferDetails);
        }
        return totalPrice
    }

    @GetMapping("/s")
    fun checkoutPrice(): ProductPriceOverride {

        return ProductPriceOverride(
            listOf(Product("A", ProductPriceAndOfferDetails(1, 2, 10)),
                Product("B", ProductPriceAndOfferDetails(10, 2, 10))),
            PriceOverrideType.FOR_ALL_FUTURE_TRANSACTIONS
        )
    }

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

}
