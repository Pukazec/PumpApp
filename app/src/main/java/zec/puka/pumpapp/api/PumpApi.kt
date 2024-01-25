package zec.puka.pumpapp.api

import retrofit2.http.GET
import retrofit2.http.Query
import zec.puka.pumpapp.model.PageResult

interface PumpApi {
    @GET("popular")
    suspend fun getPumps(
        //@Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("page") page: Int = 1
    ) : PageResult
}