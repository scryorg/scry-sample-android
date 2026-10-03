package com.scrymore.kettle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Design tokens from scryorg/scry-sample-rn (src/tokens.ts), which come from the Kettle spec.
// Kept numerically identical so a device capture lines up with the Figma file in Scry's diff.

object KettleColor {
    val bg = Color(0xFFFBF7F2)
    val surface = Color(0xFFFFFFFF)
    val ink = Color(0xFF2B1D14)
    val muted = Color(0xFF7A6A5E)
    val line = Color(0xFFEADFD3)
    val espresso = Color(0xFF3B2A20)
    val caramel = Color(0xFFB8621B)
    val tileFlatWhite = Color(0xFFC89F7A)
    val tileColdBrew = Color(0xFF5A3B2A)
    val tileMatcha = Color(0xFF8FA876)
    val tileCortado = Color(0xFFA9744F)
    val white = Color(0xFFFFFFFF)
    /** White at 35% opacity: the circle drawn on tiles and the hero. */
    val glow = Color(0x59FFFFFF)
}

object KettleRadius {
    val card: Dp = 16.dp
    val tile: Dp = 12.dp
    val hero: Dp = 20.dp
    val button: Dp = 14.dp
    val stepper: Dp = 22.dp
}

/** The size every Kettle screen is laid out on, in dp (same as the React Native sample). */
object KettleCanvas {
    val width: Dp = 390.dp
    val height: Dp = 844.dp
}

// Inter is bundled in res/font, so the capture never depends on a system font.
private val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

/** Inter at a weight, size and line height, letter spacing 0. */
fun kettleType(weight: Int, size: Int, lineHeight: Int, color: Color): TextStyle = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight(weight),
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    letterSpacing = 0.sp,
    color = color,
)
