package com.example.accountingservice.payment

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate

@Table
data class VetPayment (@Id val id: Long? = null,
                       @Column("payment_date") val date: LocalDate,
                       @Column("vet_id")val vetId: Long,
                       val amount: BigDecimal)