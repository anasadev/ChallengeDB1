package br.com.fiap.findyourmentor.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(navController: NavController, userConnected: String, screenName: String) {
    TopAppBar(
        title = {
            Text(text = screenName)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = { navController.navigate("profile/${userConnected}/${userConnected}") }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Meu perfil",
                    tint = Color.White
                )
            }
            IconButton(onClick = { navController.navigate("myMatches/${userConnected}") }) {
                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = "Meus matches",
                    tint = Color.White
                )
            }
            IconButton(onClick = { navController.navigate("home/${userConnected}") }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Usuários",
                    tint = Color.White
                )
            }
        }
    )
}