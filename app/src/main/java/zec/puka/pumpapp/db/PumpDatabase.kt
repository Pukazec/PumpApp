package zec.puka.pumpapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import zec.puka.pumpapp.dao.PumpDao
import zec.puka.pumpapp.dao.PumpRemoteKeysDao
import zec.puka.pumpapp.model.Pump
import zec.puka.pumpapp.model.PumpRemoteKeys

@Database(entities = [Pump::class, PumpRemoteKeys::class],
    version = 1, exportSchema = false)
abstract class PumpDatabase : RoomDatabase(){
    abstract fun pumpDao(): PumpDao
    abstract fun pumpRemoteKeysDao(): PumpRemoteKeysDao
}