fun agoToText() {

    println(""""Сколько секунд назад пользователь был онлайн?
        |Памятка: 3600 секунд = 1 час
        |86,400 секунд = 24 часа
    """.trimMargin())
    var input = readLine()?.toInt()

    val minutes = input!! / 60
    val hours = minutes!! / 60


    val hoursToText = when {
        hours % 10 == 1 && hours != 11 -> "был(а) $hours час назад"
        hours % 10 in 2..4 && hours !in 12..14 -> "был(а) $hours часа назад"
        else -> "$hours часов"
    }

    val minutesToText = when {
        minutes % 10 == 1 && minutes % 100 != 11 -> "был(а) $minutes минуту назад"
        minutes % 10 in 2..4 && minutes % 100 !in 12..14 -> "был(а) $minutes минуты назад"
        else -> "$minutes минут"
    }

    val secondsToText = when (input) {
        in 0..60 -> "был(а) только что"
        in 61..(60 * 60) -> "был(а) в сети $minutesToText назад"
        else -> hoursToText
    }

    println(secondsToText)
}
