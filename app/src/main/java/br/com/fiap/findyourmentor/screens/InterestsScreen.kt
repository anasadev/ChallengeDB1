package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        ProfileButton(navController = navController, route = route, textButton = "Confirmar")
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun LearningScreenPreview() {
//    LearningScreen()
//}