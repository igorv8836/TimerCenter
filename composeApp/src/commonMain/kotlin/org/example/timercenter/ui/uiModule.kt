package org.example.timercenter.ui

import org.example.timercenter.ui.viewmodels.*
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun uiModule() = module {
    viewModel { TimerDetailViewModel(get()) }
    viewModel { TimerGroupViewModel(get()) }
    viewModel { TimerHistoryViewModel(get(), get()) }
    viewModel { TimerListViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { CreateTimerViewModel(get()) }
    viewModel { CreateTimerGroupViewModel(get(), get()) }
    viewModel { AddTimersToGroupViewModel(get())}
    viewModel { HomeViewModel(get(), get()) }
}