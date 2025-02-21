package com.example.canpas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import com.example.canpas.ui.theme.CanpasTheme

//@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
//@Composable
//fun DrawingScreen(modifier: Modifier = Modifier.padding(top=20.dp)) {
//    val lines = remember {
//        mutableStateListOf<Line>()
//    }
//    val selectedColor = remember { mutableStateOf(Color.Black) }
//    // Holds the currently selected stroke width (as Dp).
//    val strokeWidth = remember { mutableStateOf(3.dp) }
//    // This state is used for the slider's value (as Float).
//    val strokeWidthSlider = remember { mutableStateOf(3f) }
//
//    val isEraserMode = remember { mutableStateOf(false) }
//    val backgroundColor = Color.White
//
//
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        // Top Row containing the Undo button, color options, and stroke width slider.
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            //Undo Button to remove the last drawn line.
//            Button(onClick = {
//                if (lines.isNotEmpty()) {
//                    lines.removeAt(lines.lastIndex)
//                }
//            }) {
//                Text("Undo")
//            }
//
//            // Color selection buttons.
//            Row {
//                listOf(Color.Black, Color.Red, Color.Blue, Color.Green, Color.White).forEach { color ->
//                    Box(
//                        modifier = Modifier
//                            .size(36.dp)
//                            .padding(4.dp)
//                            .background(color = color, shape = CircleShape)
//                            .border(
//                                width = 2.dp,
//                                color = if (selectedColor.value == color) Color.White else Color.Transparent,
//                                shape = CircleShape
//                            )
//                            .clickable { selectedColor.value = color }
//                    )
//                }
//            }
//
//
//            // Stroke width slider.
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//
//                Slider(
//                    value = strokeWidthSlider.value,
//                    onValueChange = {
//                        strokeWidthSlider.value = it
//                        strokeWidth.value = it.dp // update the stroke width based on slider value
//                    },
//                    valueRange = 1f..20f,
//                    modifier = Modifier.width(150.dp)
//                )
//                Text("Stroke: ${strokeWidthSlider.value.toInt()}dp")
//            }
//        }
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .pointerInput(Unit) {
//                    detectDragGestures { change, dragAmount ->
//                        change.consume()
//                        val line = Line(
//                            start = change.position - dragAmount,
//                            end = change.position,
//                            color = selectedColor.value,
//                            strokeWidth = strokeWidth.value
//                        )
//                        lines.add(line)
//                    }
//                }
//        ) {
//            lines.forEach { line ->
//                drawLine(
//                    color = line.color,
//                    start = line.start,
//                    end = line.end,
//                    strokeWidth = line.strokeWidth.toPx(),
//                    cap = StrokeCap.Round
//                )
//            }
//        }
//
//    }
//}
//
//data class Line(
//    val start: Offset,
//    val end: Offset,
//    val color: Color = Color.Black,
//    val strokeWidth: Dp = 1.dp
//)
//
//@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
//@Preview(showBackground = true)
//@Composable
//fun DrawingScreenPreview() {
//    DrawingScreen()
//
//}





@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun DrawingScreen(modifier: Modifier = Modifier.padding(top = 20.dp)) {
    val lines = remember { mutableStateListOf<Line>() }

    // Initially set to a default color
    val selectedColor = remember { mutableStateOf(Color.Black) }
    val strokeWidth = remember { mutableStateOf(3.dp) }
    val strokeWidthSlider = remember { mutableStateOf(3f) }
    val isEraserMode = remember { mutableStateOf(false) }

    // Get the theme-based colors
    val themeBackground = MaterialTheme.colorScheme.background
    val themeOnBackground = MaterialTheme.colorScheme.onBackground
    val themeOnSurface = MaterialTheme.colorScheme.onSurface

    // Update selected color when theme changes
    LaunchedEffect(themeOnBackground) {
        selectedColor.value = themeOnBackground
    }

    Surface(color = themeBackground, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Undo Button
                Button(onClick = {
                    if (lines.isNotEmpty()) {
                        lines.removeAt(lines.lastIndex)
                    }
                }) {
                    Text("Undo", color = themeOnSurface)
                }

                // Color selection buttons
                Row {
                    listOf(themeOnBackground, Color.Red, Color.Blue, Color.Green, themeBackground).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .padding(4.dp)
                                .background(color = color, shape = CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = if (selectedColor.value == color && !isEraserMode.value) themeOnSurface else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedColor.value = color
                                    isEraserMode.value = false
                                }
                        )
                    }
                }

                // Stroke width slider
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Slider(
                        value = strokeWidthSlider.value,
                        onValueChange = {
                            strokeWidthSlider.value = it
                            strokeWidth.value = it.dp
                        },
                        valueRange = 1f..20f,
                        modifier = Modifier.width(150.dp)
                    )
                    Text("Stroke: ${strokeWidthSlider.value.toInt()}dp", color = themeOnSurface)
                }

                // Eraser Button
                Button(onClick = {
                    isEraserMode.value = true
                    selectedColor.value = themeBackground
                }) {
                    Text("Eraser", color = themeOnSurface)
                }
            }

            // Drawing Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val line = Line(
                                start = change.position - dragAmount,
                                end = change.position,
                                color = selectedColor.value,
                                strokeWidth = strokeWidth.value
                            )
                            lines.add(line)
                        }
                    }
            ) {
                lines.forEach { line ->
                    drawLine(
                        color = line.color,
                        start = line.start,
                        end = line.end,
                        strokeWidth = line.strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

// Data class for each drawn line
data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 1.dp
)

// Preview
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview(showBackground = true)
@Composable
fun DrawingScreenPreview() {
    CanpasTheme {
        DrawingScreen()
    }
}
