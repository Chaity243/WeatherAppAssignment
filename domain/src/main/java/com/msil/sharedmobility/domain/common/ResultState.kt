package com.msil.sharedmobility.domain.common

import com.msil.sharedmobility.domain.entity.Entity

/**
 * Created by Shivang Goel on 08/08/2019.
 */
/**
 * A wrapper for database and network states.
 */
sealed class ResultState<T> {

    /**
     * A state of [data] which shows that we know there is still an update to come.
     */
    data class Loading<T>(val data: T) : ResultState<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * A state to show a [throwable] is thrown.
     */
    data class Error<T>(val throwable: Throwable, val error: Entity.ErrorEntity?) : ResultState<T>()
}