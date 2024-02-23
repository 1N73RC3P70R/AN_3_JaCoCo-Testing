package homework

import org.junit.Assert.assertEquals
import org.junit.Test

class CommissionTest {

    @Test
    fun testCommissionForMir() {
        val commissionAmount = commission(transferForMonth = 0.0, transferAmount = 1000.0, cardType = 1)
        assertEquals(35.0, commissionAmount, 0.0)
    }

    @Test
    fun testCommissionForDefault() {
        val commissionAmount = commission(transferForMonth = 100.0, transferAmount = 1000.0)
        assertEquals(0.0, commissionAmount, 0.0)
    }

    @Test
    fun testCommissionForVisa() {
        val commissionAmount = commission(transferForMonth = 100.0, transferAmount = 1000.0, cardType = 3)
        assertEquals(35.0, commissionAmount, 0.0)
    }

    @Test
    fun testCommissionForMastercardMaestro() {
        val commissionAmount = commission(transferForMonth = 10000.0, transferAmount = 1000.0, cardType = 4)
        assertEquals(0.0, commissionAmount, 0.0)
    }

    @Test
    fun testCommissionForMaestro() {
        val commissionAmount = commission(transferForMonth = 2.0, transferAmount = 1000.0, cardType = 5)
        assertEquals(0.0, commissionAmount, 0.0)
    }

    @Test
    fun testInvalidAmountHandling() {
        val commissionAmount = commission(transferForMonth = 2.0, transferAmount = -1000.0, cardType = 6)
        assertEquals(0.0, commissionAmount, 0.0)
    }

    @Test
    fun testLimitCheckForValidAmount() {
        val result = checkLimits(cardType = 1, transferAmount = 1000.0, totalSpentMonth = 1000.0)
        assertEquals(true, result)
    }

    @Test
    fun testCommissionCalculationForMir() {
        val commissionAmount = calculateCommission(transferAmount = 1000.0, cardType = 1)
        assertEquals(35.0, commissionAmount, 0.0)
    }

    @Test
    fun testLimitCheckForUnknownCardType() {
        val result = checkLimits(6, 1000.0, 1000.0)
        assertEquals(false, result)
    }

    @Test
    fun testLimitCheckWhenDailyLimitExceeded() {
        val result = checkLimits(1, 200_000.0, 200_000.0)
        assertEquals(false, result)
    }

    @Test
    fun testLimitCheckWhenMonthlyLimitExceeded() {
        val result = checkLimits(3, 10_000.0, 600_000.0)
        assertEquals(true, result)
    }

    @Test
    fun testCommissionCalculationForVKPay() {
        val result = calculateCommission(1000.0, 2)
        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun testCommissionCalculationForMirAgain() {
        val result = calculateCommission(1000.0, 1)
        assertEquals(35.0, result, 0.0)
    }

    @Test
    fun testMastercardMaestroCommissionForAmountWithinLimit() {
        val result = commissionMastercardMaestro(70_000.0)
        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun testMastercardMaestroCommissionForAmountExceedingLimit() {
        val result = commissionMastercardMaestro(80_000.0)
        assertEquals((80_000.0 * STANDARD_RATE_MASTERCARD_MAESTRO_PERCENT) + STANDARD_RATE_MASTERCARD_MAESTRO, result, 0.0)
    }
}