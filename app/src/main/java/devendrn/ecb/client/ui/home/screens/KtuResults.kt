package devendrn.ecb.client.ui.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.model.KtuExam
import devendrn.ecb.client.model.KtuExamResult
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun EcKtuResults(
    profileDetails: Map<String, String>,
    ktuExams: List<KtuExam>,
    ktuExamResults: List<KtuExamResult>,
    ktuExamResultsLoading: Boolean,
    ktuExamSelected: String,
    onSelectExam: (name: String, schemeId: Int, examDefId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ExamSelectDropMenu(
            expanded = expanded,
            onExpandOrDismiss = { expanded = it },
            selectedExamText = ktuExamSelected,
            ktuExams = ktuExams,
            onSelectExam = { name, schemeId, examDefId ->
                onSelectExam(name, schemeId, examDefId)
            }
        )
        OutlinedCard(
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                profileDetails.forEach {
                    Row(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = it.key,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = it.value
                        )
                    }
                }
            }
        }
        CourseGradeTable(
            loading = ktuExamResultsLoading,
            ktuExamResults = ktuExamResults
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExamSelectDropMenu(
    expanded: Boolean,
    onExpandOrDismiss: (Boolean) -> Unit,
    selectedExamText: String,
    ktuExams: List<KtuExam>,
    onSelectExam: (name: String, schemeId: Int, examDefId: Int) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandOrDismiss,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedExamText,
            onValueChange = {},
            label = { Text("Exam") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandOrDismiss(false) }
        ) {
            ktuExams.forEach { exam ->
                DropdownMenuItem(
                    text = { Text(exam.name) },
                    onClick = {
                        onSelectExam(exam.name, exam.schemeId, exam.examDefId)
                        onExpandOrDismiss(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
private fun CourseGradeTable(
    loading: Boolean,
    ktuExamResults: List<KtuExamResult>,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.padding(15.dp))
            } else if (ktuExamResults.isEmpty()) {
                Text(
                    text ="No result found for selected exam!",
                    modifier = Modifier.padding(15.dp)
                )
            } else {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                ) {
                    Text(
                        text ="Course",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text ="Grade",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                ktuExamResults.forEach {
                    Divider()
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = it.grade,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.width(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EcKtuResultsPreview() {
    EcTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            EcKtuResults(
                profileDetails = mapOf(
                    "Name" to "Maxim Nausea",
                    "Date of birth" to "12/07/2020",
                    "Reg No" to "TKM21CAT07"
                ),
                ktuExamResultsLoading = false,
                ktuExams = listOf(
                    KtuExam("B.TECH", 1, 1)
                ),/*
                ktuExamResults = listOf(
                    KtuExamResult("AEROBATICS", "C+"),
                    KtuExamResult("CATNIP BIOCHEMISTRY", "A"),
                    KtuExamResult("MEOW LANGUAGE", "S")
                ),*/
                ktuExamSelected = "Select an exam",
                ktuExamResults = listOf(),
                onSelectExam = { _, _, _ -> }
            )
        }
    }
}
