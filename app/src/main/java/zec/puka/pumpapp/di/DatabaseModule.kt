package zec.puka.pumpapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zec.puka.pumpapp.db.PumpDatabase
import javax.inject.Singleton

private const val PUMP_DATABASE = "pump_database"
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : PumpDatabase {
        return Room.databaseBuilder(
            context,
            PumpDatabase::class.java,
            PUMP_DATABASE
        ).build()
    }
}