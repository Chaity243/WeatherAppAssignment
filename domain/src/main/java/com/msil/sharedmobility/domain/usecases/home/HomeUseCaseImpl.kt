package com.msil.sharedmobility.domain.usecases.home

import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.repository.home.HomeRepository
import io.reactivex.Single

class HomeUseCaseImpl(private val homeRepository: HomeRepository) : HomeUseCase {
    override fun getCityList(): Single<ResultState<Entity.CommonResponse<Entity.CityResponseList>>> {
        return homeRepository.getCityList()
    }

    override fun getAlbums(): Single<ResultState<List<Entity.Album>>> {
       return homeRepository.getAlbums()
    }

}