package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aleexalvz.login.presentation.model.SignUpAction
import com.aleexalvz.login.presentation.model.SignUpState
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.PasswordOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.LoginRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel.SignUpViewModel
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.PrimaryButtonColor

@Composable
fun SignUpScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    signupViewModel: SignUpViewModel = hiltViewModel()
) {

    val loginUIState: SignUpState by signupViewModel.signupState.collectAsStateWithLifecycle()

    SignUpScreen(
        modifier = modifier,
        signupState = loginUIState,
        onUIAction = signupViewModel::onUIAction,
        onSignupButtonClick = { navController.navigate(LoginRoutes.SIGN_UP)}
    )
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signupState: SignUpState,
    onUIAction: ((SignUpAction) -> Unit),
    onSignupButtonClick: (() -> Unit)
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
            text = signupState.email,
            onValueChange = { onUIAction(SignUpAction.UpdateEmail(it)) },
            labelText = "Email",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email"
                )
            },
            errorMessage = signupState.emailError
        )

        PasswordOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = signupState.password,
            onValueChange = { onUIAction(SignUpAction.UpdatePassword(it)) },
            labelText = "Senha",
            contentDescription = "Senha",
            errorMessage = signupState.passwordError
        )

        PasswordOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = signupState.confirmPassword,
            onValueChange = { onUIAction(SignUpAction.UpdateConfirmPassword(it)) },
            labelText = "Confirme sua senha",
            contentDescription = "Confirme sua senha",
            errorMessage = signupState.passwordError
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButtonColor),
            shape = ShapeDefaults.Medium
        ) {
            Text("Criar conta")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(modifier = Modifier,
        signupState = SignUpState(
            email = "preview@email.com",
            password = "kbdca123",
            confirmPassword = "kbdca123",
            emailError = null,
            passwordError = null,
            confirmPasswordError = null
        ),
        onUIAction = {},
        onSignupButtonClick = {}
    )
}