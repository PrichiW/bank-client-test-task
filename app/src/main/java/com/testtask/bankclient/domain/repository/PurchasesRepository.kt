package com.testtask.bankclient.domain.repository

import com.testtask.bankclient.domain.model.PurchaseData
import kotlinx.coroutines.flow.Flow

interface PurchasesRepository {
    fun getPurchases(): Flow<List<PurchaseData>>
}