package com.example.krealogi.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.krealogi.R
import com.example.krealogi.datamodel.UserModel
import com.example.krealogi.network.database.UserTable
import kotlinx.android.synthetic.main.layout_list_history.view.*
import kotlinx.android.synthetic.main.layout_list_user.view.*
import kotlinx.android.synthetic.main.layout_list_user.view.imgUser
import kotlinx.android.synthetic.main.layout_list_user.view.txtUser

class HistoryAdapter(private val list: List<UserTable>):
        RecyclerView.Adapter<HistoryAdapter.ItemViewHolder>() {

    private lateinit var context: Context
    private var listener: HistoryAdapterInterface? = null

    fun setInterface(callback: HistoryAdapterInterface) {
        listener = callback
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_history, parent, false)
        context = parent.context
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = list[position]
        //showImageFromUrlWithGlide(data.avatarUrl, holder.ivImage)
        holder.txtUser.text = data.name
        holder.layMain.setOnClickListener {
            listener!!.onClickHistory(data.name)
        }
    }

    private fun showImageFromUrlWithGlide(link: String, imageView: ImageView) {
        val option = RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_launcher_background)

        Glide.with(context)
                .load(link)
                .apply(option)
                .into(imageView)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var ivImage = itemView.imgUser
        internal var txtUser = itemView.txtUser
        internal var layMain = itemView.layMain
    }

    interface HistoryAdapterInterface {
        fun onClickHistory(query: String)
    }

}
