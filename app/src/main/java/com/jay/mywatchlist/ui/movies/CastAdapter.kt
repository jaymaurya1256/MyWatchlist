package com.jay.mywatchlist.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.jay.mywatchlist.R
import com.jay.mywatchlist.databinding.ListItemCastBinding
import com.jay.mywatchlist.network.entity.listofcast.Cast
import com.jay.mywatchlist.util.createCoilImageLoader

class CastAdapter(val listOfCast: List<Cast>, val context: Context): RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    class CastViewHolder(val binding: ListItemCastBinding):RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.imageCast
        var textView = binding.casteName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ListItemCastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.textView.text = listOfCast[position].name
        val imageLoader = createCoilImageLoader(context)
        holder.imageView.load("https://www.themoviedb.org/t/p/w300_and_h450_bestv2"+listOfCast[position].profile_path, imageLoader) {
            placeholder(CircularProgressDrawable(holder.binding.root.context).apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            })
            crossfade(true)
            crossfade(1000)
            error(R.drawable.ic_baseline_person_24)
        }
    }

    override fun getItemCount(): Int {
        return listOfCast.size
    }

}