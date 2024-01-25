package zec.puka.pumpapp.view.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun AnimatedIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        onClick = onClick,
        colors = colors,
        enabled = enabled,
        modifier = modifier,
        interactionSource = interactionSource
    ) {
        AnimatedVisibility(visible = isPressed) {
            Row {
                icon()
                Spacer(modifier = modifier.size(ButtonDefaults.IconSpacing))
            }
        }
        text()
    }
}