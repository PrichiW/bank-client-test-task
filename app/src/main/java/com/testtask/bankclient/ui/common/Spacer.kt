package com.testtask.bankclient.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SpacerSmall() = Spacer(Modifier.height(12.dp))

@Composable
fun SpacerLarge() = Spacer(Modifier.height(24.dp))

@Composable
fun SpacerWidth(width: Dp) = Spacer(Modifier.width(width))