package be.technifuture.tff.service

import be.technifuture.tff.model.UserModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

object NetworkService {

    val user = UserService()
    val clan = ClanService()
    private const val URL_API = "https://www.thecocktaildb.com/api/json/v1/1/"

     internal fun getRetrofit(): Retrofit {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }

        return Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
    }
}

interface NetworkServiceInterface{

    @Headers("Content-type: application/json")
    @GET("random.php")
    suspend fun dataRandom(): Response<UserModel>

    @Headers("Content-type: application/json")
    @GET("search.php?s={name}")
    suspend fun dataByName(
        @Path("name") name: String) : Response<UserModel>

    @Headers("Content-type: application/json")
    @GET("list.php?c=list")
    suspend fun listOfCategory() : Response<UserModel>

    @Headers("Content-type: application/json")
    @GET("list.php?i=list")
    suspend fun listOfIngredient() : Response<UserModel>
}