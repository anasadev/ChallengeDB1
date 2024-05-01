package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.components.FormText
import br.com.fiap.findyourmentor.components.ProfileButton

@Composable
fun InterestsScreen(
    navController: NavController,
    profileType: String
) {
    var route = ""
    var interests = ""
    var kotlin by remember{
        mutableStateOf(false)
    }
    var java by remember{
        mutableStateOf(false)
    }
    var js by remember{
        mutableStateOf(false)
    }
    var css by remember{
        mutableStateOf(false)
    }

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

        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(checked = kotlin, onCheckedChange = {
                kotlin = it
            })
            Text(text = "Kotlin")
        }
        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(checked = java, onCheckedChange = {
                java = it
            })
            Text(text = "Java")
        }
        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(checked = js, onCheckedChange = {
                js = it
            })
            Text(text = "JavaScript")
        }
        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(checked = css, onCheckedChange = {
                css = it
            })
            Text(text = "CSS")
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        ProfileButton(navController = navController, route = route, textButton = "Confirmar")
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun LearningScreenPreview() {
//    LearningScreen()
//}