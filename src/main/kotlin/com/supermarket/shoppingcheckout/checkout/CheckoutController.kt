package com.supermarket.shoppingcheckout.checkout

import com.supermarket.shoppingcheckout.data.*
import com.supermarket.shoppingcheckout.helper.getKey
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/checkout")
class CheckoutController(val checkoutService: CheckoutService) {

    @PostMapping("/{tillId}/{customerId}")
    fun checkout(@PathVariable("tillId") tillId: String, @PathVariable ("customerId") customerId: String,
                                  @RequestBody productPriceOverride: ProductPriceOverride?): Int {
        val key = getKey(tillId, customerId)

        return checkoutService.getTotalPrice(key, productPriceOverride)
    }
}
