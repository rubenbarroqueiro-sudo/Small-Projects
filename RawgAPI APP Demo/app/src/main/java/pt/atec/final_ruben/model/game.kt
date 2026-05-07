package pt.atec.final_ruben.model

import com.google.gson.annotations.SerializedName

data class Game(
    val id: Int,
    val name: String,
    val released: String?,
    val rating: Double,
    @SerializedName("background_image") val backgroundImage: String?,
    val genres: List<Genre>?,
    @SerializedName("short_screenshots") val screenshots: List<Screenshot>?
)

data class Genre(val id: Int, val name: String)
data class Screenshot(val id: Int, val image: String)

data class GameListResponse(
    val count: Int,
    val results: List<Game>
)