package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMatchesScreen(navController: NavController, userConnected: String) {
//    val context = LocalContext.current
//    val userRepository = UserRepository(context)
//    val matchRepository = MatchRepository(context)
//    val matchesList = matchRepository.findLikes(userConnected.toLong())
//    val matchesListMock: List<Long> = listOf(11,12)

//
//    ça marche
//    for (element in matchesListMock){
//        usersList.add(userRepository.findUserById(element))
//    }
//
//    for (element in matchesList){
//        usersList.add(userRepository.findUserById(element))
//    }

    var usersList by remember { mutableStateOf(listOf<User>()) }
    val call = RetrofitFactory().getUserService().getUsersList();

    call.enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            //TODO mensagem pro usuário se nenhum usuário encontrado
            usersList = response.body()!!
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            Log.i("FIAP", "onResponde: ${t.message}")
        }
    })



    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Matches & Mensagens")
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(onClick = { navController.navigate("profile/${userConnected}/${userConnected}") }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Meu perfil",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { navController.navigate("myMatches/${userConnected}}") }) {
                        Icon(
                            imageVector = Icons.Filled.MailOutline,
                            contentDescription = "Meus matches",
                            tint = Color.White
                        )
                    }
                }
            )
        }) { values ->
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(values),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top

        ){
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 228.dp)
            ) {
                items(usersList) {
                    ProfileItem(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileItem(user: User){
   Row ( modifier = Modifier.padding(8.dp)){
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
