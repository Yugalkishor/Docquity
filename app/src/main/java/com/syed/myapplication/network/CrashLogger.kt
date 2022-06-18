package com.syed.myapplication.network

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import java.lang.Exception
import android.content.pm.PackageManager
import com.syed.myapplication.baseclass.Syed
import com.syed.myapplication.baseclass.Syed.Companion.applicationContext


object CrashLogger {
    fun logAndPrintException(e: Throwable) {
        Log.e("Exception Syed", e.printStackTrace().toString())

        val pm: PackageManager = applicationContext().getPackageManager()
        val applicationInfo = pm.getApplicationInfo(applicationContext().packageName, 0) }

    fun logMessage(message: String) {
        Log.e("Syed", message)
    }
}