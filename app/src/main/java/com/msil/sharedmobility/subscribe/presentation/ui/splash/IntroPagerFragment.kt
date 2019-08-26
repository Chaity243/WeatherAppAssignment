package com.msil.subscription.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.base.BaseFragment
import com.msil.sharedmobility.subscribe.presentation.ui.splash.IntroBanner
import kotlinx.android.synthetic.main.fragment_intro_screen.view.*


class IntroPagerFragment : Fragment() {

    private  var introBanner : IntroBanner?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            introBanner = bundle.getParcelable<IntroBanner>("intro" )
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_intro_screen, container, false)
        view.iv_intro.setImageDrawable(introBanner?.image?.let { resources.getDrawable(it) })
        view.tv_intro_title.text=introBanner?.title
        view.tv_intro_text.text=introBanner?.text
        return view

    }

    companion object {
        fun newInstance( introBanner: IntroBanner): Fragment {
            val fragment = IntroPagerFragment()
            val args = Bundle()
            args.putParcelable("intro", introBanner)
            fragment.arguments = args
            return fragment
        }}

}