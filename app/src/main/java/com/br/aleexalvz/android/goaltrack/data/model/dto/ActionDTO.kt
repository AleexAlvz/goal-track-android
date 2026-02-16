package com.br.aleexalvz.android.goaltrack.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActionDTO(
    private val id: Long,
    private val goalId: Long,
    private val title: String,
    private val description: String,
    private val frequency: String,
    private val creationDate: String,
    private val endDate: String? = null
)