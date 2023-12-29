fun main() {

    while (true) {
        println(
            """Выберите ДЗ: 
        |1. ДЗ I
        |2. ДЗ II
        |3. Выход
    """.trimMargin()
        )

        val input = readlnOrNull()?.toInt()
        when {
            input == 1 -> agoToText()
            input == 2 -> commission()
            input == 3 -> return
            else -> println("Неправильный ввод.\n")
        }
    }
}

