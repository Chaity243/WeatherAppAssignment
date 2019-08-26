package com.msil.subscription.ui.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.msil.sharedmobility.subscribe.presentation.ui.splash.IntroBanner

class IntroViewPagerAdapter(fragmentManager: FragmentManager?, private val introBannersList: MutableList<IntroBanner>) :
    FragmentStatePagerAdapter(fragmentManager!!) {

    // 2
    override fun getItem(position: Int): Fragment {
        return IntroPagerFragment.newInstance(introBannersList[position])
    }

    // 3
    override fun getCount(): Int {
        return introBannersList.size
    }
}
