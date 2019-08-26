package com.msil.sharedmobility.subscribe.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.msil.presentation.common.extension.observe
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.subscribe.presentation.common.SingleEvent
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragmentNew<VB : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {

    abstract fun provideViewModelClass(): Class<VM>
    abstract fun layoutId(): Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewBinding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[provideViewModelClass()]
        handleObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(layoutId(), container, false)
        viewBinding = DataBindingUtil.bind(view)!!
        viewBinding.lifecycleOwner = this
        return view
    }

    internal fun handleObserver() {
        observe(viewModel.mErrorLiveData, ::observeError)
    }

    internal fun observeError(error: SingleEvent<Entity.ErrorEntity>) {
        // handle error here
        error.getContentIfNotHandled()?.let {
            it.errorMessages?.let {

            }
        }
    }
}