package homework

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

fun commission(
    transferForMonth: Double = 0.0,
    transferAmount: Double,
    cardType: Int = 2
): Double {
    val totalSpentMonth = transferForMonth + transferAmount
    if (!checkLimits(cardType, transferAmount, totalSpentMonth)) {
        return 0.0
    }

    return calculateCommission(transferAmount, cardType)
}

fun checkLimits(cardType: Int, transferAmount: Double, totalSpentMonth: Double): Boolean {
    val dailyLimit = when (cardType) {
        1, 3 -> UPPER_LIMIT_ALL_DAILY
        2 -> UPPER_LIMIT_VK_DAILY
        4, 5 -> UPPER_LIMIT_ALL_DAILY
        else -> {
            println("Лимит отсуствует.")
            return false
        }
    }

    val monthlyLimit = when (cardType) {
        1, 3 -> UPPER_LIMIT_ALL_MONTHLY
        2 -> UPPER_LIMIT_VK_MONTHLY
        4, 5 -> UPPER_LIMIT_ALL_MONTHLY
        else -> {
            println("Лимит отсуствует.")
            return false
        }
    }

    val remainingDaily = dailyLimit - transferAmount
    if (remainingDaily < 0) {
        println("Превышен дневной лимит на ${-remainingDaily} руб!")
        return false
    }

    val remainingMonthly = monthlyLimit - totalSpentMonth
    if (remainingMonthly < 0) {
        println("Превышен лимит на ${-remainingMonthly} руб.")
        return false
    }

    println("Оставшийся лимит на сегодня: $remainingDaily руб.\n" +
            "Оставшийся лимит на месяц: $remainingMonthly руб.\n")
    return true
}

fun calculateCommission(transferAmount: Double, cardType: Int): Double {
    return when (cardType) {
        1 -> {
            standardCommissionCalculator(transferAmount)
            MINIMUM_RATE
        }
        2 -> {
            commissionVKPay(transferAmount)
            ZERO
        }
        3 -> {
            standardCommissionCalculator(transferAmount)
            MINIMUM_RATE
        }
        4, 5 -> commissionMastercardMaestro(transferAmount)
        else -> 0.0
    }
}

fun standardCommissionCalculator(amount: Double) {
    val commissionMirVisa = amount * STANDARD_RATE
    val finalCommission = if (commissionMirVisa < MINIMUM_RATE) MINIMUM_RATE else commissionMirVisa
    println("$finalCommission руб. - комиссия с ${if (amount < 0) "VK Pay" else "Visa"}")
}

fun commissionVKPay(amount: Double) {
    println("Комиссия с VK Pay не взимается.")
}

fun commissionMastercardMaestro(amount: Double): Double {
    return if (amount <= UPPER_LIMIT_MASTERCARD_MAESTRO_DAILY) {
        println("Комиссия с Mastercard отсутствует.")
        ZERO
    } else {
        val commission = (amount * STANDARD_RATE_MASTERCARD_MAESTRO_PERCENT) + STANDARD_RATE_MASTERCARD_MAESTRO
        println("$commission руб. - комиссия с Mastercard")
        commission
    }
}
