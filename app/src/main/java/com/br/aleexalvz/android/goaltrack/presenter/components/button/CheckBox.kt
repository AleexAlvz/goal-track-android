package com.aleexalvz.designsystem.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.Green
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GraySuperLight

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
            checkedColor = GraySuperLight,
            uncheckedColor = GraySuperLight,
            checkmarkColor = Green,
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
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun CheckBoxPreview() {
    CheckBox(
        selected = true,
        onStateChanged = {},
        text = "Check box text"
    )
}

