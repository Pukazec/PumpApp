package zec.puka.pumpapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity(tableName = "cat_table")
@Serializable
data class Cat(
    @PrimaryKey(autoGenerate = true)
    @Transient
    val catId: Int = 0,

    val id: String,
    val url: String = "",
    @Transient
    var liked: Boolean = false

)