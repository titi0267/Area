package com.example.area.data

import android.graphics.Bitmap
import com.example.area.model.ServiceListElement

class ServiceDatasource {
    private var serviceList:MutableList<ServiceListElement> = mutableListOf<ServiceListElement>()

    fun addService(id: Int, name: String, imageBitmap: Bitmap) {
        serviceList += ServiceListElement(id, name, imageBitmap)
    }
    fun addService(serviceListElement: ServiceListElement) {
        serviceList += serviceListElement
    }
    fun clear() {
        serviceList.clear()
    }
    fun loadServiceInfo(): MutableList<ServiceListElement> {
        return serviceList
    }
}