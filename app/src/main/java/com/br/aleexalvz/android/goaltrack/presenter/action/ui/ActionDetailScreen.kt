package com.br.aleexalvz.android.goaltrack.presenter.action.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.br.aleexalvz.android.goaltrack.domain.model.note.NoteModel
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionDetailAction
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionDetailState
import com.br.aleexalvz.android.goaltrack.presenter.action.viewmodel.ActionDetailViewModel
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

    ActionDetailContent(
        actionDetailState = actionDetailState,
        onBackClicked = { navController.popBackStack() },
        onNavigateToEditAction = { actionId ->
            navController.navigate(
                HomeRoutes.actionFormEditMode(
                    actionId
                )
            )
        },
        onUIAction = {
            viewModel.onUIAction(it)
        }
    )
}

@Composable
fun ActionDetailContent(
    actionDetailState: ActionDetailState,
    onBackClicked: () -> Unit = {},
    onNavigateToEditAction: (actionId: Long) -> Unit = {},
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
                        text = "Detalhes da Ação",
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
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                        Text(
                            text = actionDetailState.action?.frequency?.getMessage(context)
                                .orEmpty(),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
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
                            onUIAction(ActionDetailAction.addNote(note))
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
            )
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
            )
        )
    }
}