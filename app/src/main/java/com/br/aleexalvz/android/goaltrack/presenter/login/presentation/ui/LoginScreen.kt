package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.R
import com.br.aleexalvz.android.goaltrack.presenter.components.dialog.ErrorDialog
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.PasswordOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.LoginRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginAction
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginEvent
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginState
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel.LoginViewModel
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.LightGrayColor
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.LinkTextColor
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.PrimaryButtonColor

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val loginState: LoginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    LoginEventHandler(
        loginViewModel = loginViewModel,
        onLoginSuccess = { navController.navigate(HomeRoutes.HOME) }
    )

    LoginContent(
        modifier = modifier,
        loginState = loginState,
        onUIAction = loginViewModel::onUIAction,
        onSignupClickListener = { navController.navigate(LoginRoutes.SIGN_UP) }
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onUIAction: ((LoginAction) -> Unit),
    onSignupClickListener: (() -> Unit)
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
            text = stringResource(R.string.welcome)
        )

        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = loginState.email,
            onValueChange = { onUIAction(LoginAction.UpdateEmail(it)) },
            labelText = stringResource(R.string.email),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = stringResource(R.string.email)
                )
            },
            errorMessage = loginState.emailError
        )

        PasswordOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = loginState.password,
            onValueChange = { onUIAction(LoginAction.UpdatePassword(it)) },
            labelText = stringResource(R.string.password),
            contentDescription = stringResource(R.string.password),
            errorMessage = loginState.passwordError,
            onImeAction = { onUIAction(LoginAction.Submit) }

        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp),
            onClick = {
                onUIAction(LoginAction.Submit)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButtonColor),
            shape = ShapeDefaults.Medium
        ) {
            if (loginState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text(stringResource(R.string.login))
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = stringResource(R.string.or),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp),
            onClick = { onUIAction(LoginAction.LoginWithGoogle) },
            colors = ButtonDefaults.buttonColors(containerColor = LightGrayColor),
            shape = ShapeDefaults.Medium,
            enabled = false //TODO feature futura
        ) {
            Text(
                text = stringResource(R.string.login_with_google),
                color = Color.Black
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                modifier = Modifier.clickable { onSignupClickListener() },
                color = LinkTextColor,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.go_to_signup),
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        }

    }
}

@Composable
private fun LoginEventHandler(
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loginViewModel.loginEvents.collect { event ->
            when (event) {
                LoginEvent.OnLoginSuccess -> {
                    onLoginSuccess()
                }

                is LoginEvent.InvalidCredentials -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.invalid_credentials_error_title)
                    dialogMessage = context.getString(R.string.invalid_credentials_error_message)
                }

                LoginEvent.NetworkFailure -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.network_error_title)
                    dialogMessage = context.getString(R.string.network_error_message)
                }

                LoginEvent.UnexpectedError -> {
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

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginContent(modifier = Modifier,
        loginState = LoginState(
            email = "preview@email.com",
            password = "kbdca123",
            rememberMe = true,
            emailError = null,
            passwordError = null
        ),
        onUIAction = {},
        onSignupClickListener = {}
    )
}