package com.example.area.data

import com.example.area.model.ServiceInfo

class ServiceDatasource {
    private var serviceList:MutableList<ServiceInfo> = ArrayList()
    fun addService(id: Int, name: String, imageUrl: String) {
        serviceList += ServiceInfo(id, name, imageUrl)
    }
    fun clear() {
        serviceList.clear()
    }
    fun loadServiceInfo(): MutableList<ServiceInfo> {
        return serviceList
    }
}