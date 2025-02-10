package com.example.taxigofordriver.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taxigofordriver.R
import com.example.taxigofordriver.ui.enterDriverInfo.EnterDriverInfoContract

interface HomeViewModelInterface {
    var uiState : LiveData<HomeContract.UiState>
    fun onAction(action:HomeContract.UIAction)
}

class HomeViewModel : ViewModel(),HomeViewModelInterface {
    private  var _uiState = MutableLiveData(HomeContract.UiState())
    override var uiState: LiveData<HomeContract.UiState> = _uiState
    private var driverState : TaxiDriverState = TaxiDriverState.CLOSE
    override fun onAction(action: HomeContract.UIAction) {
        when(action) {
            is HomeContract.UIAction.onTappedStateSwiftch ->onTappedStateAction(action.state)
        }
    }

    private fun  onTappedStateAction(state:Boolean) {
        driverState = if (state) TaxiDriverState.OPEN else TaxiDriverState.CLOSE
        _uiState.value = _uiState.value?.copy(
            driverState = state
        )

    }
}