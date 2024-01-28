package zec.puka.pumpapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import zec.puka.pumpapp.model.Cat

@Dao
interface CatDao {
    @Query("SELECT * FROM cat_table")
    fun getCats() : PagingSource<Int, Cat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCat(cats: List<Cat>)

    @Query("DELETE FROM cat_table")
    fun deleteCat()

    @Update
    fun update(cat: Cat)

    @Delete
    fun delete(cat: Cat)
}