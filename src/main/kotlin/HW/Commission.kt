const val MINIMUM_RATE: Int = 35;
const val STANDARD_RATE: Double = 0.0075;

fun commission() {

    println(
        """Выберите карту:
    | 1. Мир
    | 2. Visa
    | 3. Mastercard
""".trimMargin()
    )
    var cardChoice = readLine()

    cardChoice = if (cardChoice == "1") {
        """"Мир""""
    } else if (cardChoice == "2") {
        """"Visa""""
    } else if (cardChoice == "3") {
        """"Mastercard""""
    } else {
        println("Неправильный выбор!")
        return
    }

    println("Введите сумму: ")
    var amount = readLine()?.toDoubleOrNull();


    val commissionTotal = if (amount == null || amount <= 0 || amount > 150_000) {
        ("Неправильная сумма.")
    } else if (amount <= MINIMUM_RATE) {
        ("$MINIMUM_RATE руб. комиссия с $cardChoice")
    } else if (amount > MINIMUM_RATE) {
        ("${amount * STANDARD_RATE} руб. - комиссия с $cardChoice")
    } else ("Ошибка!")

    if (amount != null) {
        if ((amount * STANDARD_RATE) < MINIMUM_RATE)
            println("$MINIMUM_RATE руб. комиссия с $cardChoice")
        else println(commissionTotal)
    }
}