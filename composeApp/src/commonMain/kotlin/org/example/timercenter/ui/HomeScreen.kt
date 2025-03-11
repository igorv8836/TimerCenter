package org.example.timercenter.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column {
        Text("All Timers(3)")
        LazyColumn {

        }
    }
}

@Composable
fun Timer() {
    Row {
        Column {
            Text(text = "Pomodoro Timer")
            Text(text = "25 minutes")
        }
        Spacer(modifier = Modifier.fillMaxWidth(1f))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFF253646)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Start", color = Color.White)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}