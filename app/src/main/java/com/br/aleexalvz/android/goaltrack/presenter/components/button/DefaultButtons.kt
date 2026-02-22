package com.br.aleexalvz.android.goaltrack.presenter.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme

@Composable
fun BasicButton(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String,
    contentColor: Color,
    containerColor: Color,
    borderStroke: BorderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(0.2f)),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = borderStroke,
        shape = shape
    ) {
        Text(text)
    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String
) {
    BasicButton(
        modifier = modifier,
        onClick = onClick,
        text = text,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun SecondaryButton(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String
) {
    BasicButton(
        modifier = modifier,
        onClick = onClick,
        text = text,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    )
}

@Composable
fun AlertButton(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String
) {
    BasicButton(
        modifier = modifier,
        onClick = onClick,
        text = text,
        containerColor = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.error
    )
}

@Preview(name = "Basic Button - Light", showBackground = true)
@Composable
fun BasicButtonLightPreview() {
    GoalTrackTheme {
        BasicButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Basic Button",
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(name = "Basic Button - Dark", showBackground = true)
@Composable
fun BasicButtonDarkPreview() {
    GoalTrackTheme(darkTheme = true) {
        BasicButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Basic Button",
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(name = "Primary Button - Light", showBackground = true)
@Composable
fun PrimaryButtonLightPreview() {
    GoalTrackTheme {
        PrimaryButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Primary Button",
        )
    }
}

@Preview(name = "Primary Button - Dark", showBackground = true)
@Composable
fun PrimaryButtonDarkPreview() {
    GoalTrackTheme(darkTheme = true) {
        PrimaryButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Primary Button",
        )
    }
}

@Preview(name = "Secondary Button - Light", showBackground = true)
@Composable
fun SecondaryButtonLightPreview() {
    GoalTrackTheme {
        SecondaryButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Secondary Button",
        )
    }
}

@Preview(name = "Secondary Button - Dark", showBackground = true)
@Composable
fun SecondaryButtonDarkPreview() {
    GoalTrackTheme(darkTheme = true) {
        SecondaryButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Secondary Button",
        )
    }
}

@Preview(name = "Alert Button - Light", showBackground = true)
@Composable
fun AlertButtonLightPreview() {
    GoalTrackTheme {
        AlertButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Alert Button",
        )
    }
}

@Preview(name = "Alert Button - Dark", showBackground = true)
@Composable
fun AlertButtonDarkPreview() {
    GoalTrackTheme(darkTheme = true) {
        AlertButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
            text = "Alert Button",
        )
    }
}