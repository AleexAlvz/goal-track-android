package com.br.aleexalvz.android.goaltrack.presenter.goal.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.model.action.getMessage
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.getMessage
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailState
import com.br.aleexalvz.android.goaltrack.presenter.goal.viewmodel.GoalDetailViewModel
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme
import java.time.LocalDate

@Composable
fun GoalDetailScreen(
    navController: NavController,
    viewModel: GoalDetailViewModel = hiltViewModel()
) {

    val goalDetailState by viewModel.goal.collectAsState()

    GoalDetailContent(
        goalDetailState = goalDetailState,
        onBackClicked = { navController.popBackStack() },
        onNavigateToCreateAction = {
            navController.navigate(
                HomeRoutes.actionFormCreateMode(
                    goalDetailState.goal?.id ?: error("Goal id is required")
                )
            )
        },
        onNavigateToActionDetail = { actionId ->
            navController.navigate(HomeRoutes.actionDetailWithId(actionId))
        }
    )
}

@Composable
fun GoalDetailContent(
    goalDetailState: GoalDetailState,
    onBackClicked: () -> Unit,
    onNavigateToCreateAction: () -> Unit,
    onNavigateToActionDetail: (actionId: Long) -> Unit,
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = onBackClicked
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                    Text(
                        text = "Detalhes da Meta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.padding(end = 56.dp))
                }
                HorizontalDivider()
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Text(
                            text = goalDetailState.goal?.status?.getMessage(context).orEmpty(),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    Spacer(Modifier.padding(4.dp))
                    Text(
                        text = goalDetailState.goal?.title.orEmpty(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = goalDetailState.goal?.description.orEmpty(),
                        fontSize = 16.sp
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 16.dp),
                        imageVector = Icons.AutoMirrored.Filled.ListAlt,
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = "Plano de ação",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "+ Nova ação",
                        fontSize = 14.sp,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { onNavigateToCreateAction() }
                    )
                }
            }

            items(goalDetailState.actions.size) { index ->
                val action = goalDetailState.actions[index]
                ActionListItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    action = action,
                    onClick = { onNavigateToActionDetail(action.id) }
                )

                Spacer(Modifier.padding(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun ActionListItem(
    modifier: Modifier = Modifier,
    action: ActionModel,
    onClick: (action: ActionModel) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(action) }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = CardDefaults.shape
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(28.dp),
                onClick = {},
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.TaskAlt,
                    contentDescription = "Imagem",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = action.title
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline,
                    text = action.frequency.getMessage(LocalContext.current)
                )
            }

            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(24.dp),
                imageVector = Icons.Default.ChevronRight,
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoalDetailScreenPreview() {
    val previewGoal = GoalModel(
        id = 1,
        title = "Correr 5km na semana",
        description = "Manter o hábito de correr para melhorar o condicionamento físico e a saúde.",
        status = GoalStatusEnum.IN_PROGRESS,
        category = GoalCategoryEnum.CAREER,
        creationDate = LocalDate.now()
    )

    val actions = listOf(
        ActionModel(
            id = 1L,
            title = "Comprar tênis de corrida que seja confortável",
            description = "Ir à loja de esportes e escolher um tênis adequado para corrida.",
            frequency = ActionFrequencyEnum.ONCE, // Ação única
            goalId = 1
        ),
        ActionModel(
            id = 2L,
            title = "Corrida leve - 1km",
            description = "Primeira corrida para aquecer e acostumar o corpo.",
            frequency = ActionFrequencyEnum.WEEKLY, // Ação semanal
            goalId = 1
        ),
        ActionModel(
            id = 3L,
            title = "Caminhada rápida - 30 min",
            description = "Manter o cardio em dias de descanso da corrida.",
            frequency = ActionFrequencyEnum.DAILY, // Ação diária
            goalId = 1
        ),
        ActionModel(
            id = 4L,
            title = "Corrida intermediária - 2.5km",
            description = "Aumentar a distância no meio da semana.",
            frequency = ActionFrequencyEnum.WEEKLY,
            goalId = 1
        ),
        ActionModel(
            id = 5L,
            title = "Alongamento pós-treino",
            description = "Alongar por 10 minutos após cada corrida para evitar lesões.",
            frequency = ActionFrequencyEnum.DAILY,
            goalId = 1
        )
    )

    GoalTrackTheme {
        GoalDetailContent(
            goalDetailState = GoalDetailState(
                goal = previewGoal,
                actions = actions
            ),
            onBackClicked = {},
            onNavigateToCreateAction = {},
            onNavigateToActionDetail = {}
        )
    }
}