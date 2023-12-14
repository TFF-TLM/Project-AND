package be.technifuture.tff.service.network.manager

import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.network.service.AuthApiServiceImpl
import be.technifuture.tff.service.network.service.UserApiServiceImpl
import be.technifuture.tff.utils.sharedPref.SharedPrefManager
import be.technifuture.tff.service.network.dto.Auth
import be.technifuture.tff.service.network.dto.ErrorDetailsResponse
import be.technifuture.tff.service.network.utils.ClanBuilder
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Date


class AuthDataManager {
    private val sharedPrefManager = SharedPrefManager.instance
    private val authService = AuthApiServiceImpl.instance
    private val userService = UserApiServiceImpl.instance
    private val timeToReconnect = (60 * 60 * 24 * 7)

    var accessToken: String? = null
    var user: UserModel? = null
    var clan: ClanModel? = null

    companion object {
        val instance: AuthDataManager by lazy {
            AuthDataManager()
        }
    }

    fun getRefreshToken(): String? {
        return sharedPrefManager.sharedPref.getString(
            SharedPrefManager.KeyPref.REFRESH_TOKEN.value,
            null
        )
    }

    private fun saveRefreshToken(token: String) {
        with(sharedPrefManager.editor) {
            putString(SharedPrefManager.KeyPref.REFRESH_TOKEN.value, token)
            apply()
        }
    }

    fun getUserId(): Int {
        return sharedPrefManager.sharedPref.getInt(
            SharedPrefManager.KeyPref.USER_ID.value,
            -1
        )
    }

    private fun saveUserId(id: Int) {
        with(sharedPrefManager.editor) {
            putInt(SharedPrefManager.KeyPref.USER_ID.value, id)
            apply()
        }
    }

    fun getExpirationTime(): Long {
        return sharedPrefManager.sharedPref.getLong(
            SharedPrefManager.KeyPref.EXPIRATION_TIME.value,
            -0
        )
    }

    private fun updateExpirationTime() {
        with(sharedPrefManager.editor) {
            putLong(SharedPrefManager.KeyPref.EXPIRATION_TIME.value, Date().time + timeToReconnect)
            apply()
        }
    }

    private fun getUserDetailsById(
        id: Int,
        handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = userService.userDetails(id)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { userResponse ->
                            userResponse.data?.let { userData ->
                                user = UserModel(
                                    userResponse.id,
                                    userResponse.username,
                                    "",
                                    userData.image,
                                    userData.clan.id,
                                    userData.lvl,
                                    userData.expLimit,
                                    userData.exp,
                                    userData.food,
                                    userData.foodLimit
                                )
                                clan = ClanBuilder.buildClan(userData.clan.id, userData.clan.name)
                            }
                            handler(user, null, response.code())
                        }
                    }
                } catch (e: HttpException) {
                    if (response.code() == 404) {
                        val errorResponse = Gson().fromJson(
                            response.errorBody().toString(),
                            ErrorDetailsResponse::class.java
                        )
                        handler(null, errorResponse, response.code())
                    }
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }

    fun login(
        auth: Auth,
        handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authService.login(auth)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { authResponse ->
                            saveRefreshToken(authResponse.refresh)
                            saveUserId(authResponse.user.id)
                            updateExpirationTime()
                            accessToken = authResponse.access
                            getUserDetailsById(authResponse.user.id) { user, error, code ->
                                handler(user, error, code)
                            }
                        }
                    }
                } catch (e: HttpException) {
                    if (response.code() == 401) {
                        val errorResponse = Gson().fromJson(
                            response.errorBody().toString(),
                            ErrorDetailsResponse::class.java
                        )
                        handler(null, errorResponse, response.code())
                    }
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }

    fun isAlreadyConnected(handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit) {
        val userId = getUserId()
        val timestamp = getExpirationTime()

        if (timestamp < Date().time && userId != -1) {
            getUserDetailsById(userId) { user, error, code ->
                handler(user, error, code)
            }
        }
    }

}