package com.example.krealogi.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.krealogi.R

open class BaseActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScreen()
        initDialog()

    }

    private fun initScreen(){
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initDialog(){
        progressDialog = ProgressDialog(this)
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun showToast(context: Context, text: String?) {
        Toast.makeText(context, text ?: "Text yang di tampilkan error", Toast.LENGTH_SHORT).show()
    }

    fun showImageFromUrlWithGlide(link: String, imageView: ImageView) {
        val option = RequestOptions()
            .fitCenter()
            .error(R.drawable.ic_launcher_background)

        Glide.with(this)
            .load(link)
            .apply(option)
            .into(imageView)
    }

    fun View.hideSoftInput() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }


}
