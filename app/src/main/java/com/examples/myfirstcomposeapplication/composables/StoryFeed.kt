package com.examples.myfirstcomposeapplication.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow.Ellipsis
import androidx.compose.ui.unit.dp
import com.examples.myfirstcomposeapplication.R.drawable
import com.examples.myfirstcomposeapplication.viewmodel.StoryViewModel

@Composable
fun StoriesFeed(stories: List<StoryViewModel>) {
    var count by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            stories.forEachIndexed { index, storyViewModel ->
                Story(title = storyViewModel.title, location = storyViewModel.location, date = storyViewModel.date)
                if (index < stories.size - 1) {
                    StandardSpacing()
                    Divider(color = Color.Black)
                    StandardSpacing()
                }
            }
        }
        Counter(count) { count++ }
    }
}

@Composable
fun Story(title: String, location: String, date: String) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        val imageModifier = Modifier
            .fillMaxWidth()
            .preferredHeight(180.dp)
            .clip(RoundedCornerShape(4.dp))

        Image(
            asset = imageResource(id = drawable.header),
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        StandardSpacing()
        with(MaterialTheme.typography) {
            Text(
                text = title,
                style = h6,
                maxLines = 2,
                overflow = Ellipsis
            )
            Row {
                Text(text = location, style = body2)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = date, style = body2)
            }
        }
    }
}

@Composable
fun Counter(currentCount: Int, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = if (currentCount > 5) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        ),
        onClick = { onClick() }
    ) {
        Text(text = "I've been clicked $currentCount times")
    }
}

@Composable
fun StandardSpacing() {
    Spacer(modifier = Modifier.preferredHeight(16.dp))
}

fun getData(): List<StoryViewModel> {
    return listOf(
        StoryViewModel(
            title = "A day in shark fin cove. This is a very long text for a title that goes over 3 lines in the screen.",
            location = "Davenport, California",
            date = "December 2018"
        ),
        StoryViewModel(
            title = "A day in shark fin cove. This is a very long text for a title that goes over 3 lines in the screen.",
            location = "Davenport, California",
            date = "December 2018"
        )
    )
}
