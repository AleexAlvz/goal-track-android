package com.br.aleexalvz.android.goaltrack.presenter.components.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputChips(
    values: List<String>,
    onValuesChange: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    var text by remember { mutableStateOf("") }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        values.forEach { value ->
            InputChip(
                selected = true,
                onClick = { },
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(0.2f)
                ),
                shape = RoundedCornerShape(8.dp),
                label = {
                    Text(
                        text = value,
                        fontSize = 14.sp
                    )
                },
                colors = InputChipDefaults.inputChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = MaterialTheme.colorScheme.surface,
                    selectedLabelColor = MaterialTheme.colorScheme.onSurface,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                ),
                trailingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remover",
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { onValuesChange(values - value) }
                    )
                }
            )
        }

        Surface(
            modifier = Modifier
                .defaultMinSize(minHeight = 32.dp)
                .align(Alignment.CenterVertically),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(0.2f)
            ),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            BasicTextField(
                modifier = Modifier
                    .widthIn(min = 80.dp)
                    .padding(horizontal = 12.dp),
                value = text,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                onValueChange = { text = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        addValueIfValid(
                            text = text,
                            values = values,
                            onValuesChange = onValuesChange,
                            onClear = { text = "" }
                        )
                    }
                )
            ) { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(Modifier.weight(1f)) {
                        if (text.isEmpty()) {
                            Text(
                                text = label.orEmpty(),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                        innerTextField()
                    }
                }
            }
        }
    }
}

private fun addValueIfValid(
    text: String,
    values: List<String>,
    onValuesChange: (List<String>) -> Unit,
    onClear: () -> Unit
) {
    val newValue = text.trim()

    if (newValue.isNotBlank() && !values.contains(newValue)) {
        onValuesChange(values + newValue)
    }

    onClear()
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun InputChipsPreview() {
    GoalTrackTheme {
        InputChips(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            values = listOf("skill1"),
            onValuesChange = { },
            label = "Adicionar habilidade"
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun InputChipsPreviewDark() {
    GoalTrackTheme(darkTheme = true) {
        InputChips(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            values = listOf("skill1", "skill2"),
            onValuesChange = { },
            label = "Adicionar habilidade"
        )
    }
}