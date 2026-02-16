package com.br.aleexalvz.android.goaltrack.presenter.action.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.domain.model.action.getMessage
import com.br.aleexalvz.android.goaltrack.domain.model.action.toActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormAction
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormEvent
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormState
import com.br.aleexalvz.android.goaltrack.presenter.action.viewmodel.ActionFormViewModel
import com.br.aleexalvz.android.goaltrack.presenter.components.dialog.ErrorDialog
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.TextFieldWithDropDown
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes

@Composable
fun ActionFormScreen(
    navController: NavController,
    actionFormViewModel: ActionFormViewModel = hiltViewModel()
) {
    val actionState by actionFormViewModel.state.collectAsState()

    ActionFormEventHandler(
        actionFormViewModel = actionFormViewModel,
        onCreateSuccessfully = {
            navController.navigate(HomeRoutes.goalDetailWithId(actionState.goalId)) {
                popUpTo(HomeRoutes.goalDetailWithId(actionState.goalId)) {
                    inclusive = true
                }
            }
        }
    )

    ActionFormContent(
        actionState = actionState,
        onUIAction = actionFormViewModel::onUIAction,
        onBackClicked = { navController.popBackStack() }
    )
}

@Composable
fun ActionFormContent(
    actionState: ActionFormState,
    onUIAction: (ActionFormAction) -> Unit,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = { onUIAction(ActionFormAction.Submit) }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Salvar ação",
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
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                onClick = { onBackClicked() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Criar nova ação",
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
            text = actionState.title,
            labelText = "Título da ação",
            onValueChange = { onUIAction(ActionFormAction.UpdateTitle(it)) },
            errorMessage = actionState.titleError
        )

        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = actionState.description.orEmpty(),
            labelText = "Descrição",
            onValueChange = { onUIAction(ActionFormAction.UpdateDescription(it)) },
            errorMessage = actionState.descriptionError
        )

        TextFieldWithDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            dropDownValues = ActionFrequencyEnum.entries.map { it.name },
            text = actionState.frequency.getMessage(context),
            labelText = "Frequência",
            onSelectedItem = { onUIAction(ActionFormAction.UpdateFrequency(it.toActionFrequencyEnum())) }
        )
    }
}

@Composable
fun ActionFormEventHandler(
    actionFormViewModel: ActionFormViewModel,
    onCreateSuccessfully: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(actionFormViewModel) {
        actionFormViewModel.event.collect { event ->
            when (event) {
                is ActionFormEvent.Created -> onCreateSuccessfully()
                is ActionFormEvent.ConnectionError -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.network_error_title)
                    dialogMessage = context.getString(R.string.network_error_message)
                }

                is ActionFormEvent.InvalidParams -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.form_dialog_title_generic_failure)
                    dialogMessage =
                        context.getString(R.string.form_dialog_message_missing_required_fields)
                }

                is ActionFormEvent.UnexpectedError -> {
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

@Preview(showBackground = true)
@Composable
fun ActionFormScreenPreview() {
    ActionFormContent(
        actionState = ActionFormState(),
        onUIAction = { },
        onBackClicked = { }
    )
}