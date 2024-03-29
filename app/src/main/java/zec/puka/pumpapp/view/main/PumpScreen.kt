package zec.puka.pumpapp.view.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import zec.puka.pumpapp.R
import zec.puka.pumpapp.di.PUMP_IMAGES_API_URL
import zec.puka.pumpapp.model.Pump


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalPagingApi
@Composable
fun PumpScreen(
    modifier: Modifier = Modifier,
    pumpsState: PumpState,
    onUpdate: (Pump) -> Unit,
    onDelete: (Pump) -> Unit
) {
    val pumps = pumpsState.pumps.collectAsLazyPagingItems()

    Scaffold {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 12.dp)
        ) {
            items(
                items = pumps,
                key = { pump -> pump.id }
            ) { pump ->
                pump?.let {
                    PumpItem(
                        modifier = modifier,
                        pump = it,
                        onUpdate = onUpdate,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

@Composable
fun PumpItem(
    modifier: Modifier,
    pump: Pump,
    onUpdate: (Pump) -> Unit,
    onDelete: (Pump) -> Unit
) {
    val updateAction = SwipeAction(
        onSwipe = { onUpdate(pump) },
        background = MaterialTheme.colorScheme.secondary,
        icon = {
            Icon(
                modifier = modifier.size(150.dp),
                tint = Color.White,
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(R.string.like)
            )
        }
    )

    val deleteAction = SwipeAction(
        onSwipe = { onDelete(pump) },
        background = MaterialTheme.colorScheme.tertiary,
        icon = {
            Icon(
                modifier = modifier.size(150.dp),
                tint = Color.White,
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete)
            )
        }
    )

    SwipeableActionsBox (
        modifier = modifier.padding(vertical = 5.dp),
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surface,
        swipeThreshold = 150.dp,
        startActions = listOf(updateAction),
        endActions = listOf(deleteAction)
    ) {
        Card(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
            ) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("$PUMP_IMAGES_API_URL${pump.poster}")
                            .crossfade(enable = true)
                            .build(),
                        contentDescription = pump.title,
                        placeholder = painterResource(id = R.drawable.cmatpt),
                        contentScale = ContentScale.FillBounds,
                        modifier = modifier
                            .height(height = 450.dp)
                            .clip(shape = RoundedCornerShape(size = 20.dp))
                    )
                    Icon(
                        modifier = modifier
                            .size(size = 100.dp)
                            .align(Alignment.BottomEnd),
                        tint = MaterialTheme.colorScheme.tertiary,
                        imageVector = if(pump.liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.like)
                    )
                }
                Text(
                    modifier = modifier.padding(top = 6.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    text = "${pump.title} (${pump.date})"
                )
                Text(text = pump.overview)
            }
        }
    }
}