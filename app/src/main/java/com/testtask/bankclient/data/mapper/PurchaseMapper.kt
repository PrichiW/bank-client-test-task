package com.testtask.bankclient.data.mapper

import com.testtask.bankclient.domain.model.PurchaseData
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException
import javax.inject.Inject

class PurchaseMapper @Inject constructor() {

    fun mapRawToDomain(rawData: List<Map<String, Any>>): List<PurchaseData> {
        return rawData.flatMap { item ->
            val dateString = item["date"] as? String
            val date = try {
                dateString?.let { ZonedDateTime.parse(it).toLocalDate() }
            } catch (e: DateTimeParseException) {
                null
            }

            val namesAnyList = item["name"] as? List<*>
            val names = namesAnyList?.mapNotNull { it as? String }

            if (date != null && names != null) {
                names.map { name -> PurchaseData(date, name) }
            } else {
                emptyList()
            }
        }
    }
}