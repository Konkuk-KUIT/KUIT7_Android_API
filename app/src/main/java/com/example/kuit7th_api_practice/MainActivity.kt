package com.example.kuit7th_api_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.kuit7th_api_practice.ui.navigation.NavGraph
import com.example.kuit7th_api_practice.ui.theme.KUIT7th_API_practiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint      //hilt를 통해 객체를 주입 받을수 있게 함
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KUIT7th_API_practiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
