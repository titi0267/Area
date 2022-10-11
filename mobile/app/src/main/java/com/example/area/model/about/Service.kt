package com.example.area.model.about

data class Service (
    val name: String,
    val id: Int,
    val actions: List<Action>,
    val reactions: List<Reaction>
)