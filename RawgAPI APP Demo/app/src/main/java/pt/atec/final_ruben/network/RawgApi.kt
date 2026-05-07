package pt.atec.final_ruben.network

import pt.atec.final_ruben.model.Game
import pt.atec.final_ruben.model.GameListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "3e42fe272c1a41b580ca5cd206e87734"
private const val BASE_URL = "https://api.rawg.io/api/"

interface RawgApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") key: String = API_KEY,
        @Query("page_size") pageSize: Int = 20,
        @Query("search") search: String = ""
    ): GameListResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") key: String = API_KEY
    ): Game
}

object RawgApi {
    val service: RawgApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RawgApiService::class.java)
    }
}