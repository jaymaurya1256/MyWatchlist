package com.example.mywatchlist.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.example.mywatchlist.R
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.databinding.ListItemWatchlistBinding
import com.example.mywatchlist.ui.Actions
import com.example.mywatchlist.ui.movies.BASE_URL_FOR_IMAGE

class WatchlistAdapter(private val watchListTables: List<WatchlistTable>, private val onClick: (Int, Actions) -> Unit): RecyclerView.Adapter<WatchlistAdapter.ItemViewHolderWatchlist>() {
    class ItemViewHolderWatchlist(val binding: ListItemWatchlistBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.movieNameWatchlist
        val detail = binding.descriptionWatchlist
        val image = binding.imageWatchlist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderWatchlist {
        val binding = ListItemWatchlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ItemViewHolderWatchlist(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolderWatchlist, position: Int) {
        val movie =  watchListTables[position]
        with(holder.binding){
            movieNameWatchlist.text = movie.title
            descriptionWatchlist.text = movie.description
            if (movie.isAdult) {
                audienceRating.text = "A"
            }
            try {
                imageWatchlist.load(BASE_URL_FOR_IMAGE +movie.image){
                    placeholder(CircularProgressDrawable(root.context).apply {
                        strokeWidth = 5f
                        centerRadius = 30f
                        start()
                    })
                    crossfade(true)
                    crossfade(1000)
                }
            }catch (e: Exception){
                imageWatchlist.load(R.drawable.image_load_error)
            }
        }
        holder.binding.root.setOnLongClickListener {
            val popupMenu = PopupMenu(holder.binding.root.context, holder.binding.root)
            popupMenu.menuInflater.inflate(R.menu.menu_watch_list, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.removeFromListMenuItem -> {
                        onClick(movie.id, Actions.REMOVE)
                        true
                    }
                    R.id.goToDescriptionFromWatchlistMenuItem -> {
                        onClick(movie.id, Actions.GO_TO_DESCRIPTION)
                        true
                    }
                    else -> true
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return watchListTables.size
    }

}