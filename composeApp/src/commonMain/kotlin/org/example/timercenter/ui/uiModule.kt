package org.example.timercenter.ui

import org.example.timercenter.ui.viewmodels.CreateTimerGroupViewModel
import org.example.timercenter.ui.viewmodels.CreateTimerViewModel
import org.example.timercenter.ui.viewmodels.HomeViewModel
import org.example.timercenter.ui.viewmodels.SettingsViewModel
import org.example.timercenter.ui.viewmodels.TimerDetailViewModel
import org.example.timercenter.ui.viewmodels.TimerGroupViewModel
import org.example.timercenter.ui.viewmodels.TimerHistoryViewModel
import org.example.timercenter.ui.viewmodels.TimerListViewModel
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
    viewModel { HomeViewModel(get(), get()) }
}