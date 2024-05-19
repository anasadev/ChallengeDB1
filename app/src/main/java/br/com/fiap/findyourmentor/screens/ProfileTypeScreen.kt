package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileTypeScreen(navController: NavController, userId: String) {
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


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        FormText(text = stringResource(id = R.string.want_to_be) )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            user.profileType = "mentor"
            CoroutineScope(Dispatchers.Main).launch {

                withContext(Dispatchers.IO) {
                    val call = RetrofitFactory().getUserService().updateUser(userId.toLong(), user)

                    call.enqueue(object : Callback<User> {
                        override fun onResponse(
                            call: Call<User>,
                            response: Response<User>,
                        ) {
                            val result = response.body()!!
                            navController.navigate("interests/${result.id}")
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {

                            Log.i("FIAP", t.stackTrace.toString())
                        }
                    })
                }
            }
        }) {
            Text(stringResource(id = R.string.mentor))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            user.profileType = "aprendiz"
            CoroutineScope(Dispatchers.Main).launch {

                withContext(Dispatchers.IO) {
                    val call = RetrofitFactory().getUserService().updateUser(userId.toLong(), user)

                    call.enqueue(object : Callback<User> {
                        override fun onResponse(
                            call: Call<User>,
                            response: Response<User>,
                        ) {
                            val result = response.body()!!
                            navController.navigate("interests/${result.id}")
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {

                            Log.i("FIAP", t.stackTrace.toString())
                        }
                    })
                }
            }
        }) {
            Text(stringResource(id = R.string.learner))
        }
    }
}
