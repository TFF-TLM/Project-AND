package be.technifuture.tff.service.network.manager

import android.view.View
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.network.service.AuthApiServiceImpl
import be.technifuture.tff.service.network.service.UserApiServiceImpl
import be.technifuture.tff.utils.sharedPref.SharedPrefManager
import be.technifuture.tff.service.network.dto.Auth
import be.technifuture.tff.service.network.dto.ErrorDetailsResponse
import be.technifuture.tff.service.network.dto.ErrorRegisterResponse
import be.technifuture.tff.service.network.dto.Register
import be.technifuture.tff.service.network.dto.UserDataRequestBody
import be.technifuture.tff.service.network.dto.UserDataResponse
import be.technifuture.tff.service.network.utils.CallBuilder
import java.util.Date


class AuthDataManager {
    private val sharedPrefManager = SharedPrefManager.instance
    private val authService = AuthApiServiceImpl.instance
    private val userService = UserApiServiceImpl.instance
    private val timeToReconnect = (60 * 60 * 24 * 7)

    var accessToken: String? = null
    lateinit var user: UserModel

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

    fun getUserDetailsById(
        id: Int,
        handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { userService.userDetails(id) },
            404,
            ErrorDetailsResponse::class.java
        ) { callResponse, error, code ->
            callResponse?.toUserModel()?.let {
                user = it
            }
            handler(user, error, code)
        }
    }

    fun login(
        auth: Auth,
        remember: Boolean,
        handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { authService.login(auth) },
            401,
            ErrorDetailsResponse::class.java
        ) { authResponse, errorAuth, codeAuth ->
            errorAuth?.let { error ->
                handler(null, error, codeAuth)
            }
            authResponse?.let { auth ->
                saveRefreshToken(auth.refresh)
                if (remember) {
                    saveUserId(auth.user.id)
                    updateExpirationTime()
                } else {
                    deleteUserId()
                    deleteExpirationTime()
                }
                accessToken = auth.access
                GameDataManager.instance.refreshDataGameFromUser(auth.user.id) { user, _, _, _, errorUser, codeUser, _, _, _ ->
                    handler(user, errorUser, codeUser)
                }
            }
        }
    }

    fun isAlreadyConnected(
        loader: View? = null,
        handler: (user: UserModel?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        val userId = getUserId()
        val timestamp = getExpirationTime()
        val refreshToken = getRefreshToken()
        if (timestamp > Date().time && userId != -1 && refreshToken != null) {
            loader?.visibility = View.VISIBLE
            GameDataManager.instance.refreshDataGameFromUser(userId) { user, _, _, _, errorUser, codeUser, _, _, _ ->
                loader?.visibility = View.GONE
                handler(user, errorUser, codeUser)
            }
        }
    }

    fun isAvailableAndValid(
        register: Register,
        handler: (available: Boolean?, error: ErrorRegisterResponse?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { authService.verification(register) },
            400,
            ErrorRegisterResponse::class.java
        ) { callResponse, error, code ->
            handler(callResponse?.available, error, code)
        }
    }

    private fun createUserData(
        userData: UserDataRequestBody,
        handler: (userData: UserDataResponse?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { userService.createUserData(userData) },
            null,
            Nothing::class.java
        ) { callResponse, _, code ->
            handler(callResponse, code)
        }
    }

    fun register(
        register: Register,
        userData: UserDataRequestBody,
        handler: (
            user: UserModel?,
            errorRegister: ErrorRegisterResponse?,
            errorDetails: ErrorDetailsResponse?,
            codeRegister: Int,
            codeUserData: Int?,
            codeDetails: Int?
        ) -> Unit
    ) {
        CallBuilder.getCall(
            { authService.register(register) },
            400,
            ErrorRegisterResponse::class.java
        ) { registerResponse, errorRegister, codeRegister ->
            registerResponse?.let { auth ->
                saveRefreshToken(auth.refresh)
                createUserData(userData) { userDataResponse, userDataCode ->
                    userDataResponse?.let {
                        GameDataManager.instance.refreshDataGameFromUser(auth.user.id) { user, _, _, _, errorUser, codeUser, _, _, _ ->
                            handler(user, null, errorUser, codeRegister, userDataCode, codeUser)
                        }
                    } ?: run {
                        handler(
                            user,
                            null,
                            null,
                            codeRegister,
                            userDataCode,
                            null
                        )
                    }
                }
            }
            errorRegister?.let {
                handler(user, errorRegister, null, codeRegister, null, null)
            }
        }
    }
}