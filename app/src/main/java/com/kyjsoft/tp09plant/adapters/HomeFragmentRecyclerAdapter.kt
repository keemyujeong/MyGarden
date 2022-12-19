package com.kyjsoft.tp09plant.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.databinding.HomefragmentRecyclerviewItemBinding
import com.kyjsoft.tp09plant.model.HomeFragmentRecyclerItem

class HomeFragmentRecyclerAdapter(val context: Context?, var items : MutableList<HomeFragmentRecyclerItem>) : RecyclerView.Adapter<com.kyjsoft.tp09plant.adapters.HomeFragmentRecyclerAdapter.VH>(){


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = HomefragmentRecyclerviewItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView = LayoutInflater.from(context).inflate(R.layout.homefragment_recyclerview_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }


}