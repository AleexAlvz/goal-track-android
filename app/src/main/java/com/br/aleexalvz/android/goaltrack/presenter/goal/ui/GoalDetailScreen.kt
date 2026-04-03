package com.br.aleexalvz.android.goaltrack.presenter.goal.ui

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.R
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.model.action.getMessage
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.SkillModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.getMessage
import com.br.aleexalvz.android.goaltrack.presenter.components.button.PrimaryButton
import com.br.aleexalvz.android.goaltrack.presenter.components.chip.InformativeChip
import com.br.aleexalvz.android.goaltrack.presenter.components.dialog.ErrorDialog
import com.br.aleexalvz.android.goaltrack.presenter.components.fab.FabMenu
import com.br.aleexalvz.android.goaltrack.presenter.components.fab.FabMenuItem
import com.br.aleexalvz.android.goaltrack.presenter.components.header.PageHeader
import com.br.aleexalvz.android.goaltrack.presenter.components.header.SectionHeader
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailAction
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailEvent
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

    val goalDetailState by viewModel.goalDetailState.collectAsState()

    GoalDetailEventHandler(
        goalDetailViewModel = viewModel,
        onCompleted = {
            navController.navigate(HomeRoutes.GOALS)
        }
    )

    GoalDetailContent(
        goalDetailState = goalDetailState,
        onBackClicked = { navController.popBackStack() },
        onEditClicked = {
            goalDetailState.goal?.id?.let { id ->
                navController.navigate(HomeRoutes.goalFormEditMode(id))
            }
        },
        onNavigateToCreateAction = {
            navController.navigate(
                HomeRoutes.actionFormCreateMode(
                    goalDetailState.goal?.id ?: error("Goal id is required")
                )
            )
        },
        onNavigateToActionDetail = { actionId ->
            navController.navigate(HomeRoutes.actionDetailWithId(actionId))
        },
        onCompleteGoal = {
            viewModel.onUIAction(GoalDetailAction.CompleteGoal)
        }
    )
}

@Composable
fun GoalDetailContent(
    goalDetailState: GoalDetailState,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onCompleteGoal: () -> Unit,
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
            item { PageHeader(title = "Detalhes da Meta", onBackClicked = onBackClicked) }
            item { GoalDetailSection(goalDetailState, context) }
            item { GoalProgressSection(goalDetailState, onCompleteGoal) }
            skillListSection(goalDetailState.goal?.skills ?: emptyList())
            actionListSection(
                actions = goalDetailState.actions,
                onNavigateToCreateAction = onNavigateToCreateAction,
                onNavigateToActionDetail = onNavigateToActionDetail
            )
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }

        FabMenu(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            items = listOf(
                FabMenuItem(
                    icon = Icons.Default.Edit,
                    label = "Editar",
                    onClick = onEditClicked
                ),
                FabMenuItem(
                    icon = Icons.Default.Check,
                    label = "Completar",
                    onClick = onCompleteGoal
                ),
                FabMenuItem(
                    icon = Icons.Default.Delete,
                    label = "Excluir",
                    onClick = { "OndeleteGoal" }
                )
            )
        )
    }
}

@Composable
private fun GoalProgressSection(
    goalDetailState: GoalDetailState,
    onCompleteGoal: () -> Unit
) {
    val completed = goalDetailState.actions.filter { it.endDate != null }.size
    val progress: Float = if (goalDetailState.actions.isEmpty()) {
        0F
    } else {
        (completed.toFloat() / goalDetailState.actions.size)
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = CardDefaults.shape
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Progresso geral",
                    fontSize = 16.sp
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                progress = { progress }
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                goalDetailState.goal?.creationDate.toString().let { creationDate ->
                    Text(
                        text = "Inicio: $creationDate",
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "$completed/${goalDetailState.actions.size} ações concluídas",
                    fontSize = 12.sp
                )
            }

            if (progress.equals(1F) && completed > 0) {
                Spacer(Modifier.padding(16.dp))
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Concluir meta",
                    onClick = onCompleteGoal
                )
            }
        }
    }
}

@Composable
private fun GoalDetailSection(
    goalDetailState: GoalDetailState,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        InformativeChip(
            text = goalDetailState.goal?.status?.getMessage(context).orEmpty(),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
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

@OptIn(ExperimentalLayoutApi::class)
private fun LazyListScope.skillListSection(skills: List<SkillModel>) {
    item {
        SectionHeader(
            sectionIcon = Icons.AutoMirrored.Filled.MenuBook,
            sectionTitle = "Competências relacionadas"
        )
    }
    if (skills.isEmpty()) {
        item {
            Text(
                text = "Nenhuma competência foi adicionada a esta meta.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    } else {
        item {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                skills.forEach { skill ->
                    InformativeChip(
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                        text = skill.name
                    )
                }
            }
        }
    }
}

private fun LazyListScope.actionListSection(
    actions: List<ActionModel>,
    onNavigateToCreateAction: () -> Unit,
    onNavigateToActionDetail: (actionId: Long) -> Unit
) {

    item {
        SectionHeader(
            sectionIcon = Icons.AutoMirrored.Filled.ListAlt,
            sectionTitle = "Plano de ação",
            sectionAction = "+ Nova ação",
            onSectionActionClicked = onNavigateToCreateAction
        )
    }

    items(
        items = actions,
        key = { action -> action.id }
    ) { action ->
        ActionListItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            action = action,
            onClick = { onNavigateToActionDetail(action.id) }
        )

        Spacer(Modifier.padding(8.dp))
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
                val actionIcon: ImageVector = if (action.endDate == null) {
                    Icons.Default.RadioButtonUnchecked
                } else {
                    Icons.Default.TaskAlt
                }

                Icon(
                    imageVector = actionIcon,
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

@Composable
private fun GoalDetailEventHandler(
    goalDetailViewModel: GoalDetailViewModel,
    onCompleted: () -> Unit,
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(goalDetailViewModel) {
        goalDetailViewModel.goalDetailEvent.collect { event ->
            when (event) {
                is GoalDetailEvent.Completed -> onCompleted()
                is GoalDetailEvent.ConnectionError -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.network_error_title)
                    dialogMessage = context.getString(R.string.network_error_message)
                }

                is GoalDetailEvent.UnexpectedError -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.unexpected_error_title)
                    dialogMessage = context.getString(R.string.unexpected_error_message)
                }
            }
        }
    }

    if (showDialog) {
        ErrorDialog(
            title = dialogTitle,
            message = dialogMessage,
            confirmText = stringResource(R.string.ok),
            onConfirm = { showDialog = false },
            onDismiss = { showDialog = false }
        )
    }
}

private fun getGoalToPreview() = GoalModel(
    id = 1,
    title = "Correr 5km na semana",
    description = "Manter o hábito de correr para melhorar o condicionamento físico e a saúde.",
    status = GoalStatusEnum.IN_PROGRESS,
    category = GoalCategoryEnum.CAREER,
    creationDate = LocalDate.now()
)

private fun getActionsToPreview(totalSize: Int, completed: Int) =
    mutableListOf<ActionModel>().apply {
        repeat(totalSize) { index ->
            add(
                ActionModel(
                    id = index.toLong(),
                    title = "Ação $index",
                    description = "Descrição da ação $index",
                    frequency = ActionFrequencyEnum.ONCE, // Ação única
                    goalId = 1,
                    endDate = if (index < (totalSize - completed)) null else LocalDate.now()
                )
            )
        }
    }

@Preview(showBackground = true)
@Composable
private fun GoalDetailScreenPreviewCompleted() {
    GoalTrackTheme {
        GoalTrackTheme {
            GoalDetailContent(
                goalDetailState = GoalDetailState(
                    goal = getGoalToPreview(),
                    actions = getActionsToPreview(2, 2)
                ),
                onBackClicked = {},
                onEditClicked = {},
                onNavigateToCreateAction = {},
                onNavigateToActionDetail = {},
                onCompleteGoal = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalDetailScreenPreviewTotallyInProgress() {
    GoalTrackTheme {
        GoalTrackTheme {
            GoalDetailContent(
                goalDetailState = GoalDetailState(
                    goal = getGoalToPreview(),
                    actions = getActionsToPreview(2, 0)
                ),
                onBackClicked = {},
                onEditClicked = {},
                onNavigateToCreateAction = {},
                onNavigateToActionDetail = {},
                onCompleteGoal = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalDetailScreenPreviewDarkHalfProgress() {
    GoalTrackTheme(darkTheme = true) {
        GoalDetailContent(
            goalDetailState = GoalDetailState(
                goal = getGoalToPreview(),
                actions = getActionsToPreview(2, 1)
            ),
            onBackClicked = {},
            onEditClicked = {},
            onNavigateToCreateAction = {},
            onNavigateToActionDetail = {},
            onCompleteGoal = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalDetailScreenPreviewDarkEmptyActions() {
    GoalTrackTheme(darkTheme = true) {
        GoalDetailContent(
            goalDetailState = GoalDetailState(
                goal = getGoalToPreview(),
                actions = getActionsToPreview(0, 0)
            ),
            onBackClicked = {},
            onEditClicked = {},
            onNavigateToCreateAction = {},
            onNavigateToActionDetail = {},
            onCompleteGoal = {}
        )
    }
}