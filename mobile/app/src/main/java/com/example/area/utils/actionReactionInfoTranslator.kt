package com.example.area.utils

import com.example.area.model.ActionReactionInfo
import com.example.area.model.about.Action
import com.example.area.model.about.Reaction

fun actionReactionInfoTranslator(action: Action?, reaction: Reaction?): ActionReactionInfo? {
    return (if (action == null && reaction != null) {
        ActionReactionInfo(reaction.id, reaction.name, reaction.reactionParamName, reaction.description)
    } else if (action != null && reaction == null) {
        ActionReactionInfo(action.id, action.name, action.actionParamName, action.description)
    } else {
        null
    })
}