package zec.puka.pumpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zec.puka.pumpapp.model.Pump
import zec.puka.pumpapp.repository.PumpRepository
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class PumpViewModel @Inject constructor(
    private val repository: PumpRepository
) : ViewModel(){
    val pumps = repository.getPumps()

    fun update(pump: Pump) {
        viewModelScope.launch (Dispatchers.IO){
            repository.update(pump)
        }
    }

    fun delete(pump: Pump) {
        viewModelScope.launch (Dispatchers.IO){
            repository.delete(pump)
        }
    }
}