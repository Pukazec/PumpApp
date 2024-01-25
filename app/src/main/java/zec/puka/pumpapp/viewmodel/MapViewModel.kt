package zec.puka.pumpapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import zec.puka.pumpapp.repository.PointsRepository
import zec.puka.pumpapp.view.main.MapState
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: PointsRepository
) : ViewModel() {
    private val _mapState = mutableStateOf(MapState())

    val mapState: State<MapState>
        get() = _mapState

    private val errorHandler = CoroutineExceptionHandler{
            _, e -> Log.e("POINTS_VIEWMODEL", e.toString(), e)
    }

    init {
        viewModelScope.launch (errorHandler){
            _mapState.value = _mapState.value.copy(
                points = repository.getPoints(),
                loading = false
            )
        }
    }
}