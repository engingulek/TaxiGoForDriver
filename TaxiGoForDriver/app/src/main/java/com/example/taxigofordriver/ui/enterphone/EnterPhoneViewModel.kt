package com.example.taxigofordriver.ui.enterphone

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taxigofordriver.R

interface EnterPhoneViewModelInterface {
    var uiState : LiveData<EnterPhoneContract.UiState>

    fun onAction(action:EnterPhoneContract.UiAction)
}

class EnterPhoneViewModel : ViewModel(),EnterPhoneViewModelInterface {
    private var _uiState = MutableLiveData(EnterPhoneContract.UiState())
    override var uiState : LiveData<EnterPhoneContract.UiState> = _uiState

    init {
        setUiState()
    }
    private fun setUiState() {
        _uiState.value = _uiState.value?.copy(
            title = R.string.enter_phone,
            textFieldHint = R.string.phone,
            buttonTitle = R.string.coutiune,
            errorState = false,
            buttonState = false,
            errorMessage = R.string.emptyDefault

        )
    }


    override fun onAction(action:EnterPhoneContract.UiAction){
      when(action) {
          is EnterPhoneContract.UiAction.enterPhoneAction -> enterPhoneAction(action.phone)
      }
    }

    private  fun enterPhoneAction(phone:String) {
        val isPhoneValid = phone.isNotEmpty() && phone.first() != '0' && phone.length == 10 && phone.all { it.isDigit() }

        _uiState.value = _uiState.value?.copy(
            errorMessage = when {
                phone.isEmpty() -> R.string.emptyDefault
                phone.first() == '0' -> R.string.error_zero
                phone.length < 10 -> R.string.emptyDefault
                phone.any { !it.isDigit() } -> R.string.digit_error
                else -> R.string.emptyDefault
            },
            errorState = !isPhoneValid,
            buttonState = isPhoneValid
        )

    }
}