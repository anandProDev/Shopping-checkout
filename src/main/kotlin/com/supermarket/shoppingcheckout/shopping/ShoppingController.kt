package com.supermarket.shoppingcheckout.shopping


import com.supermarket.shoppingcheckout.data.Catalogue
import com.supermarket.shoppingcheckout.data.Item
import com.supermarket.shoppingcheckout.helper.getKey
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/shopping")
class ShoppingController(val catalogue: Catalogue) {

    @GetMapping("/items/{tillId}/{customerId}")
    fun getAllItemsOfCustomer(@PathVariable("tillId") tillId: String, @PathVariable ("customerId") customerId: String): List<Item> {

        val key = getKey(tillId, customerId)

        val toList: List<Pair<String, Int>> = catalogue.getCustomerShoppingCart(key)?.toList().orEmpty()

        return toList.map { Item( it.first, it.second) }.filter { it.quantity > 0}
    }

    @PostMapping("/items/add/{tillId}/{customerId}")
    fun addItemsToShoppingCart(@PathVariable("tillId") tillId: String, @PathVariable ("customerId") customerId: String, @RequestBody item: Item): List<Item> {
        val key = getKey(tillId, customerId)

        val map: HashMap<String, Int>? = catalogue.getCustomerShoppingCart(key)
        if (map.isNullOrEmpty()) {
            catalogue.addToShoppingCart(key, mutableMapOf(item.name to item.quantity) as HashMap<String, Int>)
            return catalogue.getCustomerShoppingCart(key)?.toList()?.map { Item(it.first, it.second) } ?: emptyList()
        }
        map[item.name] = (map[item.name] ?: 0).toInt() + item.quantity
        return map.toList().map { Item(it.first, it.second) }
    }

    @PutMapping("/items/remove/{tillId}/{customerId}")
    fun remoteItemsFromShoppingCart(@PathVariable("tillId") tillId: String, @PathVariable ("customerId") customerId: String, @RequestBody item: Item): List<Item> {
        val key = getKey(tillId, customerId)
        val customersItems: HashMap<String, Int>? = catalogue.getCustomerShoppingCart(key)
        if (customersItems.isNullOrEmpty()) {
            return customersItems?.toList()?.map { Item(it.first, it.second) } ?: emptyList()
        }
        customersItems[item.name] = (customersItems[item.name] ?: 0).toInt() - item.quantity
        return customersItems.toList().map { Item(it.first, it.second) }
    }

    @PostMapping("/clear")
    fun clearAllData(){
        Catalogue().clear()
    }
}
