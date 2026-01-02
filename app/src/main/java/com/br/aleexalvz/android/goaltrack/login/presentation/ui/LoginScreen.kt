package com.br.aleexalvz.android.goaltrack.login.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aleexalvz.login.presentation.model.LoginUIState
import com.br.aleexalvz.android.goaltrack.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.components.textfield.PasswordOutlinedTextField
import com.br.aleexalvz.android.goaltrack.login.navigation.LoginRoutes
import com.br.aleexalvz.android.goaltrack.login.presentation.model.LoginUIAction
import com.br.aleexalvz.android.goaltrack.login.presentation.viewmodel.LoginViewModel
import com.br.aleexalvz.android.goaltrack.ui.theme.LightGrayColor
import com.br.aleexalvz.android.goaltrack.ui.theme.LinkTextColor
import com.br.aleexalvz.android.goaltrack.ui.theme.PrimaryButtonColor

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val loginUIState: LoginUIState by loginViewModel.loginState.collectAsStateWithLifecycle()

    LoginScreen(
        modifier = modifier,
        loginUIState = loginUIState,
        onUIAction = loginViewModel::onUIAction,
        onSignupClickListener = { navController.navigate(LoginRoutes.SIGN_UP)}
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginUIState: LoginUIState,
    onUIAction: ((LoginUIAction) -> Unit),
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
            text = "Bem vindo"
        )

        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = loginUIState.email,
            onValueChange = { onUIAction(LoginUIAction.UpdateEmail(it)) },
            labelText = "Email",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email"
                )
            },
            errorMessage = loginUIState.emailError
        )

        PasswordOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = loginUIState.password,
            onValueChange = { onUIAction(LoginUIAction.UpdatePassword(it)) },
            labelText = "Senha",
            contentDescription = "Senha",
            errorMessage = loginUIState.passwordError
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp),
            onClick = {
                onUIAction(LoginUIAction.Login(loginUIState.email, loginUIState.password))
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButtonColor),
            shape = ShapeDefaults.Medium
        ) {
            Text("Entrar")
        }

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = "Ou",
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(containerColor = LightGrayColor),
            shape = ShapeDefaults.Medium
        ) {
            Text(
                text = "Entrar com Goggle",
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
                text = "Novo por aqui? Cadastrar",
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(modifier = Modifier,
        loginUIState = LoginUIState(
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