package com.br.aleexalvz.android.goaltrack.presenter.components.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme

@Composable
fun InformativeChip(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(0.2f)
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = borderColor),
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

@Preview(name = "Light Mode")
@Composable
fun InformativeChipPreviewLight() {
    GoalTrackTheme {
        InformativeChip(text = "Texto")
    }
}

@Preview(name = "Dark Mode")
@Composable
fun InformativeChipPreviewDark() {
    GoalTrackTheme(darkTheme = true) {
        InformativeChip(text = "Texto")
    }
}