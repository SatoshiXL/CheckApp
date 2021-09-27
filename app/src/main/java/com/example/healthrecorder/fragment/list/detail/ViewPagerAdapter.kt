package com.example.healthrecorder.fragment.list.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    val array: ArrayList<Fragment>,
    val manager: FragmentManager,
    val context: Context,
    lifecycle: Lifecycle
) : FragmentStateAdapter(manager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return array[position]
    }

    override fun getItemCount(): Int {
        return array.size
    }

}