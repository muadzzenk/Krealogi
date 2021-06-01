package com.example.krealogi.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialog()
    }


    private fun initDialog(){
        progressDialog = ProgressDialog(requireContext())
    }

    fun showToast(context: Context, text: String?) {
        Toast.makeText(context, text ?: "Text yang di tampilkan error", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
//        app().disposables.clear()
        super.onDestroy()
    }
}
