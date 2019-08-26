package com.msil.sharedmobility.subscribe.presentation.base

//import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.subscribe.presentation.common.SingleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
//import org.reactivestreams.Publisher

open class BaseViewModel : ViewModel() {

    private val mDisposables = CompositeDisposable()

    private var errorLiveData = MediatorLiveData<SingleEvent<Entity.ErrorEntity>>()

    val mErrorLiveData: LiveData<SingleEvent<Entity.ErrorEntity>>
        get() = errorLiveData


    protected fun Disposable.track() {
        mDisposables.add(this)
    }

    override fun onCleared() {
        mDisposables.clear()
        super.onCleared()
    }

    /* protected fun <T> MediatorLiveData<T>.add(publisher: Publisher<T>) {
         addSource(LiveDataReactiveStreams.fromPublisher(publisher)) {
             postValue(it)
         }
     }*/

    protected fun handleError(error:Entity.ErrorEntity) {
        errorLiveData.postValue(SingleEvent(error))
    }
}