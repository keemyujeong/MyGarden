package com.kyjsoft.tp09plant.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.activities.ModifyPlantActivity
import com.kyjsoft.tp09plant.databinding.HomefragmentRecyclerviewItemBinding
import com.kyjsoft.tp09plant.model.HomeFragmentRecyclerItem

class HomeFragmentRecyclerAdapter(val context: Context, var items : MutableList<HomeFragmentRecyclerItem>) : RecyclerView.Adapter<com.kyjsoft.tp09plant.adapters.HomeFragmentRecyclerAdapter.VH>(){

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = HomefragmentRecyclerviewItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, ModifyPlantActivity::class.java)
                intent.putExtra("plantImg", items[adapterPosition].imgUrl)
                intent.putExtra("plantName", items[adapterPosition].myplantname)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView = LayoutInflater.from(context).inflate(R.layout.homefragment_recyclerview_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvMyplant.text = items[position].myplantname
        Glide.with(context).load(items[position].imgUrl).into(holder.binding.ivFavoritePlant)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}