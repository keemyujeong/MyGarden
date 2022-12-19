package com.kyjsoft.tp09plant

import android.content.Context
import java.util.ArrayList
import com.kyjsoft.tp09plant.SearchFragmentRecyclerItem
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.kyjsoft.tp09plant.R
import com.bumptech.glide.Glide
import android.widget.TextView
import android.widget.ImageView
import android.widget.EditText
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog

class SearchFragmentRecyclerAdapter     // TODO DB 만들기
    (var context: Context, var items: ArrayList<SearchFragmentRecyclerItem>) :
    RecyclerView.Adapter<SearchFragmentRecyclerAdapter.VH>() {
    var plantName: String? = null
    var imgUrl: String? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.searchfragment_recyclerview_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tvTitle.text = items[position].title
        Glide.with(context).load(items[position].imgUrl).placeholder(R.drawable.img)
            .error(R.drawable.img).into(holder.iv)
        imgUrl = items[position].imgUrl.toString()
        //        Log.i("tag", imgUrl);
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var iv: ImageView
        var etMyPlant: EditText? = null
        var tvPlantName: TextView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
            iv = itemView.findViewById(R.id.iv_search)
            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(
                    context
                )
                builder.setView(R.layout.search_add_dialog)
                val dialog = builder.create()
                //                    dialog.setCancelable(false); // 뒤로가기 눌러도 다이올로그 안 없어지게
                dialog.show()
                etMyPlant = dialog.findViewById(R.id.et_myplant)
                tvPlantName = dialog.findViewById(R.id.tv_plantname)
                tvPlantName!!.text = "식물이름 : " + tvTitle.text
                dialog.findViewById<View>(R.id.iv_detail)!!.setOnClickListener { view1: View? ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        Uri.parse("https://terms.naver.com/search.naver?query=" + tvTitle.text)
                    context.startActivity(intent)
                }
                dialog.findViewById<View>(R.id.btn_add)!!.setOnClickListener { view1: View? ->
                    plantName = etMyPlant!!.text.toString()

                    // TODO 여기서 etname 저장 하고 searchfragment에서 img를 저장. -> DB에 insert
                    // TODO 아니면 새로운 액티비티 하나 만들어서 img랑 etname까지 한번에 해서 db에 묶어 버리는 게 제일 베스트 같은데.. 아니면 dbhelper클래스 써서
                    // 하던가..
                    dialog.dismiss()
                }
            }
        }
    }
}