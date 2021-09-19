package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail

data class CoinDetailViewState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    var error: String = ""
)
