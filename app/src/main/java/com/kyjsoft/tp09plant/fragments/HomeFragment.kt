package com.kyjsoft.tp09plant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kyjsoft.tp09plant.HomeFragmentRecyclerItem
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.activities.MainActivity
import com.kyjsoft.tp09plant.adapters.HomeFragmentRecyclerAdapter
import com.kyjsoft.tp09plant.databinding.FragmentHomeBinding

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

        binding.ivAdd.setOnClickListener {
            (activity as MainActivity).binding.bnv.selectedItemId = R.id.search
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadData()
        }


    }



}

private fun loadData(){

}