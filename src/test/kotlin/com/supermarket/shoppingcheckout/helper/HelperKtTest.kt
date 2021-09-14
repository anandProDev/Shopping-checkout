package com.supermarket.shoppingcheckout.helper

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HelperKtTest {

    @Test
    fun `given tillId and customerId key is built`() {
        assertEquals("1_1", getKey("1", "1"))
        assertEquals("1_1", getKey("   1  ", " 1 "))
    }
}