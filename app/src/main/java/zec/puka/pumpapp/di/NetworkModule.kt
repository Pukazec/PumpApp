package zec.puka.pumpapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import zec.puka.pumpapp.api.PointsApi
import zec.puka.pumpapp.api.PumpApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


private const val POINTS_API_URL = "https://movie-646f7-default-rtdb.firebaseio.com/"

private const val PUMP_API_URL = "https://api.themoviedb.org/3/movie/"
const val PUMP_IMAGES_API_URL = "https://image.tmdb.org/t/p/original"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providePointsApi(okHttpClient: OkHttpClient) : PointsApi {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(POINTS_API_URL)
            .client(okHttpClient)
            .addConverterFactory(json
                .asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PointsApi::class.java)
    }

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun providePumpApi(okHttpClient: OkHttpClient) : PumpApi {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(PUMP_API_URL)
            .client(okHttpClient)
            .addConverterFactory(json
                .asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PumpApi::class.java)
    }

}