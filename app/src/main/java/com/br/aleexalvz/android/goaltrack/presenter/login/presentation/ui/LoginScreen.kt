package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.br.aleexalvz.android.goaltrack.presenter.components.button.CheckBox
import com.br.aleexalvz.android.goaltrack.presenter.components.dialog.ErrorDialog
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.PasswordOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.LoginRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginAction
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginEvent
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginState
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val loginState: LoginState by loginViewModel.state.collectAsStateWithLifecycle()

    LoginEventHandler(
        loginViewModel = loginViewModel,
        onLoginSuccess = { navController.navigate(HomeRoutes.HOME_GRAPH) }
    )

    LoginContent(
        modifier = modifier,
        loginState = loginState,
        onUIAction = loginViewModel::onUIAction,
        onSignupClickListener = { navController.navigate(LoginRoutes.SIGN_UP) },
        onRecoveryPasswordClickListener = { navController.navigate(LoginRoutes.RECOVERY_PASSWORD) }
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onUIAction: ((LoginAction) -> Unit),
    onSignupClickListener: (() -> Unit),
    onRecoveryPasswordClickListener: (() -> Unit)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier
                .padding(top = 80.dp)
                .size(96.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 8.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App icon",
                modifier = Modifier.padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = "Goal track",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = stringResource(R.string.app_login_subtitle)
        )

        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
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
                .padding(top = 16.dp),
            text = loginState.password,
            onValueChange = { onUIAction(LoginAction.UpdatePassword(it)) },
            labelText = stringResource(R.string.password),
            contentDescription = stringResource(R.string.password),
            errorMessage = loginState.passwordError,
            onImeAction = { onUIAction(LoginAction.Submit) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CheckBox(
                modifier = Modifier.padding(start = 8.dp),
                horizontalAlignment = Arrangement.Start,
                selected = loginState.rememberMe,
                onStateChanged = { onUIAction(LoginAction.UpdateRememberMeCheckBox(it)) },
                text = "Lembrar de mim",
                textSize = 14.sp
            )

            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onRecoveryPasswordClickListener() },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                text = "Recuperar senha",
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 18.dp),
            onClick = {
                onUIAction(LoginAction.Submit)
            },
            shape = ShapeDefaults.Medium
        ) {
            if (loginState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = stringResource(R.string.login),
                    color = MaterialTheme.colorScheme.onPrimary
                )
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
            shape = ShapeDefaults.Medium,
            enabled = false //TODO feature futura
        ) {
            Text(
                text = stringResource(R.string.login_with_google),
                color = MaterialTheme.colorScheme.onSecondary
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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

    LaunchedEffect(loginViewModel) {
        loginViewModel.event.collect { event ->
            when (event) {
                LoginEvent.OnLoginSuccess -> {
                    onLoginSuccess()
                }

                is LoginEvent.InvalidCredentials -> {
                    showDialog = true
                    dialogTitle = context.getString(R.string.invalid_credentials_error_title)
                    dialogMessage =
                        context.getString(R.string.invalid_credentials_error_message)
                }

                LoginEvent.ConnectionError -> {
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
        onSignupClickListener = {},
        onRecoveryPasswordClickListener = {}
    )
}