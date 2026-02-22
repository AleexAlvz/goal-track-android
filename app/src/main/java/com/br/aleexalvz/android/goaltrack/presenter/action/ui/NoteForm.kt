package com.br.aleexalvz.android.goaltrack.presenter.action.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.aleexalvz.android.goaltrack.presenter.components.button.PrimaryButton
import com.br.aleexalvz.android.goaltrack.presenter.components.button.SecondaryButton
import com.br.aleexalvz.android.goaltrack.presenter.components.textfield.DefaultOutlinedTextField
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme

@Composable
fun NoteForm(
    modifier: Modifier = Modifier,
    onNoteSaved: (note: String) -> Unit = {},
    onCanceled: () -> Unit = {}
) {

    var text by remember { mutableStateOf("") }

    Surface(
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp, color =
            MaterialTheme.colorScheme.outline.copy(0.2f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            DefaultOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = text,
                labelText = "Adicionar nota rápida...",
                onValueChange = { text = it }
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SecondaryButton(
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .weight(1f),
                    onClick = onCanceled,
                    text = "Cancelar"
                )

                PrimaryButton(
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .weight(1f),
                    onClick = { onNoteSaved(text) },
                    text = "Salvar nota"
                )
            }
        }
    }


}

@Preview(name = "Note Form - Light", showBackground = true)
@Composable
private fun NoteFormLightPreview() {
    GoalTrackTheme {
        NoteForm(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(name = "Note Form - Dark", showBackground = true)
@Composable
private fun NoteFormDarkPreview() {
    GoalTrackTheme(darkTheme = true) {
        NoteForm(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}