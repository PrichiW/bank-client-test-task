package com.testtask.bankclient.ui.screen.purchases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.bankclient.domain.model.PurchaseData
import com.testtask.bankclient.domain.usecase.GetPurchasesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

data class PurchasesUiState(
    val purchaseItems: List<PurchaseDisplayItem> = emptyList()
)

@HiltViewModel
class PurchasesViewModel @Inject constructor(
    getPurchasesUseCase: GetPurchasesUseCase
) : ViewModel() {

    val uiState: StateFlow<PurchasesUiState> by lazy {
        getPurchasesUseCase()
            .map { list -> createDisplayList(list) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PurchasesUiState()
            )
    }

    private fun createDisplayList(purchases: List<PurchaseData>): PurchasesUiState {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))

        val displayList = mutableListOf<PurchaseDisplayItem>()

        purchases
            .groupBy { it.date.format(formatter) }
            .forEach { (date, purchaseDataList) ->
                displayList.add(PurchaseDisplayItem.Header(date))
                purchaseDataList.forEach { purchaseData ->
                    displayList.add(PurchaseDisplayItem.Purchase(purchaseData.name))
                }
            }

        return PurchasesUiState(displayList)
    }
}