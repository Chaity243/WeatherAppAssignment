package com.msil.sharedmobility.domain.usecases.home

import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.usecases.BaseUseCase
import io.reactivex.Single

interface HomeUseCase : BaseUseCase{

    fun getAlbums(): Single<ResultState<List<Entity.Album>>>

    fun getCityList() : Single<ResultState<Entity.CommonResponse<Entity.CityResponseList>>>

}