package com.testtask.bankclient.domain.usecase

import com.testtask.bankclient.domain.repository.PurchasesRepository

class GetPurchasesUseCase(private val repository: PurchasesRepository) {
    operator fun invoke() = repository.getPurchases()
}