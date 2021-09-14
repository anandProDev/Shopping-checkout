package com.supermarket.shoppingcheckout.checkout

import com.supermarket.shoppingcheckout.data.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


internal class CheckoutControllerTest {

    val catalogue = Catalogue()
    val service = CheckoutService(catalogue)
    val checkoutController = CheckoutController(service)
    val product = Product("A", ProductPriceAndOfferDetails(15, 2, 10))

    @Test
    fun `Given a shopping cart of the same items, When checkout is invoked from till 1 and customer 1 then product price is calculated with offer`(){

        catalogue.addToShoppingCart("1_1", hashMapOf("A" to 5))

        val total = checkoutController.checkout("1", "1", null)

        assertEquals(230, total)
    }

    @AfterEach
    fun cleanUp(){
        catalogue.clear()
    }

}
