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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    userConnected: String
) {
    var call = RetrofitFactory().getUserService().getUserById(userId.toLong())
    var user by remember {
        mutableStateOf(User())
    }

    call.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            user = response.body()!!
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.i("FIAP", "onResponde: ${t.message}")
        }
    })


    var userName = user.name
    var profileType = user.profileType
    var interestsText = ""
    if (profileType == "aprendiz") interestsText =
        stringResource(id = R.string.interests_text_learner) else interestsText =
        stringResource(id = R.string.interests_text_mentor)

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(navController, userConnected, "Perfil")
        }) { values ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(values)
        ) {
            Image(
                painter = painterResource(id = R.drawable.contact),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
            FormText(text = stringResource(id = R.string.name) + ": $userName")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = stringResource(id = R.string.user_location) + ": ${user.location}")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = stringResource(id = R.string.profile_type) + ": ${user.profileType}")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = stringResource(id = R.string.user_presentation) + ": ${user.presentation}")
            Spacer(modifier = Modifier.height(20.dp))
            FormText(text = "$interestsText: ${user.interestsList}")
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (userId != userConnected) {
                    Button(onClick = {
                        val match = Match(
                            id = 0,
                            userId = userConnected.toLong(),
                            likedUserId = userId.toLong(),
                            isLiked = false
                        )
                        CoroutineScope(Dispatchers.Main).launch {

                            withContext(Dispatchers.IO) {
                                val callMatch = RetrofitFactory().getMatchService().pushMatch(match)

                                callMatch.enqueue(object : Callback<Match> {
                                    override fun onResponse(
                                        call: Call<Match>,
                                        response: Response<Match>
                                    ) {
                                        navController.navigate("home/${userConnected}")
                                    }

                                    override fun onFailure(call: Call<Match>, t: Throwable) {
                                        Log.i("FIAP", t.stackTrace.toString())
                                    }

                                })
                            }
                        }
                    }) {
                        Text(stringResource(id = R.string.unlike_profile))
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (userId != userConnected) {
                    Dialog(userConnected, userId, userName, navController)
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
    navController: NavController
) {
    Column {
        val openAlert = remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            openAlert.value = true
            val match = Match(
                id = 0,
                userId = userConnected.toLong(),
                likedUserId = userId.toLong(),
                isLiked = true
            )
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    val callMatch = RetrofitFactory().getMatchService().pushMatch(match)

                    callMatch.enqueue(object : Callback<Match> {
                        override fun onResponse(
                            call: Call<Match>,
                            response: Response<Match>
                        ) {
                        }

                        override fun onFailure(call: Call<Match>, t: Throwable) {
                            Log.i("FIAP", t.stackTrace.toString())
                        }

                    })
                }
            }
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


