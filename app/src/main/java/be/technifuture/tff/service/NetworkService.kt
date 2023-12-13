package be.technifuture.tff.service

import be.technifuture.tff.model.UserModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

object NetworkService {

    val user = UserService()
    val clan = ClanService()
    private const val URL_API = "https://tony.servegame.com/API_TFF/"

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

    @Headers("x-api-key: django-insecure-_@m)6&to2+hch54h9n@e^yy^debo0oi11%oz(n-w#lki8t#p")
    @POST("/auth/login/")
    @FormUrlEncoded
    suspend fun getLogin(
        @Field("email") fName: String,
        @Field("password") lName: String)
        : Response<UserModel?>

    @Headers("x-api-key: django-insecure-_@m)6&to2+hch54h9n@e^yy^debo0oi11%oz(n-w#lki8t#p")
    @POST("/auth/register/")
    @FormUrlEncoded
    suspend fun setRegister(
        @Field("username") login: String,
        @Field("email") mail: String,
        @Field("password") password: String)
            : Response<UserModel?>

    @Headers("Content-type: application/json")
    @GET("user&login={login}&password={password}")
    suspend fun getUser(
        @Path("login") name: String,
        @Path("password") password: String)
    : Response<UserModel?>

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