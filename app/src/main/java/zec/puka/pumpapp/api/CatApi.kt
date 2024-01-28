package zec.puka.pumpapp.api

import retrofit2.http.GET
import retrofit2.http.Query
import zec.puka.pumpapp.BuildConfig
import zec.puka.pumpapp.model.PageResult

interface CatApi {
    @GET("search")
    suspend fun getCats(
        @Query("api_key") apiKey: String = BuildConfig.CAT_KEY,
        @Query("page") page: Int = 1
    ) : PageResult
}