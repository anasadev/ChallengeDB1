package br.com.fiap.findyourmentor.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun InterestsScreen(
    navController: NavController,
    myId: String
) {
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    var user = userRepository.findUserById(myId.toLong())
    var profileType = user.profileType

    var route = ""
    var interests = ""
    var interestsList: MutableList<String> = mutableListOf()
    val optionsList = listOf("Kotlin", "Swift", "Java", "PHP", "CSS", "JavaScript", "TypeScript", "Python", "MySQL")

    if(profileType == "aprendiz"){
        route = "profile/aprendiz"
        interests = "Quero aprender:"
    } else {
        route = "profile/mentor"
        interests = "Posso ensinar:"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        FormText(text = interests)

        Spacer(modifier = Modifier.height(20.dp))

        optionsList.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                val isChecked = remember { mutableStateOf(false) }

                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { isChecked.value = it },
                    enabled = true,
                )
                Text(text = item)
                if(isChecked.value){
                    interestsList.add(item)
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            user.interestsList = interestsList.joinToString()
            userRepository.update(user)
            navController.navigate("profile/${myId}")

         }) {
            Text("Criar perfil")
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun LearningScreenPreview() {
//    LearningScreen()
//}