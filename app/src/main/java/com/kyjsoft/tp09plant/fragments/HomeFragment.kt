package com.kyjsoft.tp09plant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.activities.MainActivity
import com.kyjsoft.tp09plant.adapters.HomeFragmentRecyclerAdapter
import com.kyjsoft.tp09plant.databinding.FragmentHomeBinding
import com.kyjsoft.tp09plant.model.DBHelper
import com.kyjsoft.tp09plant.model.HomeFragmentRecyclerItem
import com.kyjsoft.tp09plant.model.PlantItem

class HomeFragment : Fragment() {

    val binding by lazy {FragmentHomeBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val items: MutableList<HomeFragmentRecyclerItem> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = HomeFragmentRecyclerAdapter(requireContext(), items)

        items.clear()
        binding.recycler.adapter?.notifyDataSetChanged()

        loadData()


        binding.ivAdd.setOnClickListener {
            (activity as MainActivity).binding.bnv.selectedItemId = R.id.search
        }

        binding.swipeRefresh.setOnRefreshListener {
            items.clear()
            loadData()

            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        items.clear()
        binding.recycler.adapter?.notifyDataSetChanged()

        loadData()

    }

    fun loadData(){
        var item : MutableList<PlantItem> = DBHelper(requireContext()).selectDB()
        item.let{
            it.forEach {
                items.add(HomeFragmentRecyclerItem(it.plantName, it.plantUrl))
                binding.recycler.adapter?.notifyDataSetChanged()
            }
        }
    }



}

