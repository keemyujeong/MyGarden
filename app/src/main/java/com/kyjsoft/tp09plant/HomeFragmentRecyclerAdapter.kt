package com.kyjsoft.tp09plant

import android.content.Context
import java.util.ArrayList
import com.kyjsoft.tp09plant.HomeFragmentRecyclerItem
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.kyjsoft.tp09plant.R
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView
import com.kyjsoft.tp09plant.MainActivity

class HomeFragmentRecyclerAdapter(
    var context: Context,
    var items: ArrayList<HomeFragmentRecyclerItem>
) : RecyclerView.Adapter<HomeFragmentRecyclerAdapter.VH>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val inflater = LayoutInflater.from(context)
        val itemview =
            inflater.inflate(R.layout.homefragment_recyclerview_item, parent, false)
        return VH(itemview)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(items[position].imgUrl).placeholder(R.drawable.img)
            .error(R.drawable.img).into(holder.iv)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv: ImageView
        var tv: TextView

        init {
            tv = itemView.findViewById(R.id.tv_myplant)
            iv = itemView.findViewById(R.id.iv_favorite_plant)
            itemView.setOnClickListener { view: View? ->
                (context as MainActivity).bnv.selectedItemId =
                    R.id.search // MainActivity의 능력을 가진 context로 fragment를 넘기기
            }
        }
    }
}