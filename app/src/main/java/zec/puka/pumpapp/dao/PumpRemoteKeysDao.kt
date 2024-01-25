package zec.puka.pumpapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zec.puka.pumpapp.model.PumpRemoteKeys

@Dao
interface PumpRemoteKeysDao {
    @Query("SELECT * FROM pump_remote_keys_table where id=:id")
    fun getPumpRemoteKeys(id: Int) : PumpRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPumpRemoteKeys(pumpRemoteKeys: List<PumpRemoteKeys>)

    @Query("DELETE FROM pump_remote_keys_table")
    fun deletePumpRemoteKeys()
}