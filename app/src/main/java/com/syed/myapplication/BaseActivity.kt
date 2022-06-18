package com.syed.myapplication

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager.BadTokenException
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.syed.myapplication.utils.Utils.makeStatusBarTransparent


open class BaseActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            makeStatusBarTransparent()
        }
    }

    protected open fun getStringFromResource(id: Int): String? {
        return resources.getString(id)
    }

    protected open fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    open fun createProgressDialog(context: Context?, msg: String?) {
        dialog = ProgressDialog(context)
        try {
            dialog?.show()
        } catch (ignored: BadTokenException) {
        }
        dialog?.setCancelable(false)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(R.layout.progress_dialog)
        if (msg != null) dialog?.setMessage(msg)
    }

    open fun dismissDialog() {
        if (!isFinishing) {
            dialog?.dismiss()
        }
    }
}