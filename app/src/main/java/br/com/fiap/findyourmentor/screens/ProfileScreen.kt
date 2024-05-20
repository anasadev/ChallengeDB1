package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.FormText
import br.com.fiap.findyourmentor.components.NavBar
import br.com.fiap.findyourmentor.model.Match
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import br.com.fiap.findyourmentor.viewmodel.ProfileScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(
    navController: NavController,
    userId: String,
    userConnected: String,
    profileScreenViewModel: ProfileScreenViewModel
) {

    val user by profileScreenViewModel.user.observeAsState(initial = null)
    profileScreenViewModel.findUser(userId.toLong())

    val userName = user?.name
    val profileType = user?.profileType
    var interestsText = ""
    interestsText =
        if (profileType == "aprendiz") stringResource(id = R.string.interests_text_learner) else stringResource(id = R.string.interests_text_mentor)

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(navController, userConnected, "Perfil")
        }) { values ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(values)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.contact),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
            if (userName != null) {
                FormText(text = userName)
            }
            user?.profileType?.let { FormText(text = it.replaceFirstChar(Char::uppercaseChar)) }
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = stringResource(id = R.string.user_location) + ": ${user?.location}")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = stringResource(id = R.string.user_presentation) + ": ${user?.presentation}")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = "$interestsText: ${user?.interestsList}")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = stringResource(id = R.string.user_availability) + ": ${user?.availability}")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = stringResource(id = R.string.user_experience) + ": ${user?.experience}")
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (userId != userConnected) {
                    Button(colors = ButtonDefaults.buttonColors(Color.Red), onClick = {
                        profileScreenViewModel.unlikeUser(userId, userConnected, navController)
                    }) {
                        Text(stringResource(id = R.string.unlike_profile))
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (userId != userConnected) {
                    if (userName != null) {
                        Dialog(userConnected, userId, userName, navController, profileScreenViewModel)
                    }
                }
            }
        }
    }
}
@Composable
fun Dialog(
    userConnected: String,
    userId: String,
    userName: String,
    navController: NavController,
    profileScreenViewModel: ProfileScreenViewModel
) {
    Column {
        val openAlert = remember {
            mutableStateOf(false)
        }
        Button(colors = ButtonDefaults.buttonColors(Color(0xFF175732)), onClick = {
            profileScreenViewModel.likeUser(userId, userConnected)
            openAlert.value = true
        }) {
            Text(stringResource(id = R.string.like_profile))
        }
        if (openAlert.value) {
            AlertDialog(
                onDismissRequest = { openAlert.value = false },
                confirmButton = {
                    Button(onClick = {
                        openAlert.value = false
                        navController.navigate("myMatches/${userConnected}")
                    }) {
                        Text(text = "Ok")
                    }
                },
                text = { MatchMessageScreen(userName = userName) }
            )
        }
    }
}


