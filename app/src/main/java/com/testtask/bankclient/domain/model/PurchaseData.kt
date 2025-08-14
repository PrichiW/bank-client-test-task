package com.testtask.bankclient.domain.model

import java.time.LocalDate

data class PurchaseData(
    val date: LocalDate,
    val name: String
)