package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val userIdMock = 2
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("Find your mentor")
                }
            )
        }

    ) { values ->
        FlowColumn(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(values)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                value = email,
                placeholder = { Text(text = "Sandra")},
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = password,
                placeholder = { Text(text = "****************")},
                onValueChange = { password = it },
                label = { Text("Enter password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            FlowRow(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {

                OutlinedButton(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .width(150.dp),
                    onClick = { navController.navigate("personalInfos") }) {
                    Text("Criar uma conta")
                }
                Button(
                    modifier = Modifier.width(150.dp),
                    onClick = {
                        navController.navigate("home/${userIdMock}")
                    }) {
                    Text("Login")
                }
            }
        }
    }
}
