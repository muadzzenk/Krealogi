package com.example.krealogi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.krealogi.BuildConfig
import com.example.krealogi.R
import com.example.krealogi.base.BaseActivity
import com.example.krealogi.datamodel.UserModel
import com.example.krealogi.network.database.UserTable
import com.example.krealogi.network.repository.SearchRequest
import com.example.krealogi.network.repository.UserRequest
import com.example.krealogi.utils.EndlessViewScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity() {
    private val vm: MainViewModel by inject()
    private lateinit var scrollListener: EndlessViewScrollListener
    var dataUser: ArrayList<UserModel> = ArrayList()
    var dataHistory: List<UserTable> = ArrayList()
    var adapter: UserAdapter? = null
    var adapterHistory: HistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
        initObserver()
        initRequest()
        initViewSearchUser()
    }

    private fun initViewSearchUser(){
        adapter = UserAdapter(dataUser)
        reclistSearch.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    private fun initViewHistory(){
        adapterHistory = HistoryAdapter(dataHistory)
        adapterHistory!!.setInterface(object: HistoryAdapter.HistoryAdapterInterface{
            override fun onClickHistory(query: String) {
                callSearch(query)
            }

        })
        reclistSearchHistory.adapter = adapterHistory
        adapterHistory!!.notifyDataSetChanged()
    }

    private fun initListener() {
        scrollListener = object : EndlessViewScrollListener(reclistSearch.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                requestUser(page + 1)
            }
        }
        reclistSearch.addOnScrollListener(scrollListener)

        refresh.setOnRefreshListener {
            clearData()
        }

        edtSearch.setOnClickListener {
            frameHistory.visibility = View.VISIBLE
            frameSearch.visibility = View.INVISIBLE
            edtSearchHistory.text = edtSearch.text.toString().toEditable()
            edtSearchHistory.requestFocus()
            edtSearchHistory.setSelection(edtSearchHistory.text.toString().length);
            edtSearchHistory.showKeyboard()
            vm.getSearchHistory()
        }

        edtSearchHistory.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    initViewSearchUser()
                    callSearch(edtSearchHistory.text.toString().trim())
                    true
                }
                else -> false
            }
        }

    }

    private fun callSearch (query: String) {
        edtSearch.hideSoftInput()
        frameHistory.visibility = View.INVISIBLE
        frameSearch.visibility = View.VISIBLE
        edtSearch.text = query
        requesInsertHistory(query)
        clearData()
    }

    private fun initObserver() {
        vm.userData.observe(this, Observer {
            setData(it)
        })
        vm.historyData.observe(this, Observer {
            setDataHistory(it)
        })
        vm.showRefreshLoadingEvent.observe(this, Observer {
            refresh.isRefreshing = true
        })
        vm.hideRefreshLoadingEvent.observe(this, Observer {
            refresh.isRefreshing = false
        })
        vm.errorEvent.observe(this, Observer {
            showToast(this, it)
        })
    }

    private fun initRequest() {
        requestUser(1)
    }

    private fun requestUser(page: Int) {
        val req = SearchRequest()
        if (edtSearch.text.toString().trim().isNotEmpty()) {
            req.setQuery(edtSearch.text.toString().trim())
        } else {
            req.setQuery("a")
        }
        req.setPerPage(30)
        req.setPage(page)
        vm.searchUserData(req)
    }

    private fun requesInsertHistory(query: String) {
        val req = UserTable(
                0,
                query
        )
        vm.addHistory(req)
    }

    private fun setData(data: ArrayList<UserModel>){
        dataUser.addAll(data)
        checkEmpty()
    }

    private fun setDataHistory(data: ArrayList<UserTable>){
        dataHistory = data
        checkEmptyHistory()
    }

    private fun clearData(){
        scrollListener.resetState()
        dataUser.clear()
        adapter!!.notifyDataSetChanged()
        requestUser(1)
    }

    private fun checkEmpty(){
        if (dataUser.size == 0){
            txtEmpty.visibility = View.VISIBLE
        } else{
            txtEmpty.visibility = View.GONE
        }
        adapter!!.notifyDataSetChanged()
    }

    private fun checkEmptyHistory(){
        if (dataHistory.size == 0){
            txtEmpty.visibility = View.VISIBLE
        } else{
            txtEmpty.visibility = View.GONE
        }
        initViewHistory()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (frameHistory.isVisible) {
            frameHistory.visibility = View.INVISIBLE
            frameSearch.visibility = View.VISIBLE
        } else {
            finish()
        }
    }

}