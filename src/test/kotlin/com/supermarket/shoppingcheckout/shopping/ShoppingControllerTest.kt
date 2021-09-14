package com.supermarket.shoppingcheckout.shopping

import com.supermarket.shoppingcheckout.data.Catalogue
import com.supermarket.shoppingcheckout.data.Item
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested

internal class ShoppingControllerTest {

    val catalogue = Catalogue()
    val shoppingController = ShoppingController(catalogue)

    @AfterEach
    fun tearDown() {
        catalogue.clear()
    }

    @Nested
    inner class `given a customer 1 shoppin in till number 1`{

        @Test
        fun `when he shops 1 item of A, 1 item of B and 1 item of A, then ends up with 2 items of A and 1 item of B`(){
            shoppingController.addItemsToShoppingCart("1", "1", Item("A", 1))
            shoppingController.addItemsToShoppingCart("1", "1", Item("B", 1))
            shoppingController.addItemsToShoppingCart("1", "1", Item("A", 1))

            val allItemsOfCustomer = shoppingController.getAllItemsOfCustomer("1", "1")

            assertTrue(allItemsOfCustomer.isNotEmpty())
            assertEquals(listOf(Item("A", 2), Item("B", 1)), allItemsOfCustomer)
        }
    }

    @Nested
    inner class `given a customer 2 shopping in till number 1`{
        @Test
        fun `when he shops 1 item of A, 1 item of B and returns 1 item of A, then ends up with 1 item of B`() {
            shoppingController.addItemsToShoppingCart("1", "2", Item("A", 1))
            shoppingController.addItemsToShoppingCart("1", "2", Item("B", 1))
            shoppingController.remoteItemsFromShoppingCart("1", "2", Item("A", 1))

            val allItemsOfCustomer = shoppingController.getAllItemsOfCustomer("1", "2")

            assertTrue(allItemsOfCustomer.isNotEmpty())
            assertEquals(listOf(Item("B", 1)), allItemsOfCustomer)
        }
    }

}
