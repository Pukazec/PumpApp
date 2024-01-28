package zec.puka.pumpapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zec.puka.pumpapp.model.CatRemoteKeys

@Dao
interface CatRemoteKeysDao {
    @Query("SELECT * FROM cat_remote_keys_table where id=:id")
    fun getCatRemoteKeys(id: Int) : CatRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCatRemoteKeys(catRemoteKeys: List<CatRemoteKeys>)

    @Query("DELETE FROM cat_remote_keys_table")
    fun deleteCatRemoteKeys()
}