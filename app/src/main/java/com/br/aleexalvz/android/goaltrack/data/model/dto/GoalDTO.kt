package com.br.aleexalvz.android.goaltrack.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class GoalDTO(
    val id: Long,
    val title: String,
    val description: String? = "",
    val category: String,
    val skills: List<String>,
    val creationDate: String,
    val endDate: String? = null,
    val status: String
)