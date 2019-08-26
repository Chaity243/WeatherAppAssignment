package com.msil.sharedmobility.subscribe.presentation.ui.sample


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.databinding.FragmentSampleBinding
import com.msil.sharedmobility.subscribe.presentation.base.BaseFragmentNew

class SampleFragment : BaseFragmentNew<FragmentSampleBinding, SampleViewModel>() {


    override fun provideViewModelClass(): Class<SampleViewModel> = SampleViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sample

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewBinding.vm = viewModel
    }


}
