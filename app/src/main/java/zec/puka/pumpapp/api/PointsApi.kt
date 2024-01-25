package zec.puka.pumpapp.api

import retrofit2.http.GET
import zec.puka.pumpapp.model.Point


interface PointsApi {
    @GET("/points.json")
    suspend fun getPoints() : Map<String, Point>
}