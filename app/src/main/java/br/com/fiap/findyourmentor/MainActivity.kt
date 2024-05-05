package br.com.fiap.findyourmentor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.findyourmentor.screens.InterestsScreen
import br.com.fiap.findyourmentor.screens.ProfileScreen
import br.com.fiap.findyourmentor.screens.ProfileTypeScreen
import br.com.fiap.findyourmentor.ui.theme.FindYourMentorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourMentorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "profileType"
                    ) {
                        composable(route = "profileType") {
                            ProfileTypeScreen(navController)
                        }
                        composable(route = "interests/{profileType}") {
                            val profileType = it.arguments?.getString("profileType")
                            InterestsScreen(navController, profileType!!)
                        }
                        composable(route = "profile/{myId}") {
                            val myId = it.arguments?.getString("myId")
                            ProfileScreen(navController, myId!!)
                        }
                    }
                }
            }
        }
    }
}



