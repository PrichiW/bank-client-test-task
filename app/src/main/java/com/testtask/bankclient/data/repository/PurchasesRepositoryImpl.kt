package com.testtask.bankclient.data.repository

import com.testtask.bankclient.data.datasource.PurchasesLocalDataSource
import com.testtask.bankclient.data.mapper.PurchaseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.testtask.bankclient.domain.model.PurchaseData
import com.testtask.bankclient.domain.repository.PurchasesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PurchasesRepositoryImpl @Inject constructor(
    private val dataSource: PurchasesLocalDataSource,
    private val mapper: PurchaseMapper
) : PurchasesRepository {

    override fun getPurchases(): Flow<List<PurchaseData>> = flow {
        val rawData = dataSource.getRawPurchases()
        val domainData = mapper.mapRawToDomain(rawData)
        emit(domainData)
    }.flowOn(Dispatchers.IO)
}