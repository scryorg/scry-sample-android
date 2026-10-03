package com.scrymore.kettle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.scrymore.kettle.ui.ItemDetailScreen
import com.scrymore.kettle.ui.KettleCanvasBox
import com.scrymore.kettle.ui.MenuScreen
import com.scrymore.kettle.ui.OrderScreen

private enum class Route { Menu, Detail, Order }

/** The normal app: Menu, tap a drink for its detail, "View order" for the order. No network, no accounts. */
@Composable
fun KettleApp() {
    var route by remember { mutableStateOf(Route.Menu) }
    var item by remember { mutableStateOf("Flat White") }
    KettleCanvasBox {
        when (route) {
            Route.Menu -> MenuScreen(onItem = { item = it.itemName; route = Route.Detail }, onViewOrder = { route = Route.Order })
            Route.Detail -> ItemDetailScreen(item, onBack = { route = Route.Menu }, onAdd = { route = Route.Menu })
            Route.Order -> OrderScreen(onAddMore = { route = Route.Menu }, onPlace = { route = Route.Menu })
        }
    }
}
