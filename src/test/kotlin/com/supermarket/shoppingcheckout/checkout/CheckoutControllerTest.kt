package com.supermarket.shoppingcheckout.checkout

import com.supermarket.shoppingcheckout.data.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class CheckoutControllerTest {

    val catalogue = Catalogue()
    val checkoutController = CheckoutController(catalogue)
    val product = Product("A", ProductPriceAndOfferDetails(15, 2, 10))
    val productPriceOverrideForThisTransaction = ProductPriceOverride(listOf(product), PriceOverrideType.ONLY_FOR_THIS_TRANSACTION)
    val productPriceOverrideForAllTransactions = ProductPriceOverride(listOf(product), PriceOverrideType.FOR_ALL_FUTURE_TRANSACTIONS)

    @Test
    fun `Given a shopping cart of the same items ,When checkout is invoked from till 1 and customer 1 then product price is calculated with offer`(){

        catalogue.addToShoppingCart("1_1", hashMapOf("A" to 5))

        val total = checkoutController.checkout("1", "1", null)

        assertEquals(230, total)
    }

    @Test
    fun `when getPriceAndOfferDetails is called and no price override or updated product price is available, then default price is used`() {
        val priceAndOfferDetails = checkoutController.getPriceAndOfferDetails(CatalogueItem.B, null)

        assertEquals(CatalogueItem.B.offerPrice, priceAndOfferDetails.offerPrice)
        assertEquals(CatalogueItem.B.unitPrice, priceAndOfferDetails.unitPrice)
        assertEquals(CatalogueItem.B.unitsEligibleForOffer, priceAndOfferDetails.unitsEligibleForOffer)
    }

    @Test
    fun `given updated product price and no price override, then updated product price is used`(){

        catalogue.addUpdatedProductPrice("C", ProductPriceAndOfferDetails(50, 2, 60))

        val priceAndOfferDetails = checkoutController.getPriceAndOfferDetails(CatalogueItem.C, null)

        assertEquals(60, priceAndOfferDetails.offerPrice)
        assertEquals(50, priceAndOfferDetails.unitPrice)
        assertEquals(2, priceAndOfferDetails.unitsEligibleForOffer)
    }

    @Nested
    inner class `Given price override is available ONLY_FOR_THIS_TRANSACTION` {

        @Test
        fun `when getPriceAndOfferDetails is invoked, then price override is used`() {
            val priceAndOfferDetails = checkoutController.getPriceAndOfferDetails(CatalogueItem.A, productPriceOverrideForThisTransaction)

            assertEquals(10, priceAndOfferDetails.offerPrice)
            assertEquals(15, priceAndOfferDetails.unitPrice)
            assertEquals(2, priceAndOfferDetails.unitsEligibleForOffer)
        }
    }

    @Nested
    inner class `Given price override is available ONLY_FOR_All_TRANSACTION` {

        @Test
        fun `when getPriceAndOfferDetails is invoked, then price override is used for all future transactions`() {
            val priceAndOfferDetails = checkoutController.getPriceAndOfferDetails(CatalogueItem.A, productPriceOverrideForAllTransactions)

            assertEquals(10, priceAndOfferDetails.offerPrice)
            assertEquals(15, priceAndOfferDetails.unitPrice)
            assertEquals(2, priceAndOfferDetails.unitsEligibleForOffer)
        }
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "5, 10, 3, 25, 45",
            "1, 100, 2, 150, 100",
            "2, 100, 2, 150, 150",
            "3, 100, 2, 150, 250",
        ]
    )
    fun `When calculatePrice is called, Then offer price is calculated and total is returned`(
        quantity: Int,
        unitPrice: Int,
        unitsEligibleForOffer: Int,
        offerPrice: Int,
        expectedTotalPrice: Int
    ) {
        val actualTotalPrice = checkoutController.calculatePrice(quantity, ProductPriceAndOfferDetails(unitPrice, unitsEligibleForOffer, offerPrice))
        assertEquals(expectedTotalPrice , actualTotalPrice)
    }

    @AfterEach
    fun cleanUp(){
        catalogue.clear()
    }

}
