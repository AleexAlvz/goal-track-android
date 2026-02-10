package com.br.aleexalvz.android.goaltrack.presenter.goal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailState
import com.br.aleexalvz.android.goaltrack.presenter.goal.viewmodel.GoalDetailViewModel
import java.time.LocalDate

@Composable
fun GoalDetailScreen(
    navController: NavController,
    viewModel: GoalDetailViewModel = hiltViewModel()
) {

    val goalDetailState by viewModel.goal.collectAsState()

    GoalDetailContent(
        modifier = Modifier,
        goalDetailState = goalDetailState,
        onBackClicked = { navController.popBackStack() }
    )
}

@Composable
fun GoalDetailContent(
    modifier: Modifier = Modifier,
    goalDetailState: GoalDetailState,
    onBackClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.padding(start = 8.dp),
                onClick = onBackClicked
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
            Text(
                text = "Meta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(end = 56.dp))
        }

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DetailItem(label = "Título", value = goalDetailState.goal?.title.orEmpty())
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem(label = "Descrição", value = goalDetailState.goal?.description.orEmpty())
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem(label = "Status", value = goalDetailState.goal?.status?.toString().orEmpty())
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoalDetailScreenPreview() {
    val previewGoal = GoalModel(
        id = 1,
        title = "Correr 5km na semana",
        description = "Manter o hábito de correr para melhorar o condicionamento físico e a saúde.",
        status = GoalStatusEnum.IN_PROGRESS,
        category = GoalCategoryEnum.CAREER,
        creationDate = LocalDate.now()
    )
    GoalDetailContent(
        goalDetailState = GoalDetailState(goal = previewGoal),
        onBackClicked = {}
    )
}