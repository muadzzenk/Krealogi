package com.example.krealogi.network.repository

class SearchRequest : HashMap<String, Any>() {
    fun setPerPage (page: Int) {
        put("per_page", page)
    }
    fun setPage (page: Int) {
        put("page", page)
    }
    fun setQuery (query: String) {
        put("q", query)
    }
    fun setOrder (order: String) {
        put("order", order)
    }
}