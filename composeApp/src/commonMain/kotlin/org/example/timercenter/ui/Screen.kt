package org.example.timercenter.ui


enum class Screen(val route: String, val title: String) {
    HOME("home", "Главная"),
    CREATE("create", "Создать"),
    HISTORY("history", "История");

    companion object {
        fun getRouteByTitle(title: String): Screen? {
            return values().find { it.title == title }
        }
    }
}
