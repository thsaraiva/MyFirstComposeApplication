package com.examples.myfirstcomposeapplication.composables

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestingScroll() {
    // Create ScrollState to own it and be able to control scroll behaviour of ScrollableRow below
    val scrollState = rememberScrollState()
    Column {
        ScrollableRow(scrollState = scrollState) {
            repeat(1000) { index ->
                Box(
                    Modifier.preferredSize(64.dp).background(MaterialTheme.colors.background),
                    alignment = Alignment.Center
                ) {
                    Text(index.toString())
                }
            }
        }
        // Controls for scrolling
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Scroll")
            Button(onClick = { scrollState.scrollTo(scrollState.value - 1000) }) {
                Text("< -")
            }
            Button(onClick = { scrollState.scrollBy(10000f) }) {
                Text("--- >")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Smooth Scroll")
            Button(onClick = { scrollState.smoothScrollTo(scrollState.value - 1000) }) {
                Text("< -")
            }
            Button(onClick = { scrollState.smoothScrollBy(10000f) }) {
                Text("--- >")
            }
        }
    }
}
