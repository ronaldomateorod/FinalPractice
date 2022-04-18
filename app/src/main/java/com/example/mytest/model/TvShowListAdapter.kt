package com.example.mytest.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.R
import com.example.mytest.databinding.TvShowListItemBinding
import com.example.mytest.viewmodel.EpisoDateViewModel
import com.squareup.picasso.Picasso

class TvShowListAdapter(val tvShows: List<TvShow>, val viewModel: EpisoDateViewModel) : RecyclerView.Adapter<TvShowListAdapter.ViewHolder>() {

    public class ViewHolder(binding: TvShowListItemBinding): RecyclerView.ViewHolder(binding.root){
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TvShowListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tvShow = tvShows[position]

        var picasso = Picasso.get().load(tvShow.image)
            .placeholder(R.drawable.pop_corn)
            .centerCrop()
            .resize(80 * 3, 100 * 3)
            .into(holder.binding.tvShowPoster)



        holder.binding.tvShowName.text = tvShow.name
        holder.binding.tvShowNetwork.text = tvShow.network
        holder.binding.tvShowStatus.text = tvShow.status
        holder.binding.tvShowStartDate.text = tvShow.startDate
        holder.binding.root.setOnClickListener {
            viewModel.setSelectedItem(tvShow)
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
}