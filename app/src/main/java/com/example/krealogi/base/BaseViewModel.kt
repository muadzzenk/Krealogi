package com.example.krealogi.base

import android.app.Activity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.krealogi.utils.SingleLiveEvent
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.java.KoinJavaComponent.inject

abstract class BaseViewModel : ViewModel(), KoinComponent, LifecycleObserver {

    protected val TAG = javaClass.simpleName

    val errorEvent = SingleLiveEvent<String>()
    val showLoadingEvent = SingleLiveEvent<Boolean>()
    val hideLoadingEvent = SingleLiveEvent<Boolean>()
    val showRefreshLoadingEvent = SingleLiveEvent<Boolean>()
    val hideRefreshLoadingEvent = SingleLiveEvent<Boolean>()

    override fun onCleared() {

        super.onCleared()
    }

}