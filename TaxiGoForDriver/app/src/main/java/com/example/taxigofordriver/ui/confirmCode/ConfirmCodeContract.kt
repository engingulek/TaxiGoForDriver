package com.example.taxigofordriver.ui.confirmCode

import com.example.taxigofordriver.R

object ConfirmCodeContract {

    data class UiState (
        var title:Int = R.string.emptyDefault,
        var subTitle:String ="",
        var buttonTitle:Int = R.string.emptyDefault,
        var errorState:Pair<Boolean,Int> = Pair(false,R.string.emptyDefault),
        var buttonState:Boolean = false

    )


    sealed interface UiAction {
        data object onTappedSendCodeBttn:UiAction
        data class onChangedEditText( var code:String):UiAction
    }

}