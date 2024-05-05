package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.components.FormText
import br.com.fiap.findyourmentor.database.repository.UserRepository

@Composable
fun ProfileScreen(
    navController: NavController,
    myId: String
) {
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    var myProfile = remember {
        mutableStateOf(userRepository.findUserById(myId.toLong()))
    }
    var profileType = myProfile.value.profileType
    var interestsText = ""
    if(profileType == "aprendiz") interestsText = "Quero aprender" else interestsText = "Posso ensinar"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        FormText(text = "Tipo de perfil ${myProfile.value.profileType}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = "$interestsText: ${myProfile.value.interestsList}")


        Spacer(modifier = Modifier.height(20.dp))
    }
    
}