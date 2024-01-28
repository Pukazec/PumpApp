package zec.puka.pumpapp.view.main

import androidx.paging.ExperimentalPagingApi
import zec.puka.pumpapp.viewmodel.CatViewModel

@ExperimentalPagingApi
class CatState (catViewModel: CatViewModel){
    val cats = catViewModel.cats
}