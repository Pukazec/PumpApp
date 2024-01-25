package zec.puka.pumpapp.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import zec.puka.pumpapp.R
import com.google.maps.android.compose.rememberCameraPositionState
import zec.puka.pumpapp.model.Point
import zec.puka.pumpapp.utils.bitmapDescriptorFromVector


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapState: MapState
) {

    if (mapState.loading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val points = mapState.points

        var pointSelected by remember {
            mutableStateOf(points.first())
        }

        val showDialog = remember {
            mutableStateOf(false)
        }

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pointSelected.latLng(), 13f)
        }

        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
        }

        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.HYBRID, isTrafficEnabled = true))
        }

        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            properties = properties
        ) {
            points.forEach { point ->
                Marker(
                    icon = bitmapDescriptorFromVector(
                       LocalContext.current,
                        R.drawable.location
                    ),
                    position = point.latLng(),
                    onClick = {
                        pointSelected = point
                        showDialog.value = true
                        true
                    })
            }

            if (showDialog.value) {
               PointDialog(
                    modifier,
                    pointSelected,
                    showDialog
                )
            }
        }
    }
}

@Composable
fun PointDialog(
    modifier: Modifier,
    point: Point,
    showDialog: MutableState<Boolean>
) {

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            Button(onClick = { showDialog.value = false }) {
                Text(text = stringResource(R.string.close))
            }
        },
        title = { Text(text = point.title) },
        text = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(point.image)
                    .crossfade(durationMillis = 1000)
                    .build(),
                contentDescription = point.address,
                placeholder = painterResource(id = R.drawable.bicyclist),
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .padding(top = 20.dp)
                    .size(size = 270.dp)
                    .clip(shape = CircleShape)
            )
        }

    )

}