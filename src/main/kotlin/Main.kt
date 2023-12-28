fun main() {

    println("Выберите ДЗ: ")
    val input = readlnOrNull()?.toInt()
    when {
        input == 1 -> agoToText()
        input == 2 -> commission()
        else -> println("Неправильный ввод.")
    }
}

