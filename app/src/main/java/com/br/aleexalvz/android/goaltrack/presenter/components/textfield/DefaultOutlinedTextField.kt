package com.br.aleexalvz.android.goaltrack.presenter.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun DefaultOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    labelText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    prefix: String? = null,
    readOnly: Boolean? = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    OutlinedTextField(
        modifier = modifier,
        value = text,
        textStyle = TextStyle(fontSize = 14.sp),
        trailingIcon = {
            trailingIcon ?: errorMessage?.let {
                Icon(imageVector = Icons.Filled.Warning, "Error icon")
            }
        },
        prefix = {
            prefix?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
        },
        isError = (errorMessage != null),
        supportingText = {
            errorMessage?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        label = {
            labelText?.let {
                Text(
                    text = labelText,
                    fontSize = 14.sp,
                )
            }
        },
        leadingIcon = leadingIcon,
        readOnly = readOnly ?: false,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}