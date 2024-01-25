package zec.puka.pumpapp.view.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import zec.puka.pumpapp.R


@Composable
fun BlinkingText(
    text: String,
    style: TextStyle,
    duration: Int,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    val infiniteTransition = rememberInfiniteTransition(label = stringResource(R.string.alpha))
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(id = R.string.alpha)
    )
    Text(
        modifier = modifier.alpha(alpha),
        text = text,
        style = style,
        textAlign = textAlign
    )
}