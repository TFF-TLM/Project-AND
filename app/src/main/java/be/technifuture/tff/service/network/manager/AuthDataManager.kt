package be.technifuture.tff.service.network.manager

import android.view.View
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.network.service.AuthApiServiceImpl
import be.technifuture.tff.service.network.service.UserApiServiceImpl
import be.technifuture.tff.utils.sharedPref.SharedPrefManager
import be.technifuture.tff.service.network.dto.Auth
import be.technifuture.tff.service.network.dto.ErrorDetailsResponse
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

    private fun getUserId(): Int {
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

    private fun deleteUserId() {
        with(sharedPrefManager.editor) {
            remove(SharedPrefManager.KeyPref.USER_ID.value)
            apply()
        }
    }

    private fun getExpirationTime(): Long {
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

    private fun deleteExpirationTime() {
        with(sharedPrefManager.editor) {
            remove(SharedPrefManager.KeyPref.EXPIRATION_TIME.value)
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
                            user = userResponse.toUserModel()
                            userResponse.data?.clan?.let { clanResponse ->
                                clan = clanResponse.toClanModel()
                            }
                            handler(user, null, response.code())
                        }
                    } else {
                        var errorResponse: ErrorDetailsResponse? = null
                        if (response.code() == 404) {
                            errorResponse = Gson().fromJson(
                                response.body().toString(),
                                ErrorDetailsResponse::class.java
                            )
                        }
                        handler(null, errorResponse, response.code())
                    }
                } catch (e: HttpException) {
                    handler(null, null, response.code())
                    print(e)
                } catch (e: Throwable) {
                    handler(null, null, response.code())
                    print(e)
                }
            }
        }
    }

    fun login(
        auth: Auth,
        remember: Boolean,
        handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authService.login(auth)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { authResponse ->
                            saveRefreshToken(authResponse.refresh)
                            if (remember) {
                                saveUserId(authResponse.user.id)
                                updateExpirationTime()
                            } else {
                                deleteUserId()
                                deleteExpirationTime()
                            }
                            accessToken = authResponse.access
                            getUserDetailsById(authResponse.user.id) { user, error, code ->
                                    handler(user, error, code)
                            }
                        }
                    } else {
                        var errorResponse: ErrorDetailsResponse? = null
                        if (response.code() == 401) {
                            errorResponse = Gson().fromJson(
                                response.body().toString(),
                                ErrorDetailsResponse::class.java
                            )
                        }
                        handler(null, errorResponse, response.code())
                    }
                } catch (e: HttpException) {
                    handler(null, null, response.code())
                    print(e)
                } catch (e: Throwable) {
                    handler(null, null, response.code())
                    print(e)
                }
            }
        }
    }

    fun isAlreadyConnected(loader: View? = null, handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit) {
        val userId = getUserId()
        val timestamp = getExpirationTime()
        val refreshToken = getRefreshToken()

        if (timestamp > Date().time && userId != -1 && refreshToken != null) {
            loader?.visibility = View.VISIBLE
            getUserDetailsById(userId) { user, error, code ->
                loader?.visibility = View.GONE
                handler(user, error, code)
            }
        }
    }

}