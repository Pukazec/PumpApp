package zec.puka.pumpapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_remote_keys_table")
data class CatRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)