package org.example.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val lightColorScheme = lightColorScheme(
    primary = Color.Red
)

val darkColorScheme = darkColorScheme(
    primary = Color.Yellow
)

val typography = Typography(
    bodyLarge = TextStyle(
        fontSize = 22.sp
    )
)

val shapes = Shapes(
    //extraSmall = CutCornerShape(8.dp)
)

@Composable
fun CmpTheme(content: @Composable () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}