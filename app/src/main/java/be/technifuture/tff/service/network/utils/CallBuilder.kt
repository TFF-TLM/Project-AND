package be.technifuture.tff.service.network.utils

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

object CallBuilder {
    fun <T, K> getCall(
        call: suspend () -> Response<T>,
        catchError: Int?,
        errorType: Class<K>?,
        handler: (T?, K?, Int) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = call()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { callResponse ->
                            handler(callResponse, null, response.code())
                        }
                    } else {
                        var errorResponse: K? = null
                        catchError?.let { codeError ->
                            if (response.code() == codeError) {
                                errorResponse = Gson().fromJson(
                                    response.body().toString(),
                                    errorType
                                )
                            }
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
}