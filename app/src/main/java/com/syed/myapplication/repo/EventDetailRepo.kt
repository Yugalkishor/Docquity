package com.syed.myapplication.repo
import com.syed.myapplication.data.BaseResponseModel
import com.syed.myapplication.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventDetailRepo(private val service: EventDetailService) {
    private fun getNetworkIODispatcher() = Dispatchers.IO
    suspend fun getEventList() : Resource<ArrayList<BaseResponseModel>?> {
        return withContext(getNetworkIODispatcher()) {
            try {
                val response = service.getEventList()
                if (response.size != null) {
                    Resource.success(response)
                } else {
                    Resource.error("No Data Found", null)
                }
            } catch (e: Exception) {
                Resource.error(e.message,null)
            }
        }
    }
    suspend fun searchData(id: Int) : Resource<BaseResponseModel> {
        return withContext(getNetworkIODispatcher()) {
            try {
                val response = service.searchEVEnt(id)
                if (response != null) {
                    Resource.success(response)
                } else {
                    Resource.error("No Data Found", null)
                }
            } catch (e: Exception) {
                Resource.error(e.message,null)
            }
        }
    }
}