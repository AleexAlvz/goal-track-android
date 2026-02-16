package com.br.aleexalvz.android.goaltrack.presenter.home.presentation.goal.ui

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.R
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes.goalDetailWithId
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.goal.model.GoalState
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.goal.viewmodel.GoalViewModel
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme
import java.time.LocalDate

@Composable
fun GoalsScreen(
    navController: NavController,
    goalViewModel: GoalViewModel = hiltViewModel()
) {
    val state by goalViewModel.state.collectAsState()
    goalViewModel.getGoals()

    GoalsContent(
        goalState = state,
        onNavigateToCreateGoal = { navController.navigate(HomeRoutes.goalFormCreateMode()) },
        onNavigateToGoalDetail = { goalId -> navController.navigate(goalDetailWithId(goalId)) }
    )
}

@Composable
fun GoalsContent(
    goalState: GoalState,
    onNavigateToCreateGoal: () -> Unit,
    onNavigateToGoalDetail: (goalId: Long) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { onNavigateToCreateGoal() }
        ) {
            Row(
                Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
                Text(text = "Nova meta")
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Metas",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            // TODO Filtros

            Card(
                Modifier
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        shape = CardDefaults.shape
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                LazyColumn {
                    items(goalState.goals.size) { index ->
                        GoalListItem(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                            goal = goalState.goals[index],
                            onClick = { onNavigateToGoalDetail(it.id) }
                        )

                        if (index < goalState.goals.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                thickness = 1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GoalListItem(
    modifier: Modifier = Modifier,
    goal: GoalModel,
    onClick: (goal: GoalModel) -> Unit
) {

    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(goal) }
    ) {
        IconButton(
            modifier = Modifier.size(46.dp),
            onClick = {},
            colors = IconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            // TODO Trocar icone por respectivo a category
            Icon(
                imageVector = Icons.Default.TaskAlt,
                contentDescription = "Imagem",
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                text = goal.title
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                text = getGoalStatusMessage(context, goal)
            )
        }
    }
}

private fun getGoalStatusMessage(context: Context, goalModel: GoalModel): String {
    return when (goalModel.status) {
        GoalStatusEnum.IN_PROGRESS -> {
            context.getString(R.string.goal_status_in_progress) + " - " + goalModel.creationDate
        }

        GoalStatusEnum.COMPLETED -> {
            context.getString(R.string.goal_status_finished) + " - " + goalModel.endDate
        }

        GoalStatusEnum.CANCELED -> {
            context.getString(R.string.goal_status_canceled) + " - " + goalModel.endDate
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Composable
fun GoalsScreenPreviewLight() {
    GoalTrackTheme {
        GoalsContent(
            goalState = GoalState(
                goals = mutableListOf<GoalModel>().apply {
                    repeat(5) { index ->
                        add(
                            GoalModel(
                                id = 0,
                                title = "Title $index",
                                description = "Description $index",
                                category = GoalCategoryEnum.CAREER,
                                creationDate = LocalDate.now(),
                                endDate = null,
                                status = GoalStatusEnum.IN_PROGRESS
                            )
                        )
                    }
                },
                isLoading = false,
                error = null
            ),
            onNavigateToCreateGoal = { },
            onNavigateToGoalDetail = { }
        )
    }
}

@Preview(name = "Dark", showSystemUi = true)
@Composable
fun GoalsScreenPreviewDark() {
    GoalTrackTheme(darkTheme = true) {
        GoalsContent(
            goalState = GoalState(
                goals = mutableListOf<GoalModel>().apply {
                    repeat(5) { index ->
                        add(
                            GoalModel(
                                id = 0,
                                title = "Title $index",
                                description = "Description $index",
                                category = GoalCategoryEnum.CAREER,
                                creationDate = LocalDate.now(),
                                endDate = null,
                                status = GoalStatusEnum.IN_PROGRESS
                            )
                        )
                    }
                },
                isLoading = false,
                error = null
            ),
            onNavigateToCreateGoal = { },
            onNavigateToGoalDetail = { }
        )
    }
}