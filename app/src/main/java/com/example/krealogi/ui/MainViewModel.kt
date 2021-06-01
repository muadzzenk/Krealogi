package com.example.krealogi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.krealogi.base.BaseViewModel
import com.example.krealogi.datamodel.UserModel
import com.example.krealogi.network.database.UserTable
import com.example.krealogi.network.repository.SearchRequest
import com.example.krealogi.network.repository.SearchResponse
import com.example.krealogi.network.repository.UserRepository
import com.example.krealogi.network.repository.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    var userData = MutableLiveData<ArrayList<UserModel>>()
    var historyData = MutableLiveData<ArrayList<UserTable>>()

    fun getUserData(req: UserRequest) {
        viewModelScope.launch {
            try {
                var response: ArrayList<UserModel> = ArrayList()

                withContext(Dispatchers.IO) {
                    response = userRepository.getUserGithub(
                        req
                    )
                }
                userData.value = response
//                if (response!!.status_code == Constant.RESPONSE_SUCCESS) {
//                    bannerData.value = response?.results
//                } else {
//                    errorEvent.value = response!!.status_message
//                }

            } catch (e: Exception) {
                errorEvent.value = e.message
                e.printStackTrace()
            }
        }
    }

    fun searchUserData(req: SearchRequest) {
        viewModelScope.launch {
            try {
                showRefreshLoadingEvent.value = true
                var response: SearchResponse? = null

                withContext(Dispatchers.IO) {
                    response = userRepository.searchUserGithub(
                            req
                    )
                }
                userData.value = response!!.items
//                if (response!!.status_code == Constant.RESPONSE_SUCCESS) {

//                } else {
//                    errorEvent.value = response!!.status_message
//                }
                hideRefreshLoadingEvent.value = true

            } catch (e: Exception) {
                hideRefreshLoadingEvent.value = true
                errorEvent.value = e.message
                e.printStackTrace()
            }
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            try {
                showRefreshLoadingEvent.value = true
                var data: List<UserTable> = ArrayList()

                withContext(Dispatchers.IO) {
                    data = userRepository.searchHistory()
                }

                historyData.value = ArrayList(data)
                hideRefreshLoadingEvent.value = true

            } catch (e: Exception) {
                hideRefreshLoadingEvent.value = true
                errorEvent.value = e.message
                e.printStackTrace()
            }
        }

    }

    fun removeHistoryById(id: Int){
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    userRepository.deleteHistoryByID(
                            id
                    )
                }
                //remove.value = true
            } catch (e: Exception) {
                errorEvent.value = e.message
                e.printStackTrace()
            }
        }
    }

    fun addHistory(item: UserTable){
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val count = userRepository.countHistory()
                    if (count > 9) {
                        val data = userRepository.searchHistory()
                        userRepository.deleteHistoryByID(data.get(data.size-1).id)
                    }
                    userRepository.insertHistory(
                            item
                    )
                }
                //add.value = true
            } catch (e: Exception) {
                errorEvent.value = e.message
                e.printStackTrace()
            }
        }
    }

}