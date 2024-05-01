package br.com.fiap.findyourmentor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.findyourmentor.screens.InterestsScreen
import br.com.fiap.findyourmentor.screens.LearningScreen
import br.com.fiap.findyourmentor.screens.ProfileScreen
import br.com.fiap.findyourmentor.screens.ProfileTypeScreen
import br.com.fiap.findyourmentor.screens.SkillsScreen
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
                        composable(route = "profile") {
                            val profileType = it.arguments?.getString("profileType")
                            ProfileScreen(navController, profileType!!)
                        }
                    }
                }
            }
        }
    }
}



