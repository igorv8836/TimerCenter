package org.example.timercenter

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.orbitmvi.orbit.ContainerHost

internal class LoginViewModel() : ContainerHost<TestState, TestingEffect>, ViewModel() {
    override val container = container<TestState, TestingEffect>(TestState())

    fun onEvent(event: TestEvent) {
        when (event) {
            is TestEvent.SendMessage -> intent { reduce { state.copy(message = event.message) } }
            is TestEvent.SendNotification -> intent {  }
        }
    }
}

@Stable
sealed interface TestEvent {
    data class SendMessage(val message: String) : TestEvent
    data class SendNotification(val notification: String) : TestEvent
}


@Stable
data class TestState(
    val isLoading: Boolean = false,
    val message: String? = null,
)


@Stable
internal sealed interface TestingEffect {
    data class ShowMessage(val message: String) : TestingEffect
    data class ShowError(val message: String) : TestingEffect
    data class ShowSuccessTesting(val message: String) : TestingEffect
}