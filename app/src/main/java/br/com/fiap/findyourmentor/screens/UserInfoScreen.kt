package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.FormEditableText
import br.com.fiap.findyourmentor.database.repository.UserRepository
import br.com.fiap.findyourmentor.model.User

@Composable
fun UserInfoScreen(navController: NavController) {
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var presentation by remember { mutableStateOf("") }
    val presentationMaxSize = 400
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
        if(nameError){
            Text(
                text = stringResource(id = R.string.required_name),
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
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
        if(locationError){
            Text(text = stringResource(id = R.string.required_location),
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            FormEditableText(
                label = stringResource(id = R.string.user_presentation),
                value = presentation,
                placeHolder = stringResource(id = R.string.user_presentation_placeholder),
                atualizarTexto = {
                    if(it.length <= presentationMaxSize) presentation = it
                },
                isError = presentationError
            )
        }
        if(presentationError){
            Text(text = stringResource(id = R.string.required_presentation),
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if(name.isEmpty()) nameError = true else nameError = false
            if(location.isEmpty()) locationError = true else locationError = false
            if(presentation.isEmpty()) presentationError = true else presentationError = false

            if(!nameError && !locationError && !presentationError){
                val user = User(id = 0, name = name, location = location, presentation = presentation)
                var userId = userRepository.save(user).toString()
                navController.navigate("profileType/${userId}")
            }
        }) {
            Text(stringResource(id = R.string.continue_button))
        }
    }
}
