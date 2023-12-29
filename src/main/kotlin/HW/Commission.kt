const val MINIMUM_RATE: Double = 35.0;
const val STANDARD_RATE: Double = 0.0075;
const val ZERO: Double = 0.0;
const val UPPER_LIMIT_MASTERCARD_DAILY: Double = 75_000.00

var totalSpentMonth = 0.0
var commissionTotal = 0.0
var amount = 0.0
var cardChoice = ""


fun commission() {

    println(
        """Выберите карту:
    | 1. Мир
    | 2. VK Pay
    | 3. Visa
    | 4. Mastercard 
""".trimMargin()
    )
    cardChoice = readLine().toString()

    cardChoice = when (cardChoice?.toInt()) {
        1 -> "Мир"
        2 -> "VK Pay"
        3 -> "Visa"
        4 -> "Mastercard"
        else -> {
            println("Выбран счёт по умолчанию (VK Pay)")
            "VK Pay"
        }
    }


    val continueRunningProgram: Boolean = true
    while (continueRunningProgram) {
        println("Введите сумму: ")
        amount = readLine()!!.toDoubleOrNull()!!;

        if (amount<= 0 || amount > 150_000) {
            println("Неправильная сумма.")
            continue
        }

        if (amount != null) {
            totalSpentMonth += amount
        } else {
            println("Ошибка!")
        }

        when (cardChoice) {
            "Visa", "Мир" -> standardCommissionCalculator()
            "Mastercard" -> if (totalSpentMonth in ZERO..UPPER_LIMIT_MASTERCARD_DAILY) {
                println("""Комиссия с Mastercard отсутствует.""")
                commissionTotal = 0.0
            } else if (totalSpentMonth > UPPER_LIMIT_MASTERCARD_DAILY) {
                println("""${amount * STANDARD_RATE} руб. - комиссия с $cardChoice""")
            }
            "VK Pay" -> {
                commissionTotal = 0.0;
                println("Комиссия не взимается.")
            }
        }

        println(
            """Потрачено за месяц: $totalSpentMonth
            |Хотите ли продолжить?
            |1. Да
            |2. Нет (Закрыть программу)
        """.trimMargin()
        )
        when (readLine()?.toIntOrNull()) {
            1 -> continue
            2 -> return
            else ->{ println("Нет такого варианта.")
                return}

        }
    }
}

fun standardCommissionCalculator() {
    if (amount == null || amount <= 0 || amount > 150_000) {
        println("Неправильная сумма.")
    } else {
        val commissionMirVisa = amount * STANDARD_RATE
        val finalCommission = if (commissionMirVisa < MINIMUM_RATE) MINIMUM_RATE else commissionMirVisa
        println("$finalCommission руб. - комиссия с $cardChoice")
    }
}