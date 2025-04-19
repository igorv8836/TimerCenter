package org.example.timercenter.ui

import org.example.timercenter.ui.viewmodels.AddTimersToGroupViewModel
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

/**
 * Модуль Koin для внедрения зависимостей UI-компонентов
 * Регистрирует все ViewModel'и приложения для внедрения зависимостей
 * @return Модуль с зарегистрированными ViewModel'ями
 */
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