package com.example.area.model

data class ActionReaction(
    val id: Int,
    val userId: Int,
    val actionServiceId: Int,
    val actionId: Int,
    val actionParam: String,
    val lastActionFetch: String,
    val lastActionValue: String?,
    val reactionServiceId: Int,
    val reactionId: Int,
    val reactionParam: String
)