package zec.puka.pumpapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pump_remote_keys_table")
data class PumpRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)