package ke.don.faah.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil

fun java.awt.Color.toComposeColor(): Color {
    return Color(red, green, blue, alpha)
}

@Composable
fun FaahTheme(content: @Composable () -> Unit) {
    var isDarkTheme by remember { mutableStateOf(!JBColor.isBright()) }

    DisposableEffect(Unit) {
        val connection = ApplicationManager.getApplication().messageBus.connect()
        connection.subscribe(LafManagerListener.TOPIC, LafManagerListener {
            isDarkTheme = !JBColor.isBright()
        })
        onDispose {
            connection.disconnect()
        }
    }

    val background = JBColor.PanelBackground.toComposeColor()
    val surface = JBColor.PanelBackground.toComposeColor()
    val onBackground = UIUtil.getLabelForeground().toComposeColor()
    val onSurface = UIUtil.getLabelForeground().toComposeColor()

    val colors = if (isDarkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC6),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            surface = surface,
            onBackground = onBackground,
            onSurface = onSurface
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC6),
            surface = surface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = onBackground,
            onSurface = onSurface
        )
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}