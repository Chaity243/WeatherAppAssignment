package com.msil.sharedmobility.subscribe.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.usecases.home.HomeUseCase
import com.msil.sharedmobility.subscribe.presentation.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase) : BaseViewModel() {

    private var albumLiveData: MutableLiveData<ResultState<List<Entity.Album>>> = MutableLiveData();

    private var cityListLiveData: MutableLiveData<ResultState<Entity.CommonResponse<Entity.CityResponseList>>> = MutableLiveData();

    fun getAlbumLiveData(): MutableLiveData<ResultState<List<Entity.Album>>> {
        return albumLiveData
    }

    fun getAlbums() {
        homeUseCase.getAlbums().toFlowable().subscribe {
            when (it) {
                is ResultState.Success -> {
                    albumLiveData.postValue(it)
                }
                is ResultState.Error -> {
                    handleError(it.error!!)
//                    println("Here error...")
                }
            }
        }.track()
    }
    fun getCityListLiveData(): MutableLiveData<ResultState<Entity.CommonResponse<Entity.CityResponseList>>> {
        return cityListLiveData
    }
    internal fun getCityList(){
        homeUseCase.getCityList().toFlowable().subscribe{
            cityListLiveData.postValue(it)
        }.track()
    }

}