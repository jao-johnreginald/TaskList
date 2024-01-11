package com.johnreg.tasklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.johnreg.tasklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.pager) { tab, _ ->
            tab.text = "Tasks"
        }.attach()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }
    }

}