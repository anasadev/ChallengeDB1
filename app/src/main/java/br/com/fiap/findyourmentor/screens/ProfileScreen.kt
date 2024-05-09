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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.FormText
import br.com.fiap.findyourmentor.database.repository.UserRepository

@Composable
fun ProfileScreen(
    navController: NavController,
    userId: String
) {
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    var myProfile = remember {
        mutableStateOf(userRepository.findUserById(userId.toLong()))
    }
    var profileType = myProfile.value.profileType
    var interestsText = ""
    if(profileType == "aprendiz") interestsText = stringResource(id = R.string.interests_text_learner) else interestsText = stringResource(id = R.string.interests_text_mentor)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        FormText(text = stringResource(id = R.string.name) + ": ${myProfile.value.name}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = stringResource(id = R.string.user_location) + ": ${myProfile.value.location}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = stringResource(id = R.string.profile_type) + ": ${myProfile.value.profileType}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = stringResource(id = R.string.user_presentation) + ": ${myProfile.value.presentation}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = "$interestsText: ${myProfile.value.interestsList}")


        Spacer(modifier = Modifier.height(20.dp))
    }
    
}