package br.com.fiap.findyourmentor.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.NavBar
import br.com.fiap.findyourmentor.model.Match
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMatchesScreen(navController: NavController, userConnected: String) {
    var matchesList by remember { mutableStateOf(listOf<Match>()) }
    val callMatch =
        RetrofitFactory().getMatchService().getMatchesByConnectedUserId(userConnected.toLong())

    callMatch.enqueue(object : Callback<List<Match>> {
        override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
            if(response.body() != null){
                matchesList = response.body()!!
            }
        }

        override fun onFailure(call: Call<List<Match>>, t: Throwable) {
            Log.i("FIAP", "onResponde: ${t.message}")
        }
    })

    var usersList by remember { mutableStateOf(listOf<User>()) }
    var call = RetrofitFactory().getUserService().getUsersList();
    call.enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            //TODO mensagem pro usuário se nenhum usuário encontrado
            usersList = response.body()!!
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            Log.i("FIAP", "onResponde: ${t.message}")
        }
    })

    var likedUsersId: MutableList<Long> = mutableListOf()
    var usersListFiltered by remember { mutableStateOf(listOf<User>()) }
    if(matchesList.isNotEmpty()){
        for (element in matchesList) {
            likedUsersId.add(element.likedUserId)
        }
        usersListFiltered = usersList.filter { it.id in likedUsersId }
    }



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
            if(usersListFiltered.isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 228.dp)
                ) {
                    items(usersListFiltered) {
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

@OptIn(ExperimentalLayoutApi::class)
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
