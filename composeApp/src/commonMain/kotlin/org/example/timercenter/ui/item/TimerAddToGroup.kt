package org.example.timercenter.ui.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.timercenter.ui.model.formatTime
import org.example.timercenter.ui.model.TimerUiModel

@Composable
fun TimerWithoutAll(timer: TimerUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Text(
            text = timer.timerName,
            fontSize = 16.sp,
        )
        Spacer(Modifier.Companion.weight(1f))
        Text(
            text = formatTime(timer.totalTime),
            fontSize = 16.sp,
        )
    }
}

@Composable
fun TimerAddToGroup(timer: TimerUiModel, isSelected: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.Companion.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            TimerWithoutAll(timer = timer)
        }
//        Spacer(Modifier.Companion.width(8.dp))
        IconButton(onClick = { onToggle(!isSelected) }) {
            Icon(
                imageVector = if (isSelected) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (isSelected) "Remove timer" else "Add timer",
                tint = if (isSelected) Color.Companion.Red.copy(alpha = 0.7f) else Color.Companion.Green.copy(
                    alpha = 0.7f
                )
            )
        }
    }
}