package br.com.fiap.findyourmentor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileButton(
    navController: NavController,
    route: String,
    textButton: String
) {
    Button(
        onClick = { navController.navigate(route)},
        modifier = Modifier.width(250.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF412a9c))
    ) {
        Text(
            text = textButton,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace
        )
    }
}