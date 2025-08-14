package com.testtask.bankclient.ui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class FourDigitCardTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = text.text
            .take(16)
            .chunked(4)
            .joinToString(" ")

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val spacesBeforeOffset = (offset - 1).coerceAtLeast(0) / 4
                return offset + spacesBeforeOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                val spacesBeforeOffset = (offset - 1).coerceAtLeast(0) / 5
                return offset - spacesBeforeOffset
            }
        }

        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
}