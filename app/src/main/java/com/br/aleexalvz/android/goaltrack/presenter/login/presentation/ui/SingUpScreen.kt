package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.R
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.PasswordOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.LoginRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.SignUpAction
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.SignUpEvent
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.SignupState
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    SignupEventHandler(
        viewModel = viewModel,
        onSignupSuccess = {
            Toast.makeText(
                context,
                context.getString(R.string.signup_with_success), Toast.LENGTH_SHORT
            ).show()
            navController.navigate(LoginRoutes.LOGIN) {
                popUpTo(LoginRoutes.SIGN_UP) { inclusive = true }
            }
        }
    )

    SignupContent(
        modifier = modifier,
        state = state,
        onUIAction = viewModel::onUIAction
    )
}

@Composable
private fun SignupEventHandler(
    viewModel: SignUpViewModel,
    onSignupSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignUpEvent.OnSignUpSuccess -> onSignupSuccess()

                SignUpEvent.InvalidCredentials -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.invalid_credentials_error_title)
                    dialogMessage = context.getString(R.string.invalid_credentials_error_message)
                }

                SignUpEvent.ConnectionError -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.network_error_title)
                    dialogMessage = context.getString(R.string.network_error_message)
                }

                SignUpEvent.UnexpectedError -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.unexpected_error_title)
                    dialogMessage = context.getString(R.string.unexpected_error_message)
                }
            }
        }
    }
}

@Composable
fun SignupContent(
    modifier: Modifier = Modifier,
    state: SignupState,
    onUIAction: (SignUpAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "Crie sua conta"
        )

        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = state.email,
            onValueChange = { onUIAction(SignUpAction.UpdateEmail(it)) },
            labelText = "Email",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email"
                )
            },
            errorMessage = state.emailError
        )

        PasswordOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = state.password,
            onValueChange = { onUIAction(SignUpAction.UpdatePassword(it)) },
            labelText = "Senha",
            contentDescription = "Senha",
            errorMessage = state.passwordError
        )

        PasswordOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = state.confirmPassword,
            onValueChange = { onUIAction(SignUpAction.UpdateConfirmPassword(it)) },
            labelText = "Confirme sua senha",
            contentDescription = "Confirme sua senha",
            errorMessage = state.confirmPasswordError
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = { onUIAction(SignUpAction.Submit) },
            enabled = !state.isLoading,
            shape = ShapeDefaults.Medium
        ) {
            Text(
                text = if (state.isLoading) "Criando conta..." else "Criar conta"
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignupContent(
        modifier = Modifier,
        state = SignupState(
            email = "preview@email.com",
            password = "kbdca123",
            confirmPassword = "kbdca123",
            emailError = null,
            passwordError = null,
            confirmPasswordError = null
        ),
        onUIAction = {}
    )
}