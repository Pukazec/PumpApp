package zec.puka.pumpapp.view.main

import androidx.paging.ExperimentalPagingApi
import zec.puka.pumpapp.viewmodel.PumpViewModel

@ExperimentalPagingApi
class PumpState (pumpViewModel: PumpViewModel){
    val pumps = pumpViewModel.pumps
}