package com.kyjsoft.tp09plant.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.databinding.SearchfragmentRecyclerviewItemBinding
import com.kyjsoft.tp09plant.model.SearchFragmentRecyclerItem

class SearchFragmentRecyclerAdapter(val context: Context, var items: MutableList<SearchFragmentRecyclerItem>) : RecyclerView.Adapter<SearchFragmentRecyclerAdapter.VH>() {
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SearchfragmentRecyclerviewItemBinding.bind(itemView)
        init {
            itemView.setOnClickListener {
            }

        }

    }

    val imgUrl : String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(context).inflate(R.layout.searchfragment_recyclerview_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTitle.text = items[position].title
        Glide.with(context).load(items[position].imgUrl).placeholder(R.drawable.img).error(R.drawable.img).into(holder.binding.ivSearch)

    }

    override fun getItemCount(): Int {
        return items.size
    }
}