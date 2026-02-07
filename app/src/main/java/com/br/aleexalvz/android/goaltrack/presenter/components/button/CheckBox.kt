package com.br.aleexalvz.android.goaltrack.presenter.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckBox(
    modifier: Modifier = Modifier,
    horizontalAlignment: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    selected: Boolean,
    onStateChanged: (Boolean) -> Unit,
    text: String? = null,
    textSize: TextUnit = 14.sp
) {
    val state = rememberSaveable { mutableStateOf(selected) }

    Row(
        modifier = modifier
            .toggleable(
                value = state.value,
                onValueChange = {
                    state.value = !state.value
                    onStateChanged(state.value)
                },
                role = Role.Checkbox
            ),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalAlignment
    ) {
        val checkBoxColors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.primary,
            checkmarkColor = MaterialTheme.colorScheme.surface,
            uncheckedColor = MaterialTheme.colorScheme.primary,
        )

        Checkbox(
            modifier = Modifier.size(20.dp),
            checked = state.value,
            onCheckedChange = {
                state.value = !state.value
                onStateChanged(state.value)
            },
            colors = checkBoxColors,
        )
        text?.let {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = text,
                fontSize = textSize,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(name = "Checked", showBackground = true)
@Composable
private fun CheckedBoxPreview() {
    CheckBox(
        selected = true,
        onStateChanged = {},
        text = "Check box text"
    )
}

@Preview(name = "Unchecked", showBackground = true)
@Composable
private fun UncheckedBoxPreview() {
    CheckBox(
        selected = false,
        onStateChanged = {},
        text = "Check box text"
    )
}

