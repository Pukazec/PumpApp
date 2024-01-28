package zec.puka.pumpapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageResult(
    @SerialName("results")
    val cats: List<Cat>
)