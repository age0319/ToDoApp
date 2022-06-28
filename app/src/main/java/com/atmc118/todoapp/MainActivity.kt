package com.atmc118.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private val dao = RoomSettingApp.database.todoDao()
    private var todoList = mutableStateListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(todoList)
                }
            }
        }
        loadTodo()
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
                        .padding(16.dp)
                        .clickable(onClick = { deleteTodo(todo) })) {
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
                        postTodo(text)
                        text = ""
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("ADD")
                }
            }
        }
    }

    private fun loadTodo() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.getAll().forEach { todo ->
                    todoList.add(todo)
                }
            }
        }
    }

    private fun postTodo(title: String) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.post(Task(task = title))

                todoList.clear()
                loadTodo()
            }
        }
    }

    private fun deleteTodo(todo: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.delete(todo)

                todoList.clear()
                loadTodo()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ToDoAppTheme {
            MainScreen(todoList)
        }
    }

}


