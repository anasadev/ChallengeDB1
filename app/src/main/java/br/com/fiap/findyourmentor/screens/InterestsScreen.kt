package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.FormText
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
fun InterestsScreen(
    navController: NavController,
    userId: String
) {
    var callUser = RetrofitFactory().getUserService().getUserById(userId.toLong())

    var user by remember {
        mutableStateOf(User())
    }

    callUser.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            user = response.body()!!
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.i("FIAP", "onResponde: ${t.message}")
        }
    })

    var profileType = user.profileType

    var interests = ""
    var interestsList: MutableList<String> = mutableListOf()
    val optionsList = listOf("Kotlin", "Swift", "Java", "PHP", "CSS", "JavaScript", "TypeScript", "Python", "MySQL")
    var checkboxError by remember {
        mutableStateOf(false)
    }
    if(profileType == "aprendiz"){
        interests = stringResource(id = R.string.interests_text_learner)
    } else {
        interests = stringResource(id = R.string.interests_text_mentor)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        FormText(text = interests)

        Spacer(modifier = Modifier.height(20.dp))

        optionsList.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.width(300.dp)) {
                val isChecked = remember { mutableStateOf(false) }

                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { isChecked.value = it },
                    enabled = true
                )
                Text(text = item)
                if(isChecked.value){
                    interestsList.add(item)
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        if(checkboxError){
            Text(text = stringResource(id = R.string.required_checkbox),
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if(interestsList.isNotEmpty()){
                checkboxError = false
                user.interestsList = interestsList.joinToString()
                CoroutineScope(Dispatchers.Main).launch {

                    withContext(Dispatchers.IO) {
                        val call = RetrofitFactory().getUserService().updateUser(userId.toLong(), user)

                        call.enqueue(object : Callback<User> {
                            override fun onResponse(
                                call: Call<User>,
                                response: Response<User>,
                            ) {
                                val result = response.body()!!
                                navController.navigate("home/${result.id}")
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {

                                Log.i("FIAP", t.stackTrace.toString())
                            }
                        })
                    }
                }
            } else {
                checkboxError = true
            }

         }) {
            Text(stringResource(id = R.string.save_profile))
        }
    }
}
