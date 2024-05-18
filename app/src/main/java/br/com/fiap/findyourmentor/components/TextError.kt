package br.com.fiap.findyourmentor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.findyourmentor.R

@Composable
fun TextError(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = Color.Red,
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp, horizontal = 20.dp),
        textAlign = TextAlign.End
    )
}