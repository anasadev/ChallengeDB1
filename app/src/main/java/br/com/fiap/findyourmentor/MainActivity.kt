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
import br.com.fiap.findyourmentor.screens.HomeProfileScreen
import br.com.fiap.findyourmentor.screens.InterestsScreen
import br.com.fiap.findyourmentor.screens.MatchScreen
import br.com.fiap.findyourmentor.screens.ProfileScreen
import br.com.fiap.findyourmentor.screens.ProfileTypeScreen
import br.com.fiap.findyourmentor.screens.UserInfoScreen
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
                        startDestination = "home"
                        //startDestination = "personalInfos"
                    ) {
                        composable(route = "personalInfos") {
                            UserInfoScreen(navController)
                        }
                        composable(route = "profileType/{userId}") {
                            val userId = it.arguments?.getString("userId")
                            ProfileTypeScreen(navController, userId!!)
                        }
                        composable(route = "interests/{userId}") {
                            val userId = it.arguments?.getString("userId")
                            InterestsScreen(navController, userId!!)
                        }
                        composable(route = "profile/{userId}") {
                            val userId = it.arguments?.getString("userId")
                            ProfileScreen(navController, userId!!)
                        }
                        composable(route = "home") {
                            HomeProfileScreen(navController)
                        }
                        composable(route = "match/{userName}") {
                            val userName = it.arguments?.getString("userName")
                            MatchScreen(userName!!)
                        }
                    }
                }
            }
        }
    }
}



