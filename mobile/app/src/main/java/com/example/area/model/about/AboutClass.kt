package com.example.area.model.about

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

class AboutClass(val about: About) {

    private fun getServiceById(id: Int): Service? {
        for (service in about.server.services) {
            if (service.id == id) {
                return (service)
            }
        }
        return null
    }

    private fun getServiceByName(name: String): Service? {
        for (service in about.server.services) {
            if (service.name == name) {
                return (service)
            }
        }
        return null
    }

    fun getServiceNameById(id: Int): String? {
        val service = getServiceById(id) ?: return null
        return formatServiceName(service.name)
    }

    fun getServiceIdByName(name: String): Int? {
        val service = getServiceByName(name) ?: return null
        return service.id
    }

    fun getServiceBackgroundColor(id: Int): String? {
        val service = getServiceById(id) ?: return null
        return service.backgroundColor
    }

    fun getServiceBackgroundColor(name: String): String? {
        val service = getServiceByName(name) ?: return null
        return service.backgroundColor
    }

    fun getServiceImageURL(id: Int): String? {
        val service = getServiceById(id) ?: return null
        return service.imageUrl
    }

    fun getServiceImageURL(name: String): String? {
        val service = getServiceByName(name) ?: return null
        return service.imageUrl
    }

    fun getServiceOAuthName(id: Int): String? {
        val service = getServiceById(id) ?: return null
        return service.oauthName
    }

    fun getServiceOAuthName(name: String): String? {
        val service = getServiceByName(name) ?: return null
        return service.oauthName
    }

    private fun getServiceActionById(serviceId: Int, id: Int): Action? {
        val service = getServiceById(serviceId) ?: return null
        for (action in service.actions) {
            if (action.id == id) {
                return (action)
            }
        }
        return null
    }

    private fun getServiceActionById(serviceName: String, id: Int): Action? {
        val service = getServiceByName(serviceName) ?: return null
        for (action in service.actions) {
            if (action.id == id) {
                return (action)
            }
        }
        return null
    }

    private fun getServiceActionByName(serviceId: Int, name: String): Action? {
        val service = getServiceById(serviceId) ?: return null
        for (action in service.actions) {
            if (action.name == name) {
                return (action)
            }
        }
        return null
    }

    private fun getServiceActionByName(serviceName: String, name: String): Action? {
        val service = getServiceByName(serviceName) ?: return null
        for (action in service.actions) {
            if (action.name == name) {
                return (action)
            }
        }
        return null
    }

    fun getServiceActionNameById(serviceId: Int, id: Int): String? {
        val action = getServiceActionById(serviceId, id) ?: return null
        return formatAREAName(action.name)
    }

    fun getServiceActionNameById(serviceName: String, id: Int): String? {
        val action = getServiceActionById(serviceName, id) ?: return null
        return formatAREAName(action.name)
    }

    fun getServiceActionIdByName(serviceId: Int, name: String): Int? {
        val action = getServiceActionByName(serviceId, name) ?: return null
        return action.id
    }

    fun getServiceActionIdByName(serviceName: String, name: String): Int? {
        val action = getServiceActionByName(serviceName, name) ?: return null
        return action.id
    }

    fun getServiceActionDescriptionById(serviceId: Int, id: Int): String? {
        val action = getServiceActionById(serviceId, id) ?: return null
        return (action.description)
    }

    fun getServiceActionDescriptionById(serviceName: String, id: Int): String? {
        val action = getServiceActionById(serviceName, id) ?: return null
        return (action.description)
    }

    fun getServiceActionDescriptionByName(serviceId: Int, name: String): String? {
        val action = getServiceActionByName(serviceId, name) ?: return null
        return (action.description)
    }

    fun getServiceActionDescriptionByName(serviceName: String, name: String): String? {
        val action = getServiceActionByName(serviceName, name) ?: return null
        return (action.description)
    }

    fun getServiceActionParamNameById(serviceId: Int, id: Int): String? {
        val action = getServiceActionById(serviceId, id) ?: return null
        return (action.actionParamName)
    }

    fun getServiceActionParamNameById(serviceName: String, id: Int): String? {
        val action = getServiceActionById(serviceName, id) ?: return null
        return (action.actionParamName)
    }

    fun getServiceActionParamNameByName(serviceId: Int, name: String): String? {
        val action = getServiceActionByName(serviceId, name) ?: return null
        return (action.actionParamName)
    }

    fun getServiceActionParamNameById(serviceName: String, name: String): String? {
        val action = getServiceActionByName(serviceName, name) ?: return null
        return (action.actionParamName)
    }

    fun getServiceActionAvailableInjectParamsById(serviceId: Int, id: Int): List<String>? {
        val action = getServiceActionById(serviceId, id) ?: return null
        return (action.availableInjectParams)
    }

    fun getServiceActionAvailableInjectParamsById(serviceName: String, id: Int): List<String>? {
        val action = getServiceActionById(serviceName, id) ?: return null
        return (action.availableInjectParams)
    }

    fun getServiceActionAvailableInjectParamsByName(serviceId: Int, name: String): List<String>? {
        val action = getServiceActionByName(serviceId, name) ?: return null
        return (action.availableInjectParams)
    }

    fun getServiceActionAvailableInjectParamsByName(serviceName: String, name: String): List<String>? {
        val action = getServiceActionByName(serviceName, name) ?: return null
        return (action.availableInjectParams)
    }

    private fun getServiceReactionById(serviceId: Int, id: Int): Reaction? {
        val service = getServiceById(serviceId) ?: return null
        for (reaction in service.reactions) {
            if (reaction.id == id) {
                return (reaction)
            }
        }
        return null
    }

    private fun getServiceReactionById(serviceName: String, id: Int): Reaction? {
        val service = getServiceByName(serviceName) ?: return null
        for (reaction in service.reactions) {
            if (reaction.id == id) {
                return (reaction)
            }
        }
        return null
    }

    private fun getServiceReactionByName(serviceId: Int, name: String): Reaction? {
        val service = getServiceById(serviceId) ?: return null
        for (reaction in service.reactions) {
            if (reaction.name == name) {
                return (reaction)
            }
        }
        return null
    }

    private fun getServiceReactionByName(serviceName: String, name: String): Reaction? {
        val service = getServiceByName(serviceName) ?: return null
        for (reaction in service.reactions) {
            if (reaction.name == name) {
                return (reaction)
            }
        }
        return null
    }

    fun getServiceReactionNameById(serviceId: Int, id: Int): String? {
        val reaction = getServiceReactionById(serviceId, id) ?: return null
        return formatAREAName(reaction.name)
    }

    fun getServiceReactionNameById(serviceName: String, id: Int): String? {
        val reaction = getServiceReactionById(serviceName, id) ?: return null
        return formatAREAName(reaction.name)
    }

    fun getServiceReactionIdByName(serviceId: Int, name: String): String? {
        val reaction = getServiceReactionByName(serviceId, name) ?: return null
        return formatAREAName(reaction.name)
    }

    fun getServiceReactionIdByName(serviceName: String, name: String): String? {
        val reaction = getServiceReactionByName(serviceName, name) ?: return null
        return formatAREAName(reaction.name)
    }

    fun getServiceReactionDescriptionById(serviceId: Int, id: Int): String? {
        val reaction = getServiceReactionById(serviceId, id) ?: return null
        return (reaction.description)
    }

    fun getServiceReactionDescriptionById(serviceName: String, id: Int): String? {
        val reaction = getServiceReactionById(serviceName, id) ?: return null
        return (reaction.description)
    }

    fun getServiceReactionDescriptionByName(serviceId: Int, string: String): String? {
        val reaction = getServiceReactionByName(serviceId, string) ?: return null
        return (reaction.description)
    }

    fun getServiceReactionDescriptionByName(serviceName: String, string: String): String? {
        val reaction = getServiceReactionByName(serviceName, string) ?: return null
        return (reaction.description)
    }

    fun getServiceReactionParamNameById(serviceId: Int, id: Int): String? {
        val action = getServiceReactionById(serviceId, id) ?: return null
        return (action.reactionParamName)
    }

    fun getServiceReactionParamNameById(serviceName: String, id: Int): String? {
        val action = getServiceReactionById(serviceName, id) ?: return null
        return (action.reactionParamName)
    }

    fun getServiceReactionParamNameByName(serviceId: Int, name: String): String? {
        val action = getServiceReactionByName(serviceId, name) ?: return null
        return (action.reactionParamName)
    }

    fun getServiceReactionParamNameById(serviceName: String, name: String): String? {
        val action = getServiceReactionByName(serviceName, name) ?: return null
        return (action.reactionParamName)
    }

    fun formatAREAName(name: String): String {
        name.replace('_', ' ')
        if (name[0].isLowerCase())
            name[0].uppercase()
        return (name)
    }

    fun formatServiceName(name: String): String {
        if (name[0].isLowerCase())
            name[0].uppercase()
        return (name)
    }

    fun getServiceNameList(): List<String> {
        val ret = mutableListOf<String>()
        for (service in about.server.services) {
            ret.add(service.name)
        }
        return ret
    }

    fun getServiceOAuthNameList(): List<String> {
        val ret = mutableListOf<String>()
        for (service in about.server.services) {
            if (service.oauthName != "none")
                ret.add(service.oauthName)
        }
        return (ret)
    }

    private fun getBitmapByLink(link: String) : Bitmap {
        return (BitmapFactory.decodeStream(URL(link).openConnection().getInputStream()))
    }

    fun getBitmapList(url: String): List<Bitmap> {
        val ret = mutableListOf<Bitmap>()
        for (service in about.server.services) {
            ret.add(getBitmapByLink(url + service.imageUrl))
        }
        return (ret)
    }

    fun getServiceActionNameList(serviceId: Int): List<String> {
        val service = getServiceById(serviceId) ?: return mutableListOf()
        val ret = mutableListOf<String>()
        for (action in service.actions) {
            ret.add(action.name)
        }
        return ret
    }

    fun getServiceReactionNameList(serviceId: Int): List<String> {
        val service = getServiceById(serviceId) ?: return mutableListOf()
        val ret = mutableListOf<String>()
        for (reaction in service.reactions) {
            ret.add(reaction.name)
        }
        return ret
    }
}