package com.example.accountingservice.work

import com.example.accountingservice.payment.VetPayment
import com.example.accountingservice.payment.VetPaymentRepository
import com.example.accountingservice.vet.VetSalaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@Service
@Transactional
class VetWorkService(
    private val vetPaymentRepository: VetPaymentRepository,
    private val vetSalaryRepository: VetSalaryRepository
) {
    fun addWorkInfo(workInfo: VetWorkInfo) {
        // Payments are stored by months: normalize to first day of month
        val monthDate = workInfo.date.withDayOfMonth(1)

        val salary = vetSalaryRepository.findByVetId(workInfo.vetId)
            ?: throw IllegalArgumentException("Salary not found for vetId=${workInfo.vetId}")

        val increment = salary.salary.multiply(BigDecimal.valueOf(workInfo.workHours.toLong()))

        val existing = vetPaymentRepository.findByVetIdAndDate(workInfo.vetId, monthDate)
        if (existing != null) {
            val updated = existing.copy(amount = existing.amount.add(increment))
            vetPaymentRepository.save(updated)
        } else {
            // If no monthly payment exists, create a new one with calculated amount
            val newPayment = VetPayment(
                date = monthDate,
                vetId = workInfo.vetId,
                amount = increment
            )
            vetPaymentRepository.save(newPayment)
        }
    }

    @Transactional(readOnly = true)
    fun getVetPayment(vetId: Long, salaryDate: LocalDate): VetPayment {
        val monthDate = salaryDate.withDayOfMonth(1)
        return vetPaymentRepository.findByVetIdAndDate(vetId, monthDate)
            ?: throw NoSuchElementException("Payment not found for vetId=$vetId and month=$monthDate")
    }

    @Transactional(readOnly = true)
    fun getVetPayments(date: LocalDate): List<VetPayment> {
        return vetPaymentRepository.findVetPaymentByDate(date.withDayOfMonth(1))
    }

}
