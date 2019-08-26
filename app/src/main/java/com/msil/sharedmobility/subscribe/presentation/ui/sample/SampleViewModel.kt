package com.msil.sharedmobility.subscribe.presentation.ui.sample

import androidx.lifecycle.MutableLiveData
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.usecases.home.HomeUseCase
import com.msil.sharedmobility.subscribe.presentation.base.BaseViewModel
import javax.inject.Inject

class SampleViewModel @Inject constructor(private val homeUseCase: HomeUseCase) : BaseViewModel() {

    private var albumLiveData: MutableLiveData<ResultState<List<Entity.Album>>> = MutableLiveData();

    fun getAlbumLiveData(): MutableLiveData<ResultState<List<Entity.Album>>> {
        return albumLiveData
    }

    fun getAlbums() {
        homeUseCase.getAlbums().toFlowable().subscribe {
            albumLiveData.postValue(it)
        }.track()
    }

}