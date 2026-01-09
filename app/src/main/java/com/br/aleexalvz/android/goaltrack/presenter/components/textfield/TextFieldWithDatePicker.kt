package com.br.aleexalvz.android.goaltrack.presenter.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithDatePicker(
    modifier: Modifier,
    text: String,
    onSelectedDateMillis: (Long) -> Unit,
    labelText: String = "Date"
) {
    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    DefaultOutlinedTextField(
        text = text,
        onValueChange = {},
        labelText = labelText,
        readOnly = true,
        modifier = modifier.onFocusChanged {
            if (it.isFocused) {
                showDatePickerDialog = true
                focusManager.clearFocus(force = true)
            }
        }
    )

    if (showDatePickerDialog) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
//                containerColor = GrayLight
            ),
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onSelectedDateMillis(it)
                        }
                        showDatePickerDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Green
                    )
                ) {
                    Text(text = "Confirm date")
                }
            }
        ) {
//            DatePicker(
//                state = datePickerState,
//                colors = DatePickerDefaults.colors(
//                    titleContentColor = Color.Green,
//                    weekdayContentColor = Color.White,
//                    dayContentColor = Color.White,
//                    containerColor = GrayLight,
//                    selectedDayContainerColor = Green,
//                    selectedDayContentColor = Color.White,
//                    selectedYearContainerColor = Green,
//                    selectedYearContentColor = Color.White,
//                    dividerColor = Green,
//                    headlineContentColor = Green,
//                    subheadContentColor = Color.White,
//                    navigationContentColor = Color.White,
//                    todayDateBorderColor = Green,
//                    todayContentColor = Green,
//                    currentYearContentColor = Color.White,
//                    yearContentColor = Color.White,
//                    dayInSelectionRangeContentColor = Color.White,
//                    dayInSelectionRangeContainerColor = Color.White
//                )
//            )
        }
    }
}

@Preview
@Composable
fun TextFieldWithDatePickerPreview() {
    TextFieldWithDatePicker(
        modifier = Modifier.fillMaxWidth(),
        text = "17/08/2024",
        onSelectedDateMillis = {}
    )
}