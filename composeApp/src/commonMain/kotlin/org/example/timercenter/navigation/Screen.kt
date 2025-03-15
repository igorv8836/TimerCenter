package org.example.timercenter.navigation

enum class Screen(val route: String, val title: String) {
    HOME("home", "Главная"),
    CREATE("create/{id}", "Создать"),
    CREATE_GROUP("create_group/{id}", "Создать группу"),
    ADD_TO_GROUP("add_to_group", "Добавить в группу"),
    HISTORY("history", "История");

    companion object {
        fun getRouteByTitle(title: String): Screen? {
            return values().find { it.title == title }
        }
    }
}