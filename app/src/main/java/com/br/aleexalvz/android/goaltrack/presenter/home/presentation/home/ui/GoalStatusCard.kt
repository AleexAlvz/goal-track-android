package com.br.aleexalvz.android.goaltrack.presenter.home.presentation.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.home.model.GoalStatusCardState

@Composable
fun GoalStatusCard(
    modifier: Modifier = Modifier,
    state: GoalStatusCardState,
    onNavigateToGoals: () -> Unit,
    onNavigateToCreateGoal: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Goal status",
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )

            when (state) {
                is GoalStatusCardState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .size(32.dp)
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                is GoalStatusCardState.InProgress -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        text = "${state.goals} metas em progresso",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 16.dp),
                        onClick = { onNavigateToGoals() },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Ver metas")
                    }
                }

                is GoalStatusCardState.NotInProgress -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        text = "Nenhuma meta em progresso",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 16.dp),
                        onClick = { onNavigateToCreateGoal() },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Criar meta")
                    }
                }


            }
        }
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
fun GoalStatusCardLoadingPreview() {
    GoalStatusCard(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        state = GoalStatusCardState.Loading,
        onNavigateToGoals = {},
        onNavigateToCreateGoal = {}
    )
}

@Preview(name = "In progress", showBackground = true)
@Composable
fun GoalStatusCardInProgressPreview() {
    GoalStatusCard(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        state = GoalStatusCardState.InProgress(10),
        onNavigateToGoals = {},
        onNavigateToCreateGoal = {}
    )
}

@Preview(name = "Not in progress", showBackground = true)
@Composable
fun GoalStatusCardNotInProgressPreview() {
    GoalStatusCard(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        state = GoalStatusCardState.NotInProgress,
        onNavigateToGoals = {},
        onNavigateToCreateGoal = {}
    )
}