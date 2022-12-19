package com.kyjsoft.tp09plant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kyjsoft.tp09plant.adapters.SearchFragmentRecyclerAdapter
import com.kyjsoft.tp09plant.databinding.FragmentSearchBinding
import com.kyjsoft.tp09plant.model.SearchFragmentRecyclerItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.URL

class SearchFragment : Fragment() {

    val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    var items : MutableList<SearchFragmentRecyclerItem> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = SearchFragmentRecyclerAdapter(requireContext(), items)
        binding.recyclerSearchBtn.setOnClickListener {
            loadData(binding.recyclerSearchEt.text.toString())
        }

    }

    private fun loadData(input : String){
        val apikey =
            "4wlFPuoi69Pc78kZpfF7GpieaLhqRkeSrKZs9jk5ZqbKSSh4vfl2VXk36YbHSOSipfsuVFbBZk9wVLg%2BubgvHw%3D%3D"
        val address = "http://openapi.nature.go.kr/openapi/service/rest/PlantService/plntIlstrSearch" +
                    "?serviceKey=" + apikey +
                    "&st=1" +
                    "&sw=" + input +
                    "&numOfRows=100" +
                    "&pageNo=1"

        object : Thread(){
            override fun run() {

                requireActivity().runOnUiThread {
                    items.clear()
                    binding.recycler.adapter!!.notifyDataSetChanged()
                }

                val url = URL(address)
                var xpp = XmlPullParserFactory.newInstance().newPullParser()
                xpp.setInput(InputStreamReader(url.openStream()))

                var eventType = xpp.eventType
                var item :MutableMap<String, String> = mutableMapOf()
                while(eventType != XmlPullParser.END_DOCUMENT){
                    when(eventType){
                        XmlPullParser.START_DOCUMENT -> {
                        }
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name
                            if (tagName == "item"){
                            }
                            else if(tagName == "plantGnrlNm") {
                                xpp.next()
                                item.put("title", xpp.text)
                            } else if(tagName == "imgUrl"){
                                xpp.next()
                                item.put("imgUrl", xpp.text)
                            }
                        }
                        XmlPullParser.TEXT -> {}
                        XmlPullParser.END_TAG -> if(xpp.name == "item"){
                            items.add(SearchFragmentRecyclerItem(item["title"], item["imgUrl"]))
                        }
                    }
                    eventType = xpp.next()
                }

                requireActivity().runOnUiThread {
                    binding.recycler.adapter?.notifyDataSetChanged()
                }


            }
        }.start()

    }
}