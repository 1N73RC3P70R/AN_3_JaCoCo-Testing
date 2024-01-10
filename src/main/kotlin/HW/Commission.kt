package HW

import kotlin.system.exitProcess

const val MINIMUM_RATE: Double = 35.0
const val STANDARD_RATE: Double = 0.0075
const val STANDARD_RATE_MASTERCARD_MAESTRO = 20
const val STANDARD_RATE_MASTERCARD_MAESTRO_PERCENT = 0.006
const val ZERO: Double = 0.0
const val UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY: Double = 75_000.00
const val UPPER_LIMIT_ALL_DAILY: Double = 150_000.00
const val UPPER_LIMIT_ALL_MONTHLY: Double = 600_000.00
const val UPPER_LIMIT_VK_DAILY = 15_000.00
const val UPPER_LIMIT_VK_MONTHLY = 40_000.00


var day = 1
var totalSpentMonth = 0.0
var commissionTotal = 0.0
var amount = 0.0
var inputCardChoice = ""


fun commission() {
    usersCardChoice()

    while (true) {
        println("Введите сумму (или введите \"end\" чтобы выйти): ")
        val input = readlnOrNull()
        if (input.equals("end", ignoreCase = true)) {
            println("Программа закрывается!")
            exitProcess(0)
        }
        amount = input!!.toDoubleOrNull() ?: 0.0


        if (amount <= ZERO) {
            println("Неправильная сумма.")
            continue
        }


        val limitsCalculation = limits()
        if (limitsCalculation != null) {
            val (remainingDaily, remainingMonthly) = limitsCalculation
            totalSpentMonth += amount

            when (inputCardChoice) {
                "Visa", "Мир" -> standardCommissionCalculator(amount)
                "Mastercard", "Maestro" -> commissionMastercardMaestro(amount)
                "VK Pay" -> commissionVKPay(amount)
            }
            dayPrompt()
        }
    }
}

fun limits(): Pair<Double, Double>? {
    var dailyLimitAll: Double = 0.0
    var monthlyLimitAll: Double = 0.0

    when (inputCardChoice) {
        "Visa", "Мир" -> {
            dailyLimitAll = UPPER_LIMIT_ALL_DAILY
            monthlyLimitAll = UPPER_LIMIT_ALL_MONTHLY
        }
        "VK Pay" -> {
            dailyLimitAll = UPPER_LIMIT_VK_DAILY
            monthlyLimitAll = UPPER_LIMIT_VK_MONTHLY
        }
        "Mastercard", "Maestro" -> {
            dailyLimitAll = UPPER_LIMIT_ALL_DAILY
            monthlyLimitAll = UPPER_LIMIT_ALL_MONTHLY
        }
        //проверка
        else -> {
            println("Лимит отсуствует.")
        }
    }
    val remainingDaily = dailyLimitAll - amount

    if (remainingDaily < 0) {
        println("Превышен дневной лимит на ${-remainingDaily} руб!")
        return null
    }

    val remainingMonthly = monthlyLimitAll - (totalSpentMonth + amount)


    if (remainingMonthly < 0) {
        println("Превышен лимит на ${-remainingMonthly} руб.")
        return null
    }
    return Pair(remainingDaily, remainingMonthly)
}

fun dayPrompt() {
    println(
        """Потрачено сегодня: $amount
            |Потрачено за месяц: $totalSpentMonth
            |Хотите ли продолжить?
            |1. Да
            |2. Нет (Закрыть программу)
        """.trimMargin()
    )
    //Это сделал так, как будто каждая операция проводиться на следующий день.
    day++
    when (readLine()?.toIntOrNull()) {
        1 -> println("Продолжаем. День $day.")
        2 -> {
            println("Всего доброго!")
            exitProcess(0)
        }
        else -> {
            println("Нет такого варианта.")
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
    | 5. Maestro
""".trimMargin()
    )
    inputCardChoice = readLine().toString()

    inputCardChoice = when (inputCardChoice?.toIntOrNull()) {
        1 -> "Мир"
        2 -> "VK Pay"
        3 -> "Visa"
        4 -> "Mastercard"
        5 -> "Maestro"
        else -> {
            println("Выбран счёт по умолчанию (VK Pay)")
            "VK Pay"
        }
    }
}

fun standardCommissionCalculator(amount: Double) {

    val commissionMirVisa = amount * STANDARD_RATE
    val finalCommission = if (commissionMirVisa < MINIMUM_RATE) MINIMUM_RATE else commissionMirVisa
    println("$finalCommission руб. - комиссия с $inputCardChoice")
}

fun commissionVKPay(amount: Double) {

    commissionTotal = 0.0
    println("Комиссия не взимается.")
}

fun commissionMastercardMaestro(amount: Double) {
    if (amount in ZERO..UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY) {
        println("Комиссия с Mastercard отсутствует.")
        commissionTotal = 0.0
    } else if ((amount > UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY || totalSpentMonth > UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY) && amount < UPPER_LIMIT_ALL_MONTHLY) {
        println("${(amount * STANDARD_RATE_MASTERCARD_MAESTRO_PERCENT) + STANDARD_RATE_MASTERCARD_MAESTRO} руб. - комиссия с $inputCardChoice")
    }
}

