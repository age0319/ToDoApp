package com.atmc118.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atmc118.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
                    val todoList = (1..100).map { "タスク ${it}" }
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    var todoList: SnapshotStateList<String> by remember { mutableStateOf(mutableStateListOf()) }
    var text: String by remember { mutableStateOf("") }

    Column {
        TopAppBar(
            title = { Text("Todo List") }
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            items(todoList) { todo ->
                Text(
                    text = todo,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {text = it},
                label = { Text("ToDo") },
                modifier = Modifier.wrapContentHeight().weight(1f)
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    if (text.isEmpty()) return@Button
                    todoList.add(text)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("ADD")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoAppTheme {
        Greeting("Android")
    }
}