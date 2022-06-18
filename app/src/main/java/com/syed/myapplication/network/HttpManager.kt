package com.syed.myapplication.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpManager {

    lateinit var normalClient: OkHttpClient
    private var appContext: Context? = null
    private val logging = HttpLoggingInterceptor()

    private const val TIMEOUT_IN_SEC = 30
    private val CONNECT_TIMEOUT_IN_SEC = TIMEOUT_IN_SEC
    private val READ_TIMEOUT_IN_SEC = TIMEOUT_IN_SEC
    private val WRITE_TIMEOUT_IN_SEC = TIMEOUT_IN_SEC

    internal fun initialize(context: Context) {
        appContext = context
        var normalBuilder = OkHttpClient.Builder()
        normalBuilder.connectTimeout(CONNECT_TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
        normalBuilder.readTimeout(READ_TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
        normalBuilder.writeTimeout(WRITE_TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
        normalClient = normalBuilder.build()

    }


    @JvmStatic
    fun setHTTPHeaders(builder: Request.Builder): Request.Builder {
        //all header should be here
        builder.header("X_ACCESS_TOKEN", "")
        return builder
    }
}