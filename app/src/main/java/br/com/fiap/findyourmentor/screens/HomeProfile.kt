package br.com.fiap.findyourmentor.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.R
import br.com.fiap.findyourmentor.components.NavBar
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.screens.base.UIState
import br.com.fiap.findyourmentor.screens.util.ComposableLifecycle
import br.com.fiap.findyourmentor.screens.util.ObserveLifecycleEvents
import br.com.fiap.findyourmentor.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeProfileScreen(
    navController: NavController, userId: String,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.ObserveLifecycleEvents(lifecycleOwner.lifecycle)

    var connectedUser by remember { mutableStateOf(User(2, "Sandra")) }

    //Need refactor
    val callUser = RetrofitFactory().getUserService().getUserById(userId.toLong())
    callUser.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            response.body()?.let {
                connectedUser = it
            } ?: run {

            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.i("FIAP", "onResponde: ${t.message}")
        }
    })
    //

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.i("LIFE_CYCLE", "ON_CREATE")
            }

            Lifecycle.Event.ON_START -> {
                Log.i("LIFE_CYCLE", "ON_START")
                viewModel.getRemoteUsers()
            }

            Lifecycle.Event.ON_RESUME -> {
                Log.i("LIFE_CYCLE", "ON_RESUME")
            }

            Lifecycle.Event.ON_PAUSE -> {
                Log.i("LIFE_CYCLE", "ON_PAUSE")
            }

            Lifecycle.Event.ON_STOP -> {
                Log.i("LIFE_CYCLE", "ON_STOP")
            }

            Lifecycle.Event.ON_DESTROY -> {
                Log.i("LIFE_CYCLE", "ON_DESTROY")
            }

            else -> {
                Log.i("LIFE_CYCLE", "ELSE")
            }
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        topBar = {
            NavBar(navController, connectedUser.id.toString(), "Usuários")
        }) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is UIState.Idle -> {}
                is UIState.Loading -> {
                    ShowLoading(true)
                }

                is UIState.Success -> {
                    val data = (uiState as UIState.Success).data
                    var dataList by remember { mutableStateOf(data) }

                    ShowSearchBar(onFilter = { text ->
                        dataList = if (text.isNotBlank()) {
                            data.filter { user ->
                                user.name.contains(text, ignoreCase = true)
                            }.toMutableStateList()
                        } else {
                            data
                        }
                    })
                    ShowGridProfile(connectedUser, dataList, navController)
                }

                is UIState.Error -> {
                    ShowError((uiState as UIState.Error).error)
                }

                else -> {}
            }
        }

    }

}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun HomeProfileScreenPreview(){
//    HomeProfileScreen(navController = navController)
//}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileItem(navController: NavController, user: User, connectedUserId: String) {
    Column(
        modifier = Modifier
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                ) {
                    val image: Painter = painterResource(id = R.drawable.contact)
                    Image(painter = image, contentDescription = "Wess")
                    Icon(Icons.Filled.Check, contentDescription = "")
                }

                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 22.dp),
                    text = user.name
                )
                Text(text = user.profileType.replaceFirstChar(Char::uppercaseChar))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(
                        top = 12.dp,
                        bottom = 12.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                    text = user.interestsList
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        navController.navigate("profile/${user.id}/${connectedUserId}")
                    }) {
                    Text(stringResource(id = R.string.view_profile))
                }
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ShowLoading(isLoading: Boolean = false) {
    if (isLoading) {
        FlowColumn(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            LinearProgressIndicator()
        }
    }
}

@Composable
private fun ShowSearchBar(onFilter: (String) -> Unit) {
    var input by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 74.dp, bottom = 12.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            trailingIcon = {
                if (input.isNotBlank()) {
                    AnimatedVisibility(visible = true) {
                        IconButton(onClick = {
                            input = ""
                            onFilter(input)
                        }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                        }
                    }
                }
            },
            value = input,
            onValueChange = {
                input = it
                onFilter(input.trim())
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Search...", color = Color.Blue) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.White
            )
        )
    }
}

@Composable
private fun ShowGridProfile(
    connectedUser: User,
    usersList: MutableList<User>,
    navController: NavController
) {

    val usersListFiltered = if (connectedUser.profileType == "mentor") {
        usersList.filter { u -> u.profileType == "aprendiz" }.toMutableList()
    } else {
        usersList.filter { u -> u.profileType == "mentor" }.toMutableList()
    }

    LazyVerticalGrid(
        modifier = Modifier,
        columns = GridCells.Adaptive(minSize = 228.dp)
    ) {
        items(usersListFiltered) {
            if (connectedUser != null) {
                ProfileItem(navController, it, connectedUser.id.toString())
            }
        }
    }
}

@Composable
fun ShowError(error: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(24.dp)
                .alpha(0.8f),
            fontSize = 16.sp,
            text = error ?: "Error",
            color = Color.Red,
            textAlign = TextAlign.Start
        )
    }
}

