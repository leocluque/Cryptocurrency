package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val useCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CoinListViewState())
    val state: State<CoinListViewState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        useCase().onEach { result ->
            run {
                when (result) {
                    is Resource.Success -> {
                        _state.value =
                            CoinListViewState(coins = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _state.value =
                            CoinListViewState(error = result.message ?: "unexpected error")
                    }
                    is Resource.Loading -> {
                        _state.value = CoinListViewState(isLoading = true)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}