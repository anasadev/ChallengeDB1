package br.com.fiap.findyourmentor.screens

import android.provider.ContactsContract.Profile
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeProfileScreen(navController: NavController) {
    var usersList by remember {mutableStateOf(listOf<User>())}
    var call = RetrofitFactory().getUserService().getUsersList();

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
        .background(Color.Cyan)
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ){
        var text by remember{ mutableStateOf("") }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Procurar matches") }
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 228.dp)
        ) {
           items(usersList) {
               ProfileItem(navController, it)
           }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun HomeProfileScreenPreview(){
//    HomeProfileScreen(navController = navController)
//}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileItem(navController: NavController, user: User){
    Column {
        Image(
            painter = painterResource(id = R.drawable.contact),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        FlowRow {
            Text(text = user.name)
        }
        FlowRow {
            Text(text = user.profileType.replaceFirstChar(Char::uppercaseChar))
        }
        FlowRow {
            Text(text = user.interestsList)
        }
        Button(onClick = {
                navController.navigate("profile/${user.id}")
        }) {
            Text(stringResource(id = R.string.view_profile))
        }
    }
}

