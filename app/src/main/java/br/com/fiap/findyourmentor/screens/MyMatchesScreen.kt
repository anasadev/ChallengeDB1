package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.database.repository.MatchRepository
import br.com.fiap.findyourmentor.database.repository.UserRepository
import br.com.fiap.findyourmentor.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMatchesScreen(navController: NavController, userConnected: String) {
//    val context = LocalContext.current
//    val userRepository = UserRepository(context)
//    val matchRepository = MatchRepository(context)
//    val matchesList = matchRepository.findLikes(userConnected.toLong())
//    val matchesListMock: List<Long> = listOf(11,12)
//    var usersList: MutableList<User> = mutableListOf()
//
//    ça marche
//    for (element in matchesListMock){
//        usersList.add(userRepository.findUserById(element))
//    }
//
//    for (element in matchesList){
//        usersList.add(userRepository.findUserById(element))
//    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Matches & Mensagens")
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Gray),
                actions = {
                    IconButton(onClick = { navController.navigate("profile/${userConnected}/${userConnected}") }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Meu perfil",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { navController.navigate("myMatches/${userConnected}}")}){
                        Icon(imageVector = Icons.Filled.MailOutline, contentDescription = "Meus matches", tint = Color.White)
                    }
                }
            )
        }) { values ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(values)
        ) {
            Text(text = "myMatches")
        }
    }
}