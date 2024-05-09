package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

    Column {
        Row {
            FormEditableText(
                label = stringResource(id = R.string.user_name),
                value = name,
                placeHolder = stringResource(id = R.string.user_name_placeholder),
                atualizarTexto = {
                    name = it
                }
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
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            FormEditableText(
                label = stringResource(id = R.string.user_presentation),
                value = presentation,
                placeHolder = stringResource(id = R.string.user_presentation_placeholder),
                atualizarTexto = {
                    presentation = it
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val user = User(id = 0, name = name, location = location, presentation = presentation)
            var userId = userRepository.save(user).toString()
            navController.navigate("profileType/${userId}")
        }) {
            Text(stringResource(id = R.string.continue_button))
        }
    }
}
