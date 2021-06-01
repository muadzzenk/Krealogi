package com.example.krealogi.network.repository

import com.google.gson.annotations.SerializedName

class UserRequest : HashMap<String, Any>() {
    fun setPerPage (page: Int) {
        put("per_page", page)
    }
}