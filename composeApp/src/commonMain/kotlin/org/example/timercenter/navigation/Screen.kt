package org.example.timercenter.navigation

enum class Screen(val route: String, val title: String) {
    HOME("home", "Главная"),
//    CREATE("create", "Создать"),

    /*Тут желательно вместо параметров при изменении таймера {timerName}/{totalTime}/{show}
    * передавать id этого таймера, и потом по этому id находить таймер, применять к нему
    * изменения и обновлять этот таймер в базе данных.*/
//    CREATE("create/{timerName}/{totalTime}/{show}", "Создать"),
    CREATE("create/{id}", "Создать"),

    /*Тут сделать аналогично CREATE.*/
//    CREATE_GROUP("create_group/{groupName}", "Создать группу"),
    CREATE_GROUP("create_group/{id}", "Создать группу"),

    ADD_TO_GROUP("add_to_group", "Добавить в группу"),

    HISTORY("history", "История");

    companion object {
        fun getRouteByTitle(title: String): Screen? {
            return values().find { it.title == title }
        }
    }
}