package com.neutraltbr.passker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InputAndProcessScreen(modifier = Modifier)
        }
    }
}

@Composable
fun InputAndProcessScreen(modifier: Modifier) {
    var passwordCandidateInput by remember {mutableStateOf(TextFieldValue(""))}
    var timesInput by remember {mutableStateOf(TextFieldValue("0"))}
    var result by remember {mutableStateOf("")}
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = passwordCandidateInput,
            onValueChange = { passwordCandidateInput = it },
            label = { Text("Password Candidate") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = timesInput,
            onValueChange = { timesInput = it },
            label = { Text("How many iterations:") },
        )
        Button(
            onClick = {
                result = if (timesInput.text.isBlank()) {
                    processString(passwordCandidateInput.text)
                } else {
                    repeatProcess(passwordCandidateInput.text, timesInput.text.toInt())
                }
                copyToClipboard(context, result)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Create Password")
        }

        Text(
            text = """
                Your password:
                $result
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                copyToClipboard(context, result)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Copy to Clipboard")
        }
    }
}