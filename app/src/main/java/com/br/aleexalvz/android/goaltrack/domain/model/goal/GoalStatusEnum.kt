package com.br.aleexalvz.android.goaltrack.domain.model.goal

import android.content.Context
import com.br.aleexalvz.android.goaltrack.R

enum class GoalStatusEnum {
    IN_PROGRESS,
    COMPLETED,
    CANCELED
}

fun GoalStatusEnum.getMessage(context: Context): String = when (this) {
    GoalStatusEnum.IN_PROGRESS -> context.getString(R.string.in_progress)
    GoalStatusEnum.COMPLETED -> context.getString(R.string.completed)
    GoalStatusEnum.CANCELED -> context.getString(R.string.canceled)
}
