package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.components.FormText
import br.com.fiap.findyourmentor.components.ProfileButton
import br.com.fiap.findyourmentor.database.repository.UserRepository
import br.com.fiap.findyourmentor.model.User

@Composable
fun ProfileTypeScreen(navController: NavController, userId: String) {
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    var user = userRepository.findUserById(userId.toLong())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        FormText(text = "Quero ser...")

        Spacer(modifier = Modifier.height(20.dp))

        //ProfileButton(navController = navController, route = "interests/mentor", textButton = "...mentor")
        Button(onClick = {
            user.profileType = "mentor"
            userRepository.update(user)
            navController.navigate("interests/${userId}")
        }) {
            Text("...mentor")
        }

        Spacer(modifier = Modifier.height(10.dp))

        //ProfileButton(navController = navController, route = "interests/aprendiz", textButton = "...aprendiz")

        Button(onClick = {
            user.profileType = "aprendiz"
            userRepository.update(user)
            navController.navigate("interests/${userId}")
        }) {
            Text("...aprendiz")
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun ProfileTypeScreenPreview() {
//    ProfileTypeScreen()
//}