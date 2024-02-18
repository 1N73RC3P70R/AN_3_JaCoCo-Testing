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

var totalSpentMonth = 0.0
var commissionTotal = 0.0
var transferAmount = 0.0
var inputCardChoice = ""


fun commission(transferForMonth: Double = 0.0, transferAmount: Double, cardType: Int = 2) {
    val cardTypes = listOf("Мир", "VK Pay", "Visa", "Mastercard", "Maestro")
    inputCardChoice = cardTypes.getOrElse(cardType - 1) { "VK Pay" }

    if (cardType > 5 || cardType < 1) {
        println("Карта не найдена!")
        exitProcess(0)
    }

    totalSpentMonth = transferForMonth

    if (transferAmount <= ZERO) {
        println("Неправильная сумма.")
        return
    }

    val limitsCalculation = limits(cardType, transferAmount, totalSpentMonth)
    if (limitsCalculation != null) {
        totalSpentMonth += transferAmount

        when (inputCardChoice) {
            "Мир" -> {
                standardCommissionCalculator(transferAmount)
                println(limitsCalculation)
            }
            "VK Pay" -> {
                commissionVKPay(transferAmount)
                println(limitsCalculation)
            }
            "Visa" -> {
                standardCommissionCalculator(transferAmount)
                println(limitsCalculation)
            }
            "Mastercard" -> {
                commissionMastercardMaestro(transferAmount)
                println(limitsCalculation)
            }
            "Maestro" -> {
                commissionMastercardMaestro(transferAmount)
                println(limitsCalculation)
            }
        }
        // dayPrompt()
    }
}

fun limits(cardType: Int, transferAmount: Double, totalSpentMonth: Double): String? {
    var dailyLimitAll: Double = 0.0
    var monthlyLimitAll: Double = 0.0

    when (cardType) {
        1, 3 -> {
            dailyLimitAll = UPPER_LIMIT_ALL_DAILY
            monthlyLimitAll = UPPER_LIMIT_ALL_MONTHLY
        }
        2 -> {
            dailyLimitAll = UPPER_LIMIT_VK_DAILY
            monthlyLimitAll = UPPER_LIMIT_VK_MONTHLY
        }
        4, 5 -> {
            dailyLimitAll = UPPER_LIMIT_ALL_DAILY
            monthlyLimitAll = UPPER_LIMIT_ALL_MONTHLY
        }
        else -> {
            println("Лимит отсуствует.")
        }
    }
    val remainingDaily = dailyLimitAll - transferAmount

    if (remainingDaily < 0) {
        println("Превышен дневной лимит на ${-remainingDaily} руб!")
        return null
    }

    val remainingMonthly = monthlyLimitAll - (totalSpentMonth + transferAmount)

    if (remainingMonthly < 0) {
        println("Превышен лимит на ${-remainingMonthly} руб.")
        return null
    }

    return "Оставшийся лимит на сегодня: $remainingDaily руб.\n" +
            "Оставшийся лимит на месяц: $remainingMonthly руб.\n"
}
fun dayPrompt() {
    println(
        """Потрачено сегодня: $transferAmount
            |Потрачено за месяц: $totalSpentMonth
            |Хотите ли продолжить?
            |1. Да
            |2. Нет (Закрыть программу)
        """.trimMargin()
    )
    //Это сделал так, как будто каждая операция проводиться на следующий день.
    var day = 1
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


fun standardCommissionCalculator(amount: Double) {

    val commissionMirVisa = amount * STANDARD_RATE
    val finalCommission = if (commissionMirVisa < MINIMUM_RATE) MINIMUM_RATE else commissionMirVisa
    println("$finalCommission руб. - комиссия с $inputCardChoice")
}

fun commissionVKPay(amount: Double) {
    commissionTotal = 0.0
    println("Комиссия с VK Pay не взимается.")
}

fun commissionMastercardMaestro(amount: Double) {
    if (amount in ZERO..UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY) {
        if (inputCardChoice == "Mastercard") {
            println("Комиссия с Mastercard отсутствует.")
        } else println("Комиссия с Maestro отсутствует.")

        commissionTotal = 0.0

    } else if ((amount > UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY || totalSpentMonth > UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY) && amount < UPPER_LIMIT_ALL_MONTHLY) {
        println("${(amount * STANDARD_RATE_MASTERCARD_MAESTRO_PERCENT) + STANDARD_RATE_MASTERCARD_MAESTRO} руб. - комиссия с $inputCardChoice")
    }
}


/*
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
 */