import HW.commission

fun main() {


    commission(transferForMonth = 0.0, transferAmount = 1000.0, cardType = 1)
    commission(transferForMonth = 100.00, transferAmount = 1000.0)
    commission(transferForMonth = 100.00, transferAmount = 1000.00, cardType = 3)
    commission(transferForMonth = 10000.00, transferAmount = 1000.00, cardType = 4)
    commission(transferForMonth = 2.00, transferAmount = 1000.00, cardType = 5)
    commission(transferForMonth = 2.00, transferAmount = 1000.00, cardType = 6)


/*
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

 */
}

