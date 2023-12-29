const val MINIMUM_RATE: Double = 35.0
const val STANDARD_RATE: Double = 0.0075
const val ZERO: Double = 0.0
const val UPPER_LIMIT_MASTERCARD_DAILY: Double = 75_000.00

var day = 1
var totalSpentMonth = 0.0
var commissionTotal = 0.0
var amount = 0.0
var inputCardChoice = ""

fun main() {
    commission()
}

fun commission() {
    usersCardChoice()

    val continueRunningProgram: Boolean = true
    while (continueRunningProgram) {
        println("Введите сумму: ")
        amount = readLine()!!.toDoubleOrNull() ?: 0.0

        if (amount <= 0 || amount > 150_000) {
            println("Неправильная сумма.")
            continue
        }

        totalSpentMonth += amount

        when (inputCardChoice) {
            "Visa", "Мир" -> standardCommissionCalculator()
            "Mastercard" -> {
                if (totalSpentMonth in ZERO..UPPER_LIMIT_MASTERCARD_DAILY) {
                    println("Комиссия с Mastercard отсутствует.")
                    commissionTotal = 0.0
                } else if (totalSpentMonth > UPPER_LIMIT_MASTERCARD_DAILY) {
                    println("${amount * STANDARD_RATE} руб. - комиссия с $inputCardChoice")
                }
            }
            "VK Pay" -> {
                commissionTotal = 0.0
                println("Комиссия не взимается.")
            }
        }

        dayPrompt()
    }
}

fun dayPrompt() {
    println(
        """Потрачено за месяц: $totalSpentMonth
            |Хотите ли продолжить?
            |1. Да
            |2. Нет (Закрыть программу)
        """.trimMargin()
    )
    //Это сделал так, как будто каждая операция проводиться на следующий день.
    day++
    when (readLine()?.toIntOrNull()) {
        1 -> println("Продолжаем. День $day.")
        2 -> return
        else -> {
            println("Нет такого варианта.")
            return
        }
    }
}

fun usersCardChoice() {
    println(
        """Выберите карту:
    | 1. Мир
    | 2. VK Pay
    | 3. Visa
    | 4. Mastercard 
""".trimMargin()
    )
    inputCardChoice = readLine().toString()

    inputCardChoice = when (inputCardChoice?.toIntOrNull()) {
        1 -> "Мир"
        2 -> "VK Pay"
        3 -> "Visa"
        4 -> "Mastercard"
        else -> {
            println("Выбран счёт по умолчанию (VK Pay)")
            "VK Pay"
        }
    }
}

fun standardCommissionCalculator() {
    if (amount <= 0 || amount > 150_000) {
        println("Неправильная сумма.")
    } else {
        val commissionMirVisa = amount * STANDARD_RATE
        val finalCommission = if (commissionMirVisa < MINIMUM_RATE) MINIMUM_RATE else commissionMirVisa
        println("$finalCommission руб. - комиссия с $inputCardChoice")
    }
}
