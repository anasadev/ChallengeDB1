package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.database.repository.UserRepository
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeProfileScreen(navController: NavController, userId: String) {

    var usersList by remember {mutableStateOf(listOf<User>())}
    val call = RetrofitFactory().getUserService().getUsersList();
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    val connectedUser = userRepository.findUserById(userId.toLong())

    call.enqueue(object : Callback<List<User>>{
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            //TODO mensagem pro usuário se nenhum usuário encontrado
            usersList = response.body()!!
        }
        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            Log.i("FIAP", "onResponde: ${t.message}")
        }
    })

    Column(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(top = 8.dp, bottom = 36.dp, start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ){
        var text by remember{ mutableStateOf("") }
        Row(
            modifier = Modifier.padding(bottom = 36.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Procurar matches") }
            )
        }

        ProfilesFilter(connectedUser, usersList, navController)
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun HomeProfileScreenPreview(){
//    HomeProfileScreen(navController = navController)
//}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileItem(navController: NavController, user: User, connectedUserId: String){
    Column(
        modifier = Modifier
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                ) {
                    val image: Painter = painterResource(id = R.drawable.contact)
                    Image(painter = image, contentDescription = "Wess")
                    Icon(Icons.Filled.Check, contentDescription = "")
                }

                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 22.dp),
                    text = user.name
                )
                Text(text = user.profileType.replaceFirstChar(Char::uppercaseChar))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(
                        top = 12.dp,
                        bottom = 12.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                    text = user.interestsList
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                    navController.navigate("profile/${user.id}/${connectedUserId}")
                }) {
                    Text(stringResource(id = R.string.view_profile))
                }
            }
        }

    }
}

@Composable
private fun ProfilesFilter(connectedUser: User?, usersList: List<User>, navController: NavController){

    //var usersListFiltered by remember {mutableStateOf(listOf<User>())}
    var usersListFiltered by remember {mutableStateOf(usersList)}

    if(connectedUser?.profileType == "mentor"){
        usersListFiltered = usersList.filter {
                u -> u.profileType == "aprendiz"
        }
    } else {
        usersListFiltered = usersList.filter {
                u -> u.profileType == "mentor"
        }
    }

    LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 228.dp)
        ) {
            items(usersListFiltered) {
                if (connectedUser != null) {
                    ProfileItem(navController, it, connectedUser.id.toString())
                }
            }
        }
}

