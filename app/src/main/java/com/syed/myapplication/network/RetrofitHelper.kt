package com.syed.myapplication.network

import android.content.Intent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.syed.myapplication.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private val gson: Gson =
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().setLenient().create()

    @JvmStatic
    fun <S> createRetrofitService(serviceClass: Class<S>): S {
        return createService(serviceClass)
    }

    private fun <S> createService(serviceClass: Class<S>): S {
        return RetrofitObj.client.create(serviceClass)
    }

    private object RetrofitObj {

        val client: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(
                HttpManager.normalClient.newBuilder()
                    .callTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(UnauthorizedInterceptor())
                   /* .addInterceptor(BasicAuthInterceptor("3xaUser!@3#", "9raPass@3!)#@done"))*/
                    .addInterceptor(headersInterceptor)
                    .build()
            )
            .build()
    }

    internal class UnauthorizedInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            return response
        }
    }


    private val headersInterceptor: Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val builder: Request.Builder = HttpManager.setHTTPHeaders(original.newBuilder())
        val request: Request = builder.method(original.method, (original.body)).build()
        chain.proceed(request)
    }


    internal class BasicAuthInterceptor(user: String, password: String) : Interceptor {
        private val credentials: String = Credentials.basic(user, password)

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
            return chain.proceed(authenticatedRequest)
        }

    }

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

}


