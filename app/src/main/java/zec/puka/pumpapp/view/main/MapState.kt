package zec.puka.pumpapp.view.main

import zec.puka.pumpapp.model.Point

data class MapState(
    val points: List<Point> = emptyList(),
    val loading: Boolean = true
)