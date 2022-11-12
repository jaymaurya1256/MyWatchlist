package com.example.mywatchlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywatchlist.R

class ItemAdapterForWatchlist: RecyclerView.Adapter<ItemAdapterForWatchlist.ItemViewHolderWatchlist>() {
    class ItemViewHolderWatchlist(itemView: ViewGroup): RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderWatchlist {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolderWatchlist(holder as ViewGroup)
    }

    override fun onBindViewHolder(holder: ItemViewHolderWatchlist, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 30
    }

}