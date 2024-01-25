package zec.puka.pumpapp.view.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zec.puka.pumpapp.R
import zec.puka.pumpapp.view.common.BlinkingText


@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(animationSpec = tween(durationMillis = 3000))
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bicyclist),
                contentDescription = stringResource(R.string.rider),
                modifier = Modifier.clipToBounds().background(Color.Gray)
            )
            BlinkingText(
                modifier = modifier.padding(bottom = 75.dp),
                text = stringResource(R.string.pumptracks_application),
                style = MaterialTheme.typography.displayMedium,
                duration = 2500
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAboutScreen() {
    AboutScreen()
}