import homework.commission

fun main() {
    commission(transferForMonth = 0.0, transferAmount = 1000.0, cardType = 1)
    commission(transferForMonth = 100.00, transferAmount = 1000.0)
    commission(transferForMonth = 100.00, transferAmount = 1000.00, cardType = 3)
    commission(transferForMonth = 10000.00, transferAmount = 1000.00, cardType = 4)
    commission(transferForMonth = 2.00, transferAmount = 1000.00, cardType = 5)
    commission(transferForMonth = 2.00, transferAmount = 1000.00, cardType = 6)
}

