package com.example.krealogi.network.repository

import com.example.krealogi.datamodel.UserModel
import com.google.gson.annotations.SerializedName

class SearchResponse {
    @SerializedName("total_count")
    val totalCount: Int = 0

    @SerializedName("inclomplete_results")
    val incompleteResults: Boolean = false

    @SerializedName("items")
    val items: ArrayList<UserModel> = ArrayList()
}