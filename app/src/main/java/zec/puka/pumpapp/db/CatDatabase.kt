package zec.puka.pumpapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import zec.puka.pumpapp.dao.CatDao
import zec.puka.pumpapp.dao.CatRemoteKeysDao
import zec.puka.pumpapp.model.Cat
import zec.puka.pumpapp.model.CatRemoteKeys

@Database(entities = [Cat::class, CatRemoteKeys::class],
    version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase(){
    abstract fun catDao(): CatDao
    abstract fun catRemoteKeysDao(): CatRemoteKeysDao
}