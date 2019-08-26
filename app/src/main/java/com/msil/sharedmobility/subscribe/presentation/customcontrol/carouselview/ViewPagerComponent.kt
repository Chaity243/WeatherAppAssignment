package com.msil.sharedmobility.subscribe.presentation.customcontrol.carouselview;

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import androidx.viewpager.widget.ViewPager

import kotlin.math.abs


class ViewPagerComponent : ViewPager {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable


    private var imageClickListener: ImageClickListener? = null
    private var oldX = 0f
    private var newX = 0f
    private val sens = 5f

    private var mScroller: CarouselViewPagerScroller? = null

    fun setImageClickListener(imageClickListener: ImageClickListener) {
        this.imageClickListener = imageClickListener
    }

    constructor(context: Context) : super(context) {
        postInitViewPager()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        postInitViewPager()
    }

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private fun postInitViewPager() {
        try {
            val viewpager = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            val interpolator = viewpager.getDeclaredField("sInterpolator")
            interpolator.isAccessible = true

            mScroller = CarouselViewPagerScroller(
                context,
                interpolator.get(null) as Interpolator
            )
            scroller.set(this, mScroller)
        } catch (e: Exception) {
        }

    }

    /**
     * Set the factor by which the duration will change
     */
    fun setTransitionVelocity(scrollFactor: Int) {
        mScroller!!.setmScrollDuration(scrollFactor)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> oldX = ev.x

            MotionEvent.ACTION_UP -> {
                newX = ev.x
                if (abs(oldX - newX) < sens) {
                    if (imageClickListener != null)
                        imageClickListener!!.onClick(currentItem)
                    return true
                }
                oldX = 0f
                newX = 0f
            }
        }

        return super.onTouchEvent(ev)
    }


     fun pagerAutoScroller( pagerTimerInMillis:Long, onScroll : ()-> Unit) {
        // Initialize the handler instance
        mHandler = Handler()
        mRunnable = Runnable {
           /* nextItem()*/
            onScroll()

            // Schedule the task to repeat after 1 second
            mHandler.postDelayed(
                mRunnable, // Runnable
                pagerTimerInMillis // Delay in milliseconds
            )
        }

        // Schedule the task to repeat after 1 second
        mHandler.postDelayed(
            mRunnable, // Runnable
            pagerTimerInMillis // Delay in milliseconds
        )
    }


    fun stopPagerAutoScroll() {

        // Stop the periodic task
        mHandler.removeCallbacks(mRunnable)
    }
}