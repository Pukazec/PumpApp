package zec.puka.pumpapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zec.puka.pumpapp.db.CatDatabase
import javax.inject.Singleton

private const val CAT_DATABASE = "cat_database"
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : CatDatabase {
        return Room.databaseBuilder(
            context,
            CatDatabase::class.java,
            CAT_DATABASE
        ).build()
    }
}