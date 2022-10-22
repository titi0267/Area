package com.example.area.model.about

data class Service (
    val id: Int,
    val name: String,
    val backgroundColor: String,
    val imageUrl: String,
    val actions: List<Action>,
    val reactions: List<Reaction>
)