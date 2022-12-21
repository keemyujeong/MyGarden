package com.kyjsoft.tp09plant.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.databinding.ActivityMainBinding
import com.kyjsoft.tp09plant.fragments.HomeFragment
import com.kyjsoft.tp09plant.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val fragments : MutableList<Fragment?> = mutableListOf()
    val fragmentManager : FragmentManager by lazy { supportFragmentManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragments.add(HomeFragment())
        fragments.add(null)

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragments[0]!!).commit()

        binding.bnv.setOnItemSelectedListener { menuitem ->

            val tran = fragmentManager.beginTransaction()

            if(fragments[0]!=null) tran.hide(fragments[0]!!)
            if(fragments[1]!=null) tran.hide(fragments[1]!!)

            when(menuitem.itemId){
                R.id.home -> tran.show(fragments[0]!!)
                R.id.search -> {
                    if(fragments[1] == null) {
                        fragments[1] = SearchFragment()
                        tran.add(R.id.fragment_container, fragments[1]!!)
                    }
                    tran.show(fragments[1]!!)
                }
            }
            tran.commit()
            true

        }



    }
}