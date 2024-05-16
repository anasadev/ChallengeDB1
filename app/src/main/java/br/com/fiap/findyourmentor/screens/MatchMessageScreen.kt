package br.com.fiap.findyourmentor.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.findyourmentor.R

@Composable
fun MatchMessageScreen(userName: String) {
    Column {
        Row {
            Text(stringResource(id = R.string.match))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text("${userName} " + stringResource(id = R.string.send_match))
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}