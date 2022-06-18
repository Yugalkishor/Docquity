package com.syed.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.syed.myapplication.data.BaseResponseModel
import com.syed.myapplication.network.CrashLogger
import com.syed.myapplication.network.Resource
import com.syed.myapplication.repo.EventDetailRepo
import com.syed.myapplication.utils.RequestType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MyEventVM(private val repo : EventDetailRepo) :ViewModel() {

    var requestType = RequestType.NORMAL
    var isEventFav: Boolean = false

    val eventDetail : MutableLiveData<ArrayList<BaseResponseModel>> by lazy {
        MutableLiveData<ArrayList<BaseResponseModel>>()
    }
    val searchData : MutableLiveData<BaseResponseModel> by lazy {
        MutableLiveData<BaseResponseModel>()
    }
    val error : MutableLiveData<String>?= null


    val showTicketLoader : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val showLoader : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isFav : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val favId : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        CrashLogger.logAndPrintException(throwable)
        handleError()
    }
    fun getEventDetail(){
        synchronized(showLoader){ showLoader.postValue(true)}
        requestType = RequestType.NORMAL
        viewModelScope.launch(exceptionHandler) {
            repo.getEventList().let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.data != null)
                            handleSuccessData(it.data)
                        else
                            handleError()
                    }
                    else -> {
                        handleError()
                    }
                }
            }
        }
    }
fun searchEvent(id :Int){
        synchronized(showLoader){ showLoader.postValue(true)}
        requestType = RequestType.NORMAL
        viewModelScope.launch(exceptionHandler) {
            repo.searchData(id).let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.data != null)
                            handleSuccessSearchData(it.data)
                        else
                            handleError()
                    }
                    else -> {
                        handleError()
                    }
                }
            }
        }
    }

    private fun handleSuccessData(data: ArrayList<BaseResponseModel>?) {
        synchronized(eventDetail) {
            eventDetail.postValue(data)
            synchronized(showLoader){ showLoader.postValue(false)}
        }
    }
    private fun handleSuccessSearchData(data: BaseResponseModel) {
        synchronized(eventDetail) {
            searchData.postValue(data)
            synchronized(showLoader){ showLoader.postValue(false)}
        }
    }

    private fun handleError(){
        synchronized(showLoader){ showLoader.postValue(false)}
        synchronized(showTicketLoader){ showTicketLoader.postValue(false)}
        error?.let {
            synchronized(it){
                error.postValue("Some Went Wrong")
            }
        }
    }

    class Factory(
        private val repository: EventDetailRepo
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MyEventVM(repository ) as T
        }
    }
}

private fun <T> MutableLiveData<T>.postValue(data: BaseResponseModel?) {

}
