package com.scrymore.kettle.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.scrymore.kettle.Fixtures
import com.scrymore.kettle.KettleColor
import com.scrymore.kettle.MenuItemData
import com.scrymore.kettle.kettleType

@Composable
fun MenuScreen(
    items: List<MenuItemData> = Fixtures.menuItems,
    onItem: (MenuItemData) -> Unit = {},
    onViewOrder: () -> Unit = {},
) {
    At(56) { BasicText("Menu", style = kettleType(700, 28, 34, KettleColor.ink)) }
    At(94) { BasicText("Order ahead, skip the line", style = kettleType(400, 15, 20, KettleColor.muted)) }
    At(138) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items.forEach { MenuItem(it) { onItem(it) } }
        }
    }
    At(758) { KettleButton("View order · 2 items", onClick = onViewOrder) }
}
