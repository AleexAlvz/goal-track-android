package com.br.aleexalvz.android.goaltrack.domain.model.goal

enum class GoalCategoryEnum {
    HEALTH,
    FITNESS,
    STUDY,
    CAREER,
    FINANCE,
    PERSONAL,
    RELATIONSHIPS,
    HOBBY,
    SPIRITUALITY,
    TRAVEL,
    HOME,
    OTHER
}

fun String.toGoalCategoryEnum() = GoalCategoryEnum.entries.first { it.name == this }