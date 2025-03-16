package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable
import org.example.timercenter.ui.viewmodels.states.HomeEvent


@Stable
data class TimerHistoryUiModel(
    val id: Int,
    val name: String,
    val lastStartedTimeText: String
)