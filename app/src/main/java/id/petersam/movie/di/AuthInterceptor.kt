package id.petersam.movie.di

import id.petersam.movie.BuildConfig
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().addBearerAuthorization()
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

    private fun Request.Builder.addBearerAuthorization(): Request.Builder {
        val token = BuildConfig.TOKEN
        return addHeader("Authorization", "Bearer $token")
    }
}