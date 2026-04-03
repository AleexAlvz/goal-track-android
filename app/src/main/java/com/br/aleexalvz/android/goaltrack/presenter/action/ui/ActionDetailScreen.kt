package com.br.aleexalvz.android.goaltrack.presenter.action.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.R
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.model.action.getMessage
import com.br.aleexalvz.android.goaltrack.domain.model.note.NoteModel
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionDetailAction
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionDetailEvent
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionDetailState
import com.br.aleexalvz.android.goaltrack.presenter.action.viewmodel.ActionDetailViewModel
import com.br.aleexalvz.android.goaltrack.presenter.components.chip.InformativeChip
import com.br.aleexalvz.android.goaltrack.presenter.components.dialog.ErrorDialog
import com.br.aleexalvz.android.goaltrack.presenter.components.fab.FabMenu
import com.br.aleexalvz.android.goaltrack.presenter.components.fab.FabMenuItem
import com.br.aleexalvz.android.goaltrack.presenter.components.header.PageHeader
import com.br.aleexalvz.android.goaltrack.presenter.components.header.SectionHeader
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme
import java.time.LocalDate

@Composable
fun ActionDetailScreen(
    navController: NavController,
    viewModel: ActionDetailViewModel = hiltViewModel()
) {
    val actionDetailState by viewModel.state.collectAsState()

    ActionDetailEventHandler(
        actionDetailViewModel = viewModel,
        onDeleted = {
            actionDetailState.action?.goalId?.let { goalId ->
                navController.navigate(HomeRoutes.goalDetailWithId(goalId)) {
                    popUpTo(HomeRoutes.goalDetailWithId(goalId)) {
                        inclusive = true
                    }
                }
            }
        }
    )

    ActionDetailContent(
        actionDetailState = actionDetailState,
        onBackClicked = { navController.popBackStack() },
        onEditClicked = {
            actionDetailState.action?.let { action ->
                navController.navigate(
                    HomeRoutes.actionFormEditMode(
                        actionId = action.id
                    )
                )
            }
        },
        onUIAction = {
            viewModel.onUIAction(it)
        }
    )
}

@Composable
fun ActionDetailContent(
    actionDetailState: ActionDetailState,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onUIAction: (uiAction: ActionDetailAction) -> Unit = {}
) {

    val context = LocalContext.current
    var showNoteForm by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                PageHeader(title = "Detalhes da Ação", onBackClicked = onBackClicked)
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    InformativeChip(
                        text = actionDetailState.action?.frequency?.getMessage(context)
                            .orEmpty(),
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(Modifier.padding(4.dp))
                    Text(
                        text = actionDetailState.action?.title.orEmpty(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = actionDetailState.action?.description.orEmpty(),
                        fontSize = 16.sp
                    )
                }
            }

            item {
                SectionHeader(
                    sectionIcon = Icons.AutoMirrored.Filled.Notes,
                    sectionTitle = "Notas de Progresso",
                    sectionAction = "+ Nova nota",
                    onSectionActionClicked = { showNoteForm = true }
                )
            }

            item {
                AnimatedVisibility(visible = showNoteForm) {
                    NoteForm(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onNoteSaved = { note ->
                            onUIAction(ActionDetailAction.AddNote(note))
                            showNoteForm = false
                        },
                        onCanceled = { showNoteForm = false }
                    )
                }
            }

            items(actionDetailState.notes.size) { index ->
                val note = actionDetailState.notes[index]
                NoteListItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    description = note.notes,
                    noteDate = note.executionDate.toString(),
                    onClick = { /* TODO: Implementar click da anotação */ }
                )
                Spacer(Modifier.padding(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
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
                    onClick = { onUIAction(ActionDetailAction.CompleteAction) }
                ),
                FabMenuItem(
                    icon = Icons.Default.Delete,
                    label = "Excluir",
                    onClick = { onUIAction(ActionDetailAction.DeleteAction) }
                )
            )
        )
    }
}

@Composable
fun ActionDetailEventHandler(
    actionDetailViewModel: ActionDetailViewModel,
    onDeleted: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(actionDetailViewModel) {
        actionDetailViewModel.event.collect { event ->
            when (event) {
                is ActionDetailEvent.Deleted -> onDeleted()

                is ActionDetailEvent.ConnectionError -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.network_error_title)
                    dialogMessage = context.getString(R.string.network_error_message)
                }

                is ActionDetailEvent.UnexpectedError -> {
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

@Composable
fun NoteListItem(
    modifier: Modifier = Modifier,
    description: String,
    noteDate: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = CardDefaults.shape
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = noteDate,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, name = "Action Detail Preview - Light")
@Composable
fun ActionDetailScreenPreview() {
    val previewAction = ActionModel(
        id = 1,
        goalId = 1,
        title = "Corrida intermediária - 2.5km",
        description = "Aumentar a distância no meio da semana para atingir a meta de 5km.",
        frequency = ActionFrequencyEnum.WEEKLY,
        creationDate = LocalDate.now()
    )

    GoalTrackTheme {
        ActionDetailContent(
            actionDetailState = ActionDetailState(
                action = previewAction,
                notes = listOf(
                    NoteModel(0L, 0L, LocalDate.now(), "Nota de progresso: Hoje eu fiz algo")
                )
            ),
            onBackClicked = {},
            onEditClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "Action Detail Preview - Dark")
@Composable
fun ActionDetailScreenPreviewDark() {
    val previewAction = ActionModel(
        id = 1,
        goalId = 1,
        title = "Corrida intermediária - 2.5km",
        description = "Aumentar a distância no meio da semana para atingir a meta de 5km.",
        frequency = ActionFrequencyEnum.WEEKLY,
        creationDate = LocalDate.now()
    )

    GoalTrackTheme(darkTheme = true) {
        ActionDetailContent(
            actionDetailState = ActionDetailState(
                action = previewAction,
                notes = listOf(
                    NoteModel(0L, 0L, LocalDate.now(), "Nota de progresso: Hoje eu fiz algo")
                )
            ),
            onBackClicked = {},
            onEditClicked = {}
        )
    }
}