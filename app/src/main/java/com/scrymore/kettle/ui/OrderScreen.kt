package com.scrymore.kettle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scrymore.kettle.Fixtures
import com.scrymore.kettle.KettleColor
import com.scrymore.kettle.KettleRadius
import com.scrymore.kettle.OrderLine
import com.scrymore.kettle.OrderTotal
import com.scrymore.kettle.kettleType

@Composable
fun OrderScreen(
    lines: List<OrderLine> = Fixtures.orderLines,
    totals: List<OrderTotal> = Fixtures.orderTotals,
    onAddMore: () -> Unit = {},
    onPlace: () -> Unit = {},
) {
    val shape = RoundedCornerShape(KettleRadius.card)
    At(56) { BasicText("Your order", style = kettleType(700, 28, 34, KettleColor.ink)) }
    At(94) { BasicText("Pickup at Kettle on 5th St · ready in 8 min", style = kettleType(400, 15, 20, KettleColor.muted)) }
    At(138) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(KettleColor.surface, shape)
                .border(1.dp, KettleColor.line, shape)
                .padding(horizontal = 16.dp),
        ) {
            lines.forEachIndexed { i, line ->
                if (i > 0) Box(Modifier.fillMaxWidth().height(1.dp).background(KettleColor.line))
                Row(Modifier.fillMaxWidth().height(56.dp), verticalAlignment = Alignment.CenterVertically) {
                    Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        BasicText(line.name, style = kettleType(600, 16, 22, KettleColor.ink))
                        BasicText(line.quantity, style = kettleType(400, 15, 20, KettleColor.muted))
                    }
                    BasicText(line.price, style = kettleType(600, 15, 20, KettleColor.ink))
                }
            }
        }
    }
    At(275) {
        Column {
            totals.forEach { t ->
                val style = if (t.strong) kettleType(700, 17, 22, KettleColor.ink) else kettleType(400, 15, 20, KettleColor.muted)
                Row(Modifier.fillMaxWidth().height(28.dp), verticalAlignment = Alignment.CenterVertically) {
                    BasicText(t.label, style = style, modifier = Modifier.weight(1f))
                    BasicText(t.value, style = style)
                }
            }
        }
    }
    At(694) { KettleButton("Add more", ButtonVariant.Secondary, onClick = onAddMore) }
    At(758) { KettleButton("Place order", onClick = onPlace) }
}
