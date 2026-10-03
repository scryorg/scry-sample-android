package com.scrymore.kettle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.scrymore.kettle.KettleColor
import com.scrymore.kettle.KettleRadius
import com.scrymore.kettle.kettleType

enum class ButtonVariant { Primary, Secondary }

/** Full-width 52 dp button. Primary is filled espresso, secondary is a surface with a 1 dp line. */
@Composable
fun KettleButton(label: String, variant: ButtonVariant = ButtonVariant.Primary, onClick: () -> Unit = {}) {
    val shape = RoundedCornerShape(KettleRadius.button)
    val primary = variant == ButtonVariant.Primary
    Box(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(if (primary) KettleColor.espresso else KettleColor.surface, shape)
            .then(if (primary) Modifier else Modifier.border(1.dp, KettleColor.line, shape))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        BasicText(
            label,
            style = kettleType(600, 16, 20, if (primary) KettleColor.white else KettleColor.ink),
            maxLines = 1,
            overflow = TextOverflow.Clip,
        )
    }
}
