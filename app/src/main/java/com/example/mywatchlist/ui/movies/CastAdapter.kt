package com.example.mywatchlist.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.ListItemCasteBinding
import com.example.mywatchlist.network.entity.listofcast.Cast

class CastAdapter(val listOfCast: List<Cast>): RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    class CastViewHolder(binding: ListItemCasteBinding):RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.imageCast
        var textView = binding.casteName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ListItemCasteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.textView.text = listOfCast[position].name
        holder.imageView.load("https://www.themoviedb.org/t/p/w300_and_h450_bestv2"+listOfCast[position].profile_path) {
            placeholder(R.drawable.place_holder_image)
            error(R.drawable.image_load_error)
        }
    }

    override fun getItemCount(): Int {
        return listOfCast.size
    }

}