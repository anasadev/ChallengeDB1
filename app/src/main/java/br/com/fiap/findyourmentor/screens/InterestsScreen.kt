package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.FormText
import br.com.fiap.findyourmentor.database.repository.UserRepository

@Composable
fun InterestsScreen(
    navController: NavController,
    userId: String
) {
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    var user = userRepository.findUserById(userId.toLong())
    var profileType = user.profileType

    var route = ""
    var interests = ""
    var interestsList: MutableList<String> = mutableListOf()
    val optionsList = listOf("Kotlin", "Swift", "Java", "PHP", "CSS", "JavaScript", "TypeScript", "Python", "MySQL")

    if(profileType == "aprendiz"){
        route = "profile/aprendiz"
        interests = stringResource(id = R.string.interests_text_learner)
    } else {
        route = "profile/mentor"
        interests = stringResource(id = R.string.interests_text_mentor)
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
            navController.navigate("profile/${userId}")

         }) {
            Text(stringResource(id = R.string.save_profile))
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun LearningScreenPreview() {
//    LearningScreen()
//}