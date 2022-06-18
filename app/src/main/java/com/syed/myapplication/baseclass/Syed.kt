package com.syed.myapplication.baseclass

import android.app.Application
import android.content.Context
import com.syed.myapplication.network.HttpManager
import io.branch.referral.Branch

class Syed : Application() {
    var mInstance: Syed? = null
    var mContext: Context? = null

    init {
        instance = this
    }

    companion object {
        private var instance: Syed? = null

        fun applicationContext() : Context {
            return instance?.applicationContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        mContext = this
        HttpManager.initialize(applicationContext)
        // Branch logging for debugging
        Branch.enableLogging()

    }

}