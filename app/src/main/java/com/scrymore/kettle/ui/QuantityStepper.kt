package com.scrymore.kettle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.scrymore.kettle.KettleColor
import com.scrymore.kettle.KettleRadius
import com.scrymore.kettle.kettleType

/** A 128 x 44 dp stepper: minus, count, plus. The bars are drawn, not typed, so they stay crisp at any density. */
@Composable
fun QuantityStepper(count: Int, onDecrement: () -> Unit = {}, onIncrement: () -> Unit = {}) {
    val shape = RoundedCornerShape(KettleRadius.stepper)
    Row(
        Modifier
            .size(128.dp, 44.dp)
            .background(KettleColor.surface, shape)
            .border(1.dp, KettleColor.line, shape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(44.dp).semantics { contentDescription = "Decrease quantity" }.clickable(onClick = onDecrement),
        ) { Bar(15, 21, 14, 2) }
        Box(Modifier.size(40.dp, 44.dp), contentAlignment = Alignment.Center) {
            BasicText(count.toString(), style = kettleType(600, 16, 20, KettleColor.ink))
        }
        Box(
            Modifier.size(44.dp).semantics { contentDescription = "Increase quantity" }.clickable(onClick = onIncrement),
        ) {
            Bar(15, 21, 14, 2)
            Bar(21, 15, 2, 14)
        }
    }
}

@Composable
private fun Bar(x: Int, y: Int, w: Int, h: Int) {
    Box(
        Modifier
            .offset(x.dp, y.dp)
            .size(w.dp, h.dp)
            .background(KettleColor.ink, RoundedCornerShape(1.dp)),
    )
}
