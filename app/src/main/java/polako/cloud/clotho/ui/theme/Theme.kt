package polako.cloud.clotho.ui.theme

import android.os.Build
import polako.cloud.clotho.R
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme =
    darkColorScheme(
        primary = DarkBg,
        secondary = Violet,
        tertiary = Poppy,
        background = DarkBg,
        surface = Platinum,
        error = Poppy,
        inversePrimary = DartGreen,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = DarkBg,
        secondary = Violet,
        tertiary = Poppy,
        background = DarkBg,
        surface = Platinum,
        error = Poppy,
        inversePrimary = DartGreen,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
    )

val rubikFamily = FontFamily(
    Font(R.font.rubik_black),
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_light, FontWeight.Light),
    Font(R.font.rubik_medium, FontWeight.Medium),
)

val ClothoTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 48.sp,
        lineHeight = 32.sp
    ),
    displayMedium = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 32.sp,
        lineHeight = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        lineHeight = 24.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 20.sp
    )
)

@Composable
fun ClothoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ClothoTypography,
        content = content,
    )
}
