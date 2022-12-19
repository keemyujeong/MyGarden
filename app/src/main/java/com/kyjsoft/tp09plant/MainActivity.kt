package com.kyjsoft.tp09plant

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.ArrayList
import android.os.Bundle
import com.kyjsoft.tp09plant.R
import com.kyjsoft.tp09plant.HomeFragment
import com.google.android.material.navigation.NavigationBarView
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kyjsoft.tp09plant.SearchFragment
import com.kyjsoft.tp09plant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @JvmField
    var bnv: BottomNavigationView? = null
    var fragments = ArrayList<Fragment?>()
    var fragmentManager: FragmentManager? = null

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragments.add(null)
        fragments.add(null)

        fragmentManager = supportFragmentManager
        fragmentManager!!.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit()
        binding.bnv.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            val trans = fragmentManager!!.beginTransaction()
            if (fragments[0] != null) trans.hide(fragments[0]!!)
            if (fragments[1] != null) trans.hide(fragments[1]!!)
            when (item.itemId) {
                R.id.home -> trans.show(fragments[0]!!)
                R.id.search -> {
                    if (fragments[1] == null) {
                        fragments.add(1, SearchFragment())
                        trans.add(R.id.fragment_container, fragments[1]!!)
                    }
                    trans.show(fragments[1]!!)
                }
            }
            trans.commit()
            true
        })
    }
}