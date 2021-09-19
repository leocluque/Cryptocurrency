package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val useCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinDetailViewState())
    val state: State<CoinDetailViewState> = _state

    init {
        savedStateHandle.get<String>(Constants.EXTRA_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinId: String) {
        useCase(coinId).onEach { result ->
            run {
                when (result) {
                    is Resource.Success -> {
                        _state.value =
                            CoinDetailViewState(coin = result.data)
                    }
                    is Resource.Error -> {
                        _state.value =
                            CoinDetailViewState(error = result.message ?: "unexpected error")
                    }
                    is Resource.Loading -> {
                        _state.value = CoinDetailViewState(isLoading = true)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}