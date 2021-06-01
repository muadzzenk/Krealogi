package com.example.krealogi.network.connection

import com.example.krealogi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AuthenticationInterceptor() : Interceptor {

    private var authToken: String? = BuildConfig.ACCESS_TOKEN

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .header("Accept", "application/vnd.github.v3+json")
            .header("Authorization", authToken!!)
        val request: Request = builder.build()
        val response = chain.proceed(request)
        if (response.code != 200) {
            try {
                throw ApiException("Internal Server Error")
            } catch (e: JSONException) {
                if(BuildConfig.DEBUG){
                    e.printStackTrace()
                }
                throw ApiException("Internal Server Error")
            }
        }
        return response
    }

}