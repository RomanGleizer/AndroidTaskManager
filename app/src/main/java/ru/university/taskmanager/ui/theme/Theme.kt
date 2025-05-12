package ru.university.taskmanager.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFF018CB4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD0EFFF),
    onPrimaryContainer = Color(0xFF001F2B),

    secondary = Color(0xFFEF7F1A),
    onSecondary = Color.White,

    background = Color(0xFFF3F3F3),
    onBackground = Color(0xFF333333),
    surface = Color.White,
    onSurface = Color(0xFF333333),

    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun TaskManagerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(
            titleLarge = Typography().titleLarge.copy(
                fontSize = 20.sp
            ),
            bodyLarge = Typography().bodyLarge.copy(
                fontSize = 16.sp
            )
        ),
        shapes = Shapes(
            small = RoundedCornerShape(0.dp),
            medium = RoundedCornerShape(4.dp),
            large = RoundedCornerShape(0.dp)
        ),
        content = content
    )
}