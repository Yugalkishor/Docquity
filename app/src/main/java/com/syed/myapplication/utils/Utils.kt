package com.syed.myapplication.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import com.syed.myapplication.MainActivity

object Utils {
   public var searchproduct=""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Activity.makeStatusBarTransparent() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
    fun hideSoftKeyBoard(context: MainActivity) {
        val view = context.currentFocus
        if (view != null) {
            try {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }
        }
    }
}