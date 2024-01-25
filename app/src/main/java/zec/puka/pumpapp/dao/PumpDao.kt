package zec.puka.pumpapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import zec.puka.pumpapp.model.Pump

@Dao
interface PumpDao {
    @Query("SELECT * FROM pump_table")
    fun getPumps() : PagingSource<Int, Pump>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPump(pumps: List<Pump>)

    @Query("DELETE FROM pump_table")
    fun deletePump()

    @Update
    fun update(pump: Pump)

    @Delete
    fun delete(pump: Pump)
}