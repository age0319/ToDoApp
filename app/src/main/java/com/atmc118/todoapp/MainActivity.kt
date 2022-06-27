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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atmc118.todoapp.ui.theme.ToDoAppTheme
import java.text.SimpleDateFormat
import java.util.*

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
                    var todoList: SnapshotStateList<Task> by remember { mutableStateOf(mutableStateListOf()) }
                    val task = Task()
                    task.task = "買い物に行く"
                    task.time = Date()
                    todoList.add(task)
                    MainScreen(todoList)
                }
            }
        }
    }
}

@Composable
fun MainScreen(todoList: SnapshotStateList<Task>) {

    var text: String by remember { mutableStateOf("") }
    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

    Column {
        TopAppBar(
            title = { Text("Todo List") }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(todoList) { todo ->
                var checkedState:Boolean by remember { mutableStateOf(false) }
                Row( modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = {
                            checkedState = it
                        })
                    if(checkedState) {
                        Text(
                            text = todo.task,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough),
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }else{
                        Text(
                            text = todo.task,
                            modifier = Modifier
                                .padding(16.dp))
                    }
                    Text(
                        text = sdf.format(todo.time),
                        color = Color.LightGray,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {text = it},
                label = { Text("ToDo") },
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    if (text.isEmpty()) return@Button
                    val task = Task()
                    task.task = text
                    task.time = Date()
                    todoList.add(task)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("ADD")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoAppTheme {
        var todoList: SnapshotStateList<Task> by remember { mutableStateOf(mutableStateListOf()) }
        var task = Task()
        task.task = "買い物に行く"
        task.time = Date()
        todoList.add(task)
        task = Task()
        task.task = "掃除をする"
        task.time = Date()
        todoList.add(task)
        MainScreen(todoList)
    }
}