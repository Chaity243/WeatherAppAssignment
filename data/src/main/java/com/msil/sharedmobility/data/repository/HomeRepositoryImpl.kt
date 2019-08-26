package com.msil.sharedmobility.data.repository

import com.msil.shadata.common.extension.applyIoScheduler
import com.msil.sharedmobility.data.mapper.map
import com.msil.sharedmobility.data.remote.api.IHomeApi
import com.msil.sharedmobility.data.remote.dto.ErrorDto
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.repository.home.HomeRepository
import io.reactivex.Single
import retrofit2.HttpException

class HomeRepositoryImpl(val api: IHomeApi) : BaseRepositoryImpl(), HomeRepository {
    override fun getCityList(): Single<ResultState<Entity.CommonResponse<Entity.CityResponseList>>> {
        return api.getCityList().applyIoScheduler()
            .map {
                ResultState.Success(it.map()) as ResultState<Entity.CommonResponse<Entity.CityResponseList>>
            }.onErrorReturn {
                handleErrorReturn(it) as ResultState<Entity.CommonResponse<Entity.CityResponseList>>
            }
    }


    override fun getAlbums(): Single<ResultState<List<Entity.Album>>> {

        return api.getAlbums(1, 20)
            .applyIoScheduler()
            .map {
                /*d ->
                if (d.size > 0)*/
                ResultState.Success(it.map { it -> it.map() }) as ResultState<List<Entity.Album>>
                /*else
                    ResultState.Loading(d) as ResultState<List<Entity.Album>>*/
            }
            .onErrorReturn {
                //                    e -> ResultState.Error(e, "")
                handleErrorReturn(it) as ResultState<List<Entity.Album>>

            }
    }

}

