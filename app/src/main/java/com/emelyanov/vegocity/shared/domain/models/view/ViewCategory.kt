package com.emelyanov.vegocity.shared.domain.models.view

import com.emelyanov.vegocity.shared.domain.models.Category

data class ViewCategory(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)

fun Category.toViewCategory()
= ViewCategory(
    id = id.toString(),
    name = name
)