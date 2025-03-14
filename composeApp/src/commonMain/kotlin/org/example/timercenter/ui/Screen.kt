package org.example.timercenter.ui


enum class Screen(val route: String, val title: String) {
    HOME("home", "Главная"),
//    CREATE("create", "Создать"),
    CREATE("create/{timerName}/{totalTime}/{show}", "Создать"),

    HISTORY("history", "История"),
    CREATE_GROUP("create_group", "Создать группу"),
    ADD_TO_GROUP("add_to_group", "Добавить в группу");

    companion object {
        fun getRouteByTitle(title: String): Screen? {
            return values().find { it.title == title }
        }
    }
}
