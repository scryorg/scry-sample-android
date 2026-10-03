package com.scrymore.kettle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.scrymore.kettle.KettleCanvas
import com.scrymore.kettle.KettleColor

/**
 * The fixed 390 x 844 dp canvas every screen is laid out on, centred under the status bar on
 * whatever device runs it. Normal launches and captures both use it, so they match.
 */
@Composable
fun KettleCanvasBox(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize().background(KettleColor.bg)) {
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .size(KettleCanvas.width, KettleCanvas.height)
                .background(KettleColor.bg)
                .clipToBounds(),
        ) { content() }
    }
}

/** Places a block at an exact y from the top of the canvas, inside the 20 dp side padding (content width 350). */
@Composable
fun At(y: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier.offset(x = 20.dp, y = y.dp).width(350.dp)) { content() }
}
