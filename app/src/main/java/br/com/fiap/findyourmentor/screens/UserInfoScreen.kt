package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import br.com.fiap.findyourmentor.components.FormEditableText
import br.com.fiap.findyourmentor.components.TextError
import br.com.fiap.findyourmentor.components.dropDownMenu
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserInfoScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var presentation by remember { mutableStateOf("") }
    val presentationMaxSize = 400
    val listAvailability = listOf("Presencial", "Online", "Online e presencial")
    var availability by remember {
        mutableStateOf(listAvailability[0])
    }
    val listExperienceYears = listOf(
        "Iniciante - nunca trabalhei na área",
        "1 ano de experiência",
        "2 anos de experiência",
        "3 anos de experiência",
        "4 anos de experiência",
        "5 anos de experiência",
        "Mais de 5 anos de experiência",
        "Mais de 10 anos de experiência"
    )
    var experienceYears by remember {
        mutableStateOf(listExperienceYears[0])
    }
    var nameError by remember {
        mutableStateOf(false)
    }
    var locationError by remember {
        mutableStateOf(false)
    }
    var presentationError by remember {
        mutableStateOf(false)
    }

    Column {
        Row {
            FormEditableText(
                label = stringResource(id = R.string.user_name),
                value = name,
                placeHolder = stringResource(id = R.string.user_name_placeholder),
                atualizarTexto = {
                    name = it
                },
                isError = nameError
            )
        }
        if (nameError) {
            TextError(text = stringResource(id = R.string.required_name))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            FormEditableText(
                label = stringResource(id = R.string.user_location),
                value = location,
                placeHolder = stringResource(id = R.string.user_location_placeholder),
                atualizarTexto = {
                    location = it
                },
                isError = locationError
            )
        }
        if (locationError) {
            TextError(text = stringResource(id = R.string.required_location))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            FormEditableText(
                label = stringResource(id = R.string.user_presentation),
                value = presentation,
                placeHolder = stringResource(id = R.string.user_presentation_placeholder),
                atualizarTexto = {
                    if (it.length <= presentationMaxSize) presentation = it
                },
                isError = presentationError
            )
        }
        if (presentationError) {
            TextError(text = stringResource(id = R.string.required_presentation))
        }
        Spacer(modifier = Modifier.height(20.dp))

        availability = dropDownMenu(listAvailability, availability, "Disponibilidade")
        Spacer(modifier = Modifier.height(20.dp))

        experienceYears = dropDownMenu(
            listExperienceYears,
            experienceYears,
            "Anos de experiência na área de tecnologia"
        )
        Spacer(modifier = Modifier.height(20.dp))

        FlowRow(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = {
                if (name.isEmpty()) nameError = true else nameError = false
                if (location.isEmpty()) locationError = true else locationError = false
                if (presentation.isEmpty()) presentationError = true else presentationError = false

                if (!nameError && !locationError && !presentationError) {
                    val user =
                        User(
                            id = 0,
                            name = name,
                            location = location,
                            presentation = presentation,
                            availability = availability,
                            experience = experienceYears
                        )
                    CoroutineScope(Dispatchers.Main).launch {

                        withContext(Dispatchers.IO) {
                            val call = RetrofitFactory().getUserService().pushUser(user)

                            call.enqueue(object : Callback<User> {
                                override fun onResponse(
                                    call: Call<User>,
                                    response: Response<User>,
                                ) {
                                    val result = response.body()!!
                                    navController.navigate("profileType/${result.id}")
                                }

                                override fun onFailure(call: Call<User>, t: Throwable) {

                                    Log.i("FIAP", t.stackTrace.toString())
                                }
                            })
                        }
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.continue_button))
            }
        }
    }
}


