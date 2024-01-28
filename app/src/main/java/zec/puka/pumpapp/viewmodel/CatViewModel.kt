package zec.puka.pumpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zec.puka.pumpapp.model.Cat
import zec.puka.pumpapp.repository.CatRepository
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel(){
    val cats = repository.getCats()

    fun update(cat: Cat) {
        viewModelScope.launch (Dispatchers.IO){
            repository.update(cat)
        }
    }

    fun delete(cat: Cat) {
        viewModelScope.launch (Dispatchers.IO){
            repository.delete(cat)
        }
    }
}