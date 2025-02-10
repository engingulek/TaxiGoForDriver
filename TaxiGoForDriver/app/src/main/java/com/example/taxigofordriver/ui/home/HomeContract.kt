package com.example.taxigofordriver.ui.home

import com.example.taxigofordriver.R

enum class TaxiDriverState {
    OPEN,CLOSE
}

object HomeContract {

    data class UiState (
        var driverState:Boolean = false,
        var workState:Boolean = false

    )


    sealed interface UIAction {
        data class onTappedStateSwiftch(var state:Boolean): UIAction
        data object onDestroy:UIAction
    }

}