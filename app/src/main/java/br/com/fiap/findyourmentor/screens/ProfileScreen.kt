package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.FormText
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(
    navController: NavController,
    userId: String
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

    var profileType = user.profileType
    var interestsText = ""
    if(profileType == "aprendiz") interestsText = stringResource(id = R.string.interests_text_learner) else interestsText = stringResource(id = R.string.interests_text_mentor)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        FormText(text = stringResource(id = R.string.name) + ": ${user.name}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = stringResource(id = R.string.user_location) + ": ${user.location}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = stringResource(id = R.string.profile_type) + ": ${user.profileType}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = stringResource(id = R.string.user_presentation) + ": ${user.presentation}")
        Spacer(modifier = Modifier.height(20.dp))
        FormText(text = "$interestsText: ${user.interestsList}")


        Spacer(modifier = Modifier.height(20.dp))
    }
    
}


