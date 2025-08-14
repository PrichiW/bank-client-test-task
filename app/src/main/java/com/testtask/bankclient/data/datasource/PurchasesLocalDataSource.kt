package com.testtask.bankclient.data.datasource

import javax.inject.Inject

class PurchasesLocalDataSource @Inject constructor() {
    fun getRawPurchases(): List<Map<String, Any>> {
        return listOf(
            mapOf(
                "date" to "2022-09-10T21:55:33Z",
                "name" to listOf("123", "321")
            ),
            mapOf(
                "date" to "2022-09-10T21:50:33Z",
                "name" to listOf("1234", "4321")
            ),
            mapOf(
                "date" to "2022-09-08T01:55:33Z",
                "name" to listOf("12345", "54321")
            ),
            mapOf(
                "date" to "2022-09-07T21:55:33Z",
                "name" to listOf("123456", "65431")
            ),
            mapOf(
                "date" to "2022-09-07T11:55:33Z",
                "name" to listOf("1234567", "7654321")
            )
        )
    }
}