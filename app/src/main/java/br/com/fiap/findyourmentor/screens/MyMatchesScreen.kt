package br.com.fiap.findyourmentor.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.NavBar
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.viewmodel.MyMatchesScreenViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyMatchesScreen(navController: NavController, userConnected: String, myMatchesScreenViewModel: MyMatchesScreenViewModel) {
    val matchesList by myMatchesScreenViewModel.matchesList.observeAsState(initial = null)
    val usersListFiltered by myMatchesScreenViewModel.usersListFiltered.observeAsState(initial = null)

    myMatchesScreenViewModel.findMatchesByUser(userConnected.toLong())
    myMatchesScreenViewModel.usersListFiltered(matchesList)

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(navController = navController, userConnected = userConnected, "Matches & Mensagens")
        }) { values ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(values),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top

        ) {
            if(usersListFiltered?.isNotEmpty() == true){
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 228.dp)
                ) {
                    items(usersListFiltered!!) {
                        ProfileItem(it)
                    }
                }
            } else {
                Row {
                    Text(text = "Você ainda não tem nenhum match :(")
                }
            }
        }
    }
}

@Composable
private fun ProfileItem(user: User) {
    Row(modifier = Modifier.padding(8.dp)) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.contact),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
            )
        }
        Column {
            Row {
                Text(text = user.name)
            }
            Row {
                Text(text = "Olá, tudo bem ?")
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}
