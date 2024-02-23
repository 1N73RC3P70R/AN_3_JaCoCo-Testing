fun agoToText() {

    println(
        """Сколько секунд назад пользователь был онлайн?
        |Памятка: 3600 секунд = 1 час
        |86,400 секунд = 24 часа
        |172,800 скунд = 48 часов
        |259,200 секунд = 72 часа
    """.trimMargin()
    )
    var input = readLine()?.toInt()

    val minutes = input!! / 60
    val hours = minutes!! / 60


    val daysToText = when {
        hours >= 24 && hours < 48 -> {
            val days = hours / 24
            "был(а) в сети вчера"
        }
        hours >= 48 && hours < 72 -> {
            val days = hours / 24
            "был(а) в сети позавчера"
        }
        else -> "был(а) в сети давно"
    }

    val hoursToText = when {
        hours >= 24 -> daysToText
        hours % 10 == 1 && hours != 11 -> "был(а) в сети $hours час назад"
        hours % 10 in 2..4 && hours !in 12..14 -> "был(а) в сети $hours часа назад"
        else -> "был(а) $hours часов назад"
    }

    val minutesToText = when {
        minutes % 10 == 1 && minutes % 100 != 11 -> "$minutes минуту"
        minutes % 10 in 2..4 && minutes % 100 !in 12..14 -> "$minutes минуты"
        else -> "$minutes минут"
    }

    val secondsToText = when (input) {
        in 0..60 -> "был(а) только что"
        in 61..(60 * 60) -> "был(а) в сети $minutesToText назад"
        else -> hoursToText
    }

    println(secondsToText)
}
