package com.example.taxigofordriver.ui.enterphone

import com.example.taxigofordriver.R

object EnterPhoneContract {
    data class UiState(
        var title:Int = R.string.emptyDefault,
        var textFieldHint:Int = R.string.emptyDefault,
        var buttonTitle:Int = R.string.emptyDefault,
        var errorState:Boolean = false,
        var buttonState:Boolean = false,
        var errorMessage:Int = R.string.emptyDefault
    )


    sealed interface UiAction {
        data class enterPhoneAction(var phone:String):UiAction
    }

}