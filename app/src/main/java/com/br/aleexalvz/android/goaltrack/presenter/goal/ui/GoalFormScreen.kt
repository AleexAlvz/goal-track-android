package com.br.aleexalvz.android.goaltrack.presenter.goal.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.R
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.toGoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.presenter.components.chip.InputChips
import com.br.aleexalvz.android.goaltrack.presenter.components.dialog.ErrorDialog
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.TextFieldWithDropDown
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.CreateGoalEvent
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalFormAction
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalFormState
import com.br.aleexalvz.android.goaltrack.presenter.goal.viewmodel.GoalFormViewModel
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme

@Composable
fun GoalFormScreen(
    navController: NavController,
    goalFormViewModel: GoalFormViewModel = hiltViewModel()
) {

    val goalState by goalFormViewModel.state.collectAsState()

    GoalFormEventHandler(
        goalFormViewModel = goalFormViewModel,
        onCreateSuccessfully = {
            navController.navigate(HomeRoutes.GOALS) {
                popUpTo(HomeRoutes.GOAL_FORM) {
                    inclusive = true
                }
            }
        }
    )

    GoalFormContent(
        goalFormState = goalState,
        onUIAction = goalFormViewModel::onUIAction,
        onBackClicked = { navController.popBackStack() }
    )
}

@Composable
fun GoalFormContent(
    goalFormState: GoalFormState,
    onUIAction: (GoalFormAction) -> Unit,
    onBackClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = { onUIAction(GoalFormAction.Submit) }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Salvar meta",
                fontSize = 16.sp
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp),
                onClick = { onBackClicked() },
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = "Criar nova meta",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp)
                    .clickable { onBackClicked() },
                text = "Cancelar",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = goalFormState.title,
            labelText = "Título da meta",
            onValueChange = { onUIAction(GoalFormAction.UpdateTitle(it)) },
            errorMessage = goalFormState.titleError
        )

        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp)
                .padding(start = 16.dp, end = 16.dp),
            text = goalFormState.description,
            labelText = "Descrição",
            onValueChange = { onUIAction(GoalFormAction.UpdateDescription(it)) },
            errorMessage = goalFormState.descriptionError
        )

        TextFieldWithDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            dropDownValues = GoalCategoryEnum.entries.map { it.name },
            text = goalFormState.category?.name.orEmpty(),
            errorMessage = goalFormState.categoryError,
            labelText = "Categoria",
            onSelectedItem = { onUIAction(GoalFormAction.UpdateCategory(it.toGoalCategoryEnum())) }
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Habilidades a serem desenvolvidas",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.padding(8.dp))

        InputChips(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            values = goalFormState.skills.map { it.name },
            onValuesChange = { onUIAction(GoalFormAction.UpdateSkills(it)) },
            label = "Adicionar habilidade"
        )
    }
}

@Composable
fun GoalFormEventHandler(
    goalFormViewModel: GoalFormViewModel,
    onCreateSuccessfully: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(goalFormViewModel) {
        goalFormViewModel.event.collect { event ->
            when (event) {
                CreateGoalEvent.Created -> onCreateSuccessfully()

                CreateGoalEvent.ConnectionError -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.network_error_title)
                    dialogMessage = context.getString(R.string.network_error_message)
                }

                CreateGoalEvent.InvalidParams -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.form_dialog_title_generic_failure)
                    dialogMessage =
                        context.getString(R.string.form_dialog_message_missing_required_fields)
                }

                CreateGoalEvent.UnexpectedError -> {
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

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GoalFormScreenPreview() {
    GoalTrackTheme {
        GoalFormContent(
            goalFormState = GoalFormState(),
            onUIAction = { },
            onBackClicked = { }
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GoalFormScreenPreviewDark() {
    GoalTrackTheme(darkTheme = true) {
        GoalFormContent(
            goalFormState = GoalFormState(),
            onUIAction = { },
            onBackClicked = { }
        )
    }
}