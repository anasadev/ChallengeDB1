package br.com.fiap.findyourmentor.screens

import android.provider.ContactsContract.Profile
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.database.repository.getAllUsers
import br.com.fiap.findyourmentor.model.User

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeProfileScreen(

) {
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
           items(getAllUsers()) {
               ProfileItem(it)
           }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeProfileScreenPreview(){
    HomeProfileScreen()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileItem(user: User){
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
    }
}

