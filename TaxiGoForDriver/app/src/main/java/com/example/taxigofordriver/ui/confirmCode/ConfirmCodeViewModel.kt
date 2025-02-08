package com.example.taxigofordriver.ui.confirmCode

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taxigofordriver.R

import java.util.Locale


interface  ConfirmCodeViewModelInterface {
    var uiState : LiveData<ConfirmCodeContract.UiState>

    fun getPhoneNumber(phone:String)
    fun onAction(action: ConfirmCodeContract.UiAction)

}

class ConfirmCodeViewModel : ViewModel(),ConfirmCodeViewModelInterface {
    private var _uiState = MutableLiveData(ConfirmCodeContract.UiState())
    override var uiState : LiveData<ConfirmCodeContract.UiState> = _uiState
    private var code :String = ""


    init {
        setUiState()
    }

    private fun setUiState()
    {
        _uiState.value = _uiState.value?.copy(
            title = R.string.confirmTitle,
            buttonTitle = R.string.send_code,
        )
    }

    override fun getPhoneNumber(phone: String) {
        val langSubTitle = if (Locale.getDefault().toString() == "tr_TR")
            "Girilen Telefon Numarası $phone"
        else
            "Entered Phone Number $phone"
        _uiState.value = _uiState.value?.copy(
            subTitle = langSubTitle
        )
    }

    override fun onAction(action: ConfirmCodeContract.UiAction) {
        when(action) {
            is ConfirmCodeContract.UiAction.onTappedSendCodeBttn -> onTappedSendCodeBttn()
            is ConfirmCodeContract.UiAction.onChangedEditText -> onChangedEditTextAction(action.code)
        }
    }

    private fun onChangedEditTextAction(text:String) {
        code = text
    }

    private  fun onTappedSendCodeBttn() {
        _uiState.value = _uiState.value?.copy(
            errorState = if (code == "214434") Pair(false, R.string.emptyDefault)
            else Pair(true, R.string.code_error),
            buttonState = code == "214434"
        )
    }
}