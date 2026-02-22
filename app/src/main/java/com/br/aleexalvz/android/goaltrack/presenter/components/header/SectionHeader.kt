package com.br.aleexalvz.android.goaltrack.presenter.components.header

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(
    sectionIcon: ImageVector,
    sectionTitle: String,
    sectionAction: String? = null,
    onSectionActionClicked: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.padding(start = 16.dp),
            imageVector = sectionIcon,
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = sectionTitle,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.weight(1f))
        sectionAction?.let {
            Text(
                text = sectionAction,
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onSectionActionClicked?.invoke() }
            )
        }
    }
}