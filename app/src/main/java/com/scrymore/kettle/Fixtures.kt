package com.scrymore.kettle

import androidx.compose.ui.graphics.Color

// Fixed data, the same four menu items and copy as scryorg/scry-sample-rn. Screens read from here,
// so a capture is identical every run: no clock, no network, no randomness.

data class MenuItemData(
    val itemName: String,
    val description: String,
    val price: String,
    val tileColor: Color,
)

data class OrderLine(val name: String, val quantity: String, val price: String)

data class OrderTotal(val label: String, val value: String, val strong: Boolean)

object Fixtures {
    val menuItems = listOf(
        MenuItemData("Flat White", "Double ristretto, silky milk", "$4.50", KettleColor.tileFlatWhite),
        MenuItemData("Cold Brew", "Steeped 18 hours, over ice", "$4.75", KettleColor.tileColdBrew),
        MenuItemData("Matcha Latte", "Ceremonial grade, oat milk", "$5.25", KettleColor.tileMatcha),
        MenuItemData("Cortado", "Equal parts espresso and milk", "$4.00", KettleColor.tileCortado),
    )

    /** Two explicit lines of detail copy per item, so the break is the same on every capture. */
    val itemDetailCopy = mapOf(
        "Flat White" to ("A double ristretto with steamed whole milk," to "poured thin so the coffee still leads."),
        "Cold Brew" to ("Coarse-ground and steeped in cold water" to "for 18 hours, then served straight over ice."),
        "Matcha Latte" to ("Ceremonial-grade matcha whisked smooth," to "poured over oat milk, lightly sweetened."),
        "Cortado" to ("Equal parts espresso and steamed milk," to "cut just enough to soften the shot."),
    )

    val orderLines = listOf(
        OrderLine("Flat White", "×1", "$4.50"),
        OrderLine("Cold Brew", "×1", "$4.75"),
    )

    val orderTotals = listOf(
        OrderTotal("Subtotal", "$9.25", strong = false),
        OrderTotal("Tax", "$0.76", strong = false),
        OrderTotal("Total", "$10.01", strong = true),
    )
}
