package com.aleexalvz.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.aleexalvz.android.goaltrack.ui.theme.GradGreenButton1
import com.br.aleexalvz.android.goaltrack.ui.theme.GradGreenButton2
import com.br.aleexalvz.android.goaltrack.ui.theme.GradGreenButton3

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit,
    text: String,
    brush: Brush
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
        onClick = { onClickListener() },
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.wrapContentSize(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun GradientButtonPreview() {
    GradientButton(
        text = "Click me",
        onClickListener = {},
        modifier = Modifier
            .padding(10.dp)
            .width(232.dp)
            .height(50.dp),
        brush = Brush.verticalGradient(
            listOf(
                GradGreenButton1, GradGreenButton2, GradGreenButton3
            )
        )
    )
}