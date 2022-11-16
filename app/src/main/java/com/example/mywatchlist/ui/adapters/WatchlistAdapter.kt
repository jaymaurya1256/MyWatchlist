package com.example.mywatchlist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywatchlist.R

class WatchlistAdapter: RecyclerView.Adapter<WatchlistAdapter.ItemViewHolderWatchlist>() {
    class ItemViewHolderWatchlist(itemView: ViewGroup): RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderWatchlist {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.list_item_watchlist, parent,false)
        return ItemViewHolderWatchlist(holder as ViewGroup)
    }

    override fun onBindViewHolder(holder: ItemViewHolderWatchlist, position: Int) {
    }

    override fun getItemCount(): Int {
        return 30
    }

}