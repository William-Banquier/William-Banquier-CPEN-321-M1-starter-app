package com.cpen321.usermanagement.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cpen321.usermanagement.ui.viewmodels.MainViewModel
import com.cpen321.usermanagement.ui.viewmodels.MainUiState
import com.cpen321.usermanagement.data.remote.dto.EventData // Correct import for your EventData

@Composable
fun EventDisplayScreen(
    mainViewModel: MainViewModel = viewModel(),
) {

    val uiState by mainViewModel.uiState.collectAsState()

    Log.i("EventDisplayScreen", "Recomposing EventDisplayScreen")

    EventDisplayContent(
        uiState = uiState,
        onLoadEventClick = { mainViewModel.fetchEvent() }
    )
}

@Composable
fun EventDisplayContent(
    uiState: MainUiState,
    onLoadEventClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            }

            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Correctly display properties from your EventData model
            if (!uiState.isLoading && uiState.errorMessage == null && uiState.eventData != null) {
                uiState.eventData?.let { event ->
                    Text(
                        // Use the actual properties: name, link, location, user
                        text = "Event Name: ${event.name}\n" +
                                "Link: ${event.link}\n" +
                                "Location: ${event.location}\n" +
                                "User: ${event.user}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (!uiState.isLoading && uiState.eventData == null && uiState.errorMessage == null) {
                Text(
                    text = "Click the button to load event data.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = onLoadEventClick,
                modifier = Modifier.fillMaxWidth(0.8f),
                enabled = !uiState.isLoading
            ) {
                Text(text = if (uiState.isLoading) "Loading..." else "Load Event Data")
            }
        }
    }
}

// --- Previews ---

@Preview(name = "Default State (Empty)", showBackground = true)
@Composable
fun EventDisplayContentPreviewEmpty() {
    MaterialTheme {
        EventDisplayContent(
            uiState = MainUiState(isLoading = false, eventData = null, errorMessage = null),
            onLoadEventClick = {}
        )
    }
}

@Preview(name = "Loading State", showBackground = true)
@Composable
fun EventDisplayContentPreviewLoading() {
    MaterialTheme {
        EventDisplayContent(
            uiState = MainUiState(isLoading = true, eventData = null, errorMessage = null),
            onLoadEventClick = {}
        )
    }
}

@Preview(name = "Error State", showBackground = true)
@Composable
fun EventDisplayContentPreviewError() {
    MaterialTheme {
        EventDisplayContent(
            uiState = MainUiState(isLoading = false, eventData = null, errorMessage = "Failed to load event data."),
            onLoadEventClick = {}
        )
    }
}

@Preview(name = "Data Loaded State", showBackground = true)
@Composable
fun EventDisplayContentPreviewDataLoaded() {
    MaterialTheme {
        EventDisplayContent(
            uiState = MainUiState(
                isLoading = false,
                // Use the correct EventData constructor and properties
                eventData = EventData(
                    name = "Tech Conference 2024",
                    link = "http://example.com/techconf",
                    location = "Online",
                    user = "userId123"
                ),
                errorMessage = null
            ),
            onLoadEventClick = {}
        )
    }
}
