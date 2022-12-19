package com.kyjsoft.tp09plant.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyjsoft.tp09plant.HomeFragmentRecyclerItem
import com.kyjsoft.tp09plant.databinding.HomefragmentRecyclerviewItemBinding

class HomeFragmentRecyclerAdapter(val context: Context?, var items : MutableList<HomeFragmentRecyclerItem>) : RecyclerView.Adapter<com.kyjsoft.tp09plant.adapters.HomeFragmentRecyclerAdapter.VH>(){


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = HomefragmentRecyclerviewItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}