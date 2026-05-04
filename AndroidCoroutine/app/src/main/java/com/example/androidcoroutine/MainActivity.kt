package com.example.androidcoroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.androidcoroutine.ui.theme.AndroidCoroutineTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCoroutineTheme {

                var count by remember { mutableIntStateOf(0) }
                var count2 by remember { mutableIntStateOf(0) }
                val scope = rememberCoroutineScope()
                val snackBarState = remember { SnackbarHostState() }

                LaunchedEffect(count) {
                    delay(1000L)
                    //Thread.sleep(1000L)
                    Log.d("스레드", Thread.currentThread().name)
                    count++
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            snackBarState
                        )
                    }) { innerPadding ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(paddingValues = innerPadding),
                                text = "$count",
                                fontSize = 100.sp
                            )
                            Text(
                                modifier = Modifier.padding(innerPadding),
                                text = "$count2",
                                fontSize = 100.sp
                            )
                        }
                        Button(onClick = {}) {
                            Text("버튼1")
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    delay(2000L)
                                    //count--
                                    count2++
                                }
                            }) {
                            Text("버튼2")
                        }
                        Button(
                            onClick = {
                                runBlocking {
                                    delay(2000L)
                                    //count--
                                    count2++
                                }
                            }) {
                            Text("버튼3")
                        }
                        Button(
                            onClick = {
                                runBlocking {
                                    val defferedResult = async {
                                        delay(2000L)
                                        return@async 2
                                    }
                                    Log.d("async", "아무거나")
                                    val result = defferedResult.await()
                                    count2 += result
                                }
                            }) {
                            Text("버튼4")
                        }
                        Button(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    Log.d("Thread", Thread.currentThread().name)
                                }
                            }
                        ) {
                            Text("버튼5")
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    snackBarState.showSnackbar(
                                        message = "스낵바",
                                        actionLabel = "액션",
                                        withDismissAction = true
                                    )
                                }
                            }
                        ) {
                            Text("버튼6")
                        }
                    }
                }
            }
        }
    }
}
