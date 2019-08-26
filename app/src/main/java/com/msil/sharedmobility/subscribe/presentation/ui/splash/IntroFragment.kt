package com.msil.subscription.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.ui.auth.AuthActivity
import com.msil.sharedmobility.subscribe.presentation.ui.splash.IntroBanner
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.android.synthetic.main.fragment_intro_screen.*


class IntroFragment : Fragment(), ViewPager.OnPageChangeListener {

    //defining Variables
    private lateinit var introViewPagerAdapter: IntroViewPagerAdapter

    private var pagerTimerInMillis: Long = 5000

    private var introBannersList = mutableListOf<IntroBanner>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_skip.setOnClickListener(View.OnClickListener {

            iv_intro.stopNestedScroll()
            findNavController().navigate(R.id.nav_to_auth_activity)
            activity?.finish()
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createBannerList()
        vp_intro.pagerAutoScroller(pagerTimerInMillis) { nextItem() }
        iv_right_click.setOnClickListener {
            vp_intro.stopPagerAutoScroll()
            nextItem()
        }

        iv_left_click.setOnClickListener {
            vp_intro.stopPagerAutoScroll()
            previousItem()
        }
        btn_nxt.setOnClickListener {
            findNavController().navigate(R.id.nav_to_auth_activity)
//            startActivity(Intent(activity, AuthActivity::class.java))
            activity?.finish()
        }
        vp_intro.addOnPageChangeListener(this)
    }

    private fun previousItem() {
        vp_intro.arrowScroll(View.FOCUS_LEFT)
        checkCurrentIndex()
    }

    private fun pagerFirstItemUI() {
        /* iv_left_click.visibility= View.INVISIBLE*/
        btn_nxt.visibility = View.INVISIBLE
        tv_skip.visibility = View.VISIBLE
    }

    private fun pagerDefaultUI() {
        /*  iv_right_click.visibility=View.VISIBLE
          iv_left_click.visibility= View.VISIBLE
          tv_skip.visibility=View.VISIBLE
          btn_nxt.isEnabled=false*/
        tv_skip.visibility = View.VISIBLE
        btn_nxt.visibility = View.INVISIBLE
    }

    private fun nextItem() {
        vp_intro.arrowScroll(View.FOCUS_RIGHT)
        checkCurrentIndex()

    }

    private fun checkCurrentIndex() = when {
        vp_intro.currentItem == 0 -> pagerFirstItemUI()
        vp_intro.currentItem == introBannersList.size - 1 -> pagerLastItemUI()
        else -> pagerDefaultUI()
    }

    private fun pagerLastItemUI() {
        /*   iv_right_click.visibility=View.INVISIBLE
           iv_left_click.visibility= View.VISIBLE*/
        tv_skip.visibility = View.INVISIBLE
        btn_nxt.visibility = View.VISIBLE
    }


    private fun createBannerList() {

        val introImages = listOf(R.drawable.shape_circle_gray, R.drawable.shape_circle_gray, R.drawable.shape_circle_gray)
        val introTitles = resources.getStringArray(R.array.splash_intro_title)
        val introTexts = resources.getStringArray(R.array.splash_intro_text)

        for (i in 0 until introImages.size) {
            introBannersList.add(IntroBanner(introImages[i], introTitles[i], introTexts[i]))
        }

        initPager()
    }

    private fun initPager() {
        introViewPagerAdapter = IntroViewPagerAdapter(fragmentManager, introBannersList)
        vp_intro.adapter = introViewPagerAdapter
        vp_intro.setCurrentItem(0, true)
        initCircularIndicator()

    }

    private fun initCircularIndicator() {
        intro_indicator.setViewPager(vp_intro)
    }


    override fun onPageScrollStateChanged(state: Int) {


    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position == introBannersList.size - 1) {
            vp_intro.stopPagerAutoScroll()
        }

        checkCurrentIndex()
    }

    override fun onPageSelected(position: Int) {


    }

    override fun onStop() {
        super.onStop()
        vp_intro.stopPagerAutoScroll()
    }

}
