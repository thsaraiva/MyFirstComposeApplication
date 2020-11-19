package com.examples.myfirstcomposeapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition.End
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.examples.myfirstcomposeapplication.composables.StandardSpacing
import com.examples.myfirstcomposeapplication.ui.MyFirstComposeApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainScreen {
                    Toast.makeText(this, "Love Jetpack Compose!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MyFirstComposeApplicationTheme {
        content()
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen(onFavoriteIconClick: () -> Unit) {
    val scaffoldState = rememberScaffoldState(drawerState = rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppTopBar({ scaffoldState.drawerState.open() }, { onFavoriteIconClick() })
        },
        drawerContent = {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
                onClick = { scaffoldState.drawerState.close() },
                content = { Text("Close Drawer") }
            )
        },
        floatingActionButtonPosition = End,
        isFloatingActionButtonDocked = false,
        floatingActionButton = {
            Column {
                val scope = rememberCoroutineScope()
                var count by remember { mutableStateOf(0) }
                FloatingActionButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("This snackbar was clicked ${++count} times")
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add)
                }
                StandardSpacing()
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Filled.ArrowDropDown) },
                    text = { Text("Extended FAB") },
                    onClick = { /*do something*/ }
                )
            }
        },
        bodyContent = { innerPadding ->
            BodyContent(Modifier.padding(innerPadding), scaffoldState)
        },
        bottomBar = {
            BottomNavigationSample()
        }
    )
}

@Composable
fun AppTopBar(onNavigationIconClick: () -> Unit, onFavoriteIconClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClick() }) {
                Icon(asset = Icons.Filled.Menu)
            }
        },
        title = { Text(text = "App Toolbar") },
        actions = {
            IconButton(onClick = { onFavoriteIconClick() }) {
                Icon(asset = Icons.Filled.Favorite)
            }
        }
    )
}

@Composable
fun BodyContent(modifier: Modifier = Modifier, scaffoldState: ScaffoldState) {
//    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
    Column(
//            modifier = Modifier.weight(1f).padding(16.dp),
        modifier = Modifier.fillMaxHeight().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(modifier)
        Text(text = if (scaffoldState.drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
        Spacer(Modifier.preferredHeight(20.dp))
        Button(onClick = { scaffoldState.drawerState.open() }) {
            Text("Click to open")
        }
    }
//        BottomNavigationSample()
//    }
}

@Composable
fun BottomNavigationSample() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Songs", "Artists", "Playlists")

    BottomNavigation {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Composable
fun ProfileCard(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { /* do nothing */ })
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier
                .preferredSize(50.dp)
                .align(Alignment.CenterVertically),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // image goes here
        }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun DefaultPreview() {
    MainScreen { /* do something */ }
}
