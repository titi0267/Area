package com.example.area.data

import com.example.area.model.ActionReactionInfo

class ActionReactionDatasource {
    private var actionReactionList:MutableList<ActionReactionInfo> = ArrayList()
    fun addService(id: Int, name: String, paramName: String, description: String) {
        actionReactionList += ActionReactionInfo(id, name, paramName, description)
    }
    fun clear() {
        actionReactionList.clear()
    }
    fun loadActionReactionInfo(): MutableList<ActionReactionInfo> {
        return actionReactionList
    }
}