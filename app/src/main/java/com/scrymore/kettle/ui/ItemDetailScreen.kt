package com.scrymore.kettle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scrymore.kettle.Fixtures
import com.scrymore.kettle.KettleColor
import com.scrymore.kettle.KettleRadius
import com.scrymore.kettle.kettleType

@Composable
fun ItemDetailScreen(itemName: String = "Flat White", onBack: () -> Unit = {}, onAdd: () -> Unit = {}) {
    val item = Fixtures.menuItems.firstOrNull { it.itemName == itemName } ?: Fixtures.menuItems.first()
    val (line1, line2) = Fixtures.itemDetailCopy.getValue(item.itemName)
    var count by remember { mutableIntStateOf(1) }

    At(56) { BasicText("Back", style = kettleType(500, 15, 20, KettleColor.muted), modifier = Modifier.clickable(onClick = onBack)) }
    At(92) {
        Box(
            Modifier.fillMaxWidth().height(220.dp).background(item.tileColor, RoundedCornerShape(KettleRadius.hero)),
            contentAlignment = Alignment.Center,
        ) { Box(Modifier.size(96.dp).background(KettleColor.glow, CircleShape)) }
    }
    At(332) { BasicText(item.itemName, style = kettleType(700, 26, 32, KettleColor.ink)) }
    At(368) { BasicText(item.price, style = kettleType(600, 18, 24, KettleColor.caramel)) }
    At(404) {
        // Two explicit lines, so the break is the same on every device and every capture.
        Column {
            BasicText(line1, style = kettleType(400, 15, 22, KettleColor.muted))
            BasicText(line2, style = kettleType(400, 15, 22, KettleColor.muted))
        }
    }
    At(480) {
        Row(Modifier.fillMaxWidth().height(44.dp), verticalAlignment = Alignment.CenterVertically) {
            BasicText("Quantity", style = kettleType(500, 15, 20, KettleColor.ink), modifier = Modifier.weight(1f))
            QuantityStepper(count, onDecrement = { if (count > 1) count-- }, onIncrement = { count++ })
        }
    }
    At(758) { KettleButton("Add to order · ${item.price}", onClick = onAdd) }
}
