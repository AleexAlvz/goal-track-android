package com.br.aleexalvz.android.goaltrack.domain.model.action

import android.content.Context
import com.br.aleexalvz.android.goaltrack.R

enum class ActionFrequencyEnum {
    ONCE,
    DAILY,
    WEEKLY,
    MONTHLY
}

fun String.toActionFrequencyEnum() = ActionFrequencyEnum.entries.first { it.name == this }

fun ActionFrequencyEnum.getMessage(context: Context) = when (this) {
    ActionFrequencyEnum.ONCE -> context.getString(R.string.once)
    ActionFrequencyEnum.DAILY -> context.getString(R.string.daily)
    ActionFrequencyEnum.WEEKLY -> context.getString(R.string.weekly)
    ActionFrequencyEnum.MONTHLY -> context.getString(R.string.monthly)
}