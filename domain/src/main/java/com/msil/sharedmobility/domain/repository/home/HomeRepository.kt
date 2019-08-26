package com.msil.sharedmobility.domain.repository.home

import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.repository.BaseRepository
import io.reactivex.Single

interface HomeRepository : BaseRepository{

    fun getAlbums(): Single<ResultState<List<Entity.Album>>>

    fun getCityList():Single<ResultState<Entity.CommonResponse<Entity.CityResponseList>>>
}