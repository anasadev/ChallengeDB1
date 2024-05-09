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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
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

        FormText(text = stringResource(id = R.string.want_to_be) )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            user.profileType = "mentor"
            userRepository.update(user)
            navController.navigate("interests/${userId}")
        }) {
            Text(stringResource(id = R.string.mentor))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            user.profileType = "aprendiz"
            userRepository.update(user)
            navController.navigate("interests/${userId}")
        }) {
            Text(stringResource(id = R.string.learner))
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun ProfileTypeScreenPreview() {
//    ProfileTypeScreen()
//}