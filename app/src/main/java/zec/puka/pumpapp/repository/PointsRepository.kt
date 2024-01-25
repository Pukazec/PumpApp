package zec.puka.pumpapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zec.puka.pumpapp.api.PointsApi
import zec.puka.pumpapp.model.Point
import javax.inject.Inject

class PointsRepository @Inject constructor(
    private val pointsApi: PointsApi
) {
    suspend fun getPoints() : List<Point> {
        return withContext(Dispatchers.IO) {
            pointsApi.getPoints().values.toList()
        }
    }
}