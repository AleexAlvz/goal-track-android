package com.br.aleexalvz.android.goaltrack.data.dto

data class GoalDTO(
    val id: Long,
    val title: String,
    val description: String? = "",
    val category: String,
    val creationDate: String,
    val endDate: String? = null,
    val status: String
)