package com.example.taxigofordriver.ui.enterDriverInfo


import android.net.Uri
enum class TextFieldTyepe {
    NAME, SURNAME, CARPLATE
}

enum class  TaxiType {
    SEDAN {
        override fun toString() = "Sedan"
    },
    TRANSPORTER {
        override fun toString() = "Transporter"
    }
}

object EnterDriverInfoContract {

    data class ErrorState(
        var imageError:Boolean = true,
        var fileError:Boolean = true,
        var nameError:Int? = null,
        var surname:Int? = null,
        var carPlate:Int? = null,
        var selectedTypeError:Boolean = true,
        var sendInfoButtonState :Boolean = true
    )

    sealed interface UiAction {
       // data object onTappedSendCodeBttn:UiAction
        data class onTappedSelectImage(var imageUri:Uri?):UiAction
        data class onTappedSelectFile(var fileUri:Uri?):UiAction
        data class onChangedTextField(var type:TextFieldTyepe,var text:String):UiAction
        data class onTappedTaxiType(var type:TaxiType):UiAction
        data object onTappedSendInfo:UiAction


    }
}