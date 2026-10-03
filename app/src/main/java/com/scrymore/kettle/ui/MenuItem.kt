package com.scrymore.kettle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.scrymore.kettle.KettleColor
import com.scrymore.kettle.KettleRadius
import com.scrymore.kettle.MenuItemData
import com.scrymore.kettle.kettleType

/** One 350 x 88 dp menu row: colour tile, name and description, price. Used by the Menu screen. */
@Composable
fun MenuItem(item: MenuItemData, onClick: () -> Unit = {}) {
    val shape = RoundedCornerShape(KettleRadius.card)
    Row(
        Modifier
            .size(350.dp, 88.dp)
            .background(KettleColor.surface, shape)
            .border(1.dp, KettleColor.line, shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(56.dp).background(item.tileColor, RoundedCornerShape(KettleRadius.tile)),
            contentAlignment = Alignment.Center,
        ) { Box(Modifier.size(24.dp).background(KettleColor.glow, CircleShape)) }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            BasicText(item.itemName, style = kettleType(600, 16, 22, KettleColor.ink), maxLines = 1, overflow = TextOverflow.Ellipsis)
            BasicText(item.description, style = kettleType(400, 13, 18, KettleColor.muted), maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        BasicText(item.price, style = kettleType(600, 15, 20, KettleColor.caramel), maxLines = 1)
    }
}
