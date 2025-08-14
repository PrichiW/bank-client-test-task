package com.testtask.bankclient.ui.screen.purchases

import java.util.UUID

sealed interface PurchaseDisplayItem {
    data class Header(val date: String) : PurchaseDisplayItem
    data class Purchase(val name: String, val id: String = UUID.randomUUID().toString()) : PurchaseDisplayItem
}