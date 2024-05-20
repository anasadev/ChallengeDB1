package br.com.fiap.findyourmentor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.findyourmentor.screens.HomeProfileScreen
import br.com.fiap.findyourmentor.screens.InterestsScreen
import br.com.fiap.findyourmentor.screens.LoginScreen
import br.com.fiap.findyourmentor.screens.MatchMessageScreen
import br.com.fiap.findyourmentor.screens.MyMatchesScreen
import br.com.fiap.findyourmentor.screens.ProfileScreen
import br.com.fiap.findyourmentor.screens.ProfileTypeScreen
import br.com.fiap.findyourmentor.screens.UserInfoScreen
import br.com.fiap.findyourmentor.ui.theme.FindYourMentorTheme
import br.com.fiap.findyourmentor.viewmodel.MyMatchesScreenViewModel
import br.com.fiap.findyourmentor.viewmodel.ProfileScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        //startDestination = "home"
                        startDestination = "login"
                    ) {
                        composable(route = "login") {
                            LoginScreen(navController)
                        }
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
                        composable(
                            route = "profile/{userId}/{userConnected}",
                            arguments = listOf(
                                navArgument(name = "userId"){
                                    type = NavType.StringType
                                },
                                navArgument(name = "userConnected"){
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val userId = it.arguments?.getString("userId")
                            val userConnected = it.arguments?.getString("userConnected")
                            ProfileScreen(navController, userId!!, userConnected!!, ProfileScreenViewModel())
                        }
                        composable(route = "home/{userId}") {
                            val userId = it.arguments?.getString("userId")
                            HomeProfileScreen(navController, userId!!)
                        }
                        composable(route = "match/{userName}") {
                            val userName = it.arguments?.getString("userName")
                            MatchMessageScreen(userName!!)
                        }
                        composable(route = "myMatches/{userId}") {
                            val userId = it.arguments?.getString("userId")
                            MyMatchesScreen(navController = navController, userConnected = userId!!, MyMatchesScreenViewModel())
                        }

                    }
                }
            }
        }
    }
}



