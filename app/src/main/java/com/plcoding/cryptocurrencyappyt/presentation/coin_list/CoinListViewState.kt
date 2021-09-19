package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import com.plcoding.cryptocurrencyappyt.domain.model.Coin

data class CoinListViewState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    var error: String = ""
)
