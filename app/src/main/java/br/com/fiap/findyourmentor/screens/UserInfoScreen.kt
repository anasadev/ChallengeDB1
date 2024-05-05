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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.components.FormEditableText
import br.com.fiap.findyourmentor.database.repository.UserRepository
import br.com.fiap.findyourmentor.model.User

@Composable
fun UserInfoScreen(navController: NavController,) {
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    var nome by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var presentation by remember { mutableStateOf("") }

    Column {
        Row {
            FormEditableText(
                label = "Username",
                value = nome,
                placeHolder = "Nome que será visível para os outros usuários",
                atualizarTexto = {
                    nome = it
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            FormEditableText(
                label = "Localização",
                value = location,
                placeHolder = "Cidade onde você mora",
                atualizarTexto = {
                    location = it
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            FormEditableText(
                label = "Apresentação",
                value = presentation,
                placeHolder = "Conte um pouco sobre você!",
                atualizarTexto = {
                    presentation = it
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val user = User(id = 0, name = nome, location = location, presentation = presentation)
            var myId = userRepository.save(user).toString()
            navController.navigate("profileType")
        }) {
            Text("Continuar")
        }
    }
}
