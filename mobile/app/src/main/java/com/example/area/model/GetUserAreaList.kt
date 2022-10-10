package com.example.area.model

data class GetUserAreaList(
    val id: Int,
    val userId: Int,
    val actionServiceId: Int,
    val actionId: Int,
    val actionParam: String,
    val lastActionFetch: Long,
    val reactionServiceId: Int,
    val reactionId: Int,
    val reactionParam: String
)