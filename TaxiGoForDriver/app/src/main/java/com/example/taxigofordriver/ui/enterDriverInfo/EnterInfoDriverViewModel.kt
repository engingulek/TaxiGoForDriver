package com.example.taxigofordriver.ui.enterDriverInfo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.taxigofordriver.R
import kotlinx.coroutines.delay


interface EnterInfoDriverViewModelInterface {
    var errorState : LiveData<EnterDriverInfoContract.ErrorState>

    fun onAction(action:EnterDriverInfoContract.UiAction)
}
class EnterInfoDriverViewModel : ViewModel(),EnterInfoDriverViewModelInterface {

    private  var _errorState = MutableLiveData(EnterDriverInfoContract.ErrorState())
    override var errorState: LiveData<EnterDriverInfoContract.ErrorState> = _errorState
    //TODO will be url
    private var selectedImageUri: Uri? = null
    private  var pdfUri:Uri? = null

    private var nameText:String = ""
    private var surnameText:String = ""
    private var carPLateText:String = ""
    private var selectedTaxiType:TaxiType? = null
    override fun onAction(action: EnterDriverInfoContract.UiAction) {
        when(action) {
            is EnterDriverInfoContract.UiAction.onTappedSelectImage -> onTappedSelectImageAction(action.imageUri)
            is EnterDriverInfoContract.UiAction.onTappedSelectFile -> onTappedSelectFileAction(action.fileUri)
            is EnterDriverInfoContract.UiAction.onChangedTextField ->  onChangedTextFieldAction(action.type,action.text)
            is EnterDriverInfoContract.UiAction.onTappedTaxiType -> selectTaxiType(action.type)
            EnterDriverInfoContract.UiAction.onTappedSendInfo -> sendInfoToDatabase()
        }
    }


    private fun onChangedTextFieldAction(type:TextFieldTyepe,text:String) {
        when(type) {
            TextFieldTyepe.NAME -> nameControl(text)
            TextFieldTyepe.SURNAME -> surnameControl(text)
            TextFieldTyepe.CARPLATE -> carPLateText = text
        }
    }


    private fun updateErrorState(
        nameError: Int? = _errorState.value?.nameError,
        surnameError: Int? = _errorState.value?.surname,
        imageError: Boolean = true,
        fileError: Boolean = true,
        selectedTypeError: Boolean = true
    ) {
        _errorState.value = _errorState.value?.copy(
            nameError = nameError,
            surname = surnameError,
            imageError = imageError,
            fileError = fileError,
            selectedTypeError = selectedTypeError,
            sendInfoButtonState = selectedImageUri != null && pdfUri != null
                    && nameText.isNotEmpty() && surnameText.isNotEmpty()
                    && carPLateText.isNotEmpty() && selectedTaxiType != null
        )
    }

    private fun nameControl(text: String) {
        if (text.length < 3) {
            nameText = ""
            updateErrorState(nameError = R.string.too_short)
        } else {
            nameText = text
            updateErrorState(nameError = null)
        }
    }

    private fun surnameControl(text: String) {
        if (text.length < 2) {
            surnameText = ""
            updateErrorState(surnameError = R.string.too_short)
        } else {
            surnameText = text
            updateErrorState(surnameError = null)
        }
    }

    private fun onTappedSelectImageAction(imageUri: Uri?) {
        selectedImageUri = imageUri
        updateErrorState(imageError = imageUri == null)
        // TODO Firebase store will be added here
    }

    private fun onTappedSelectFileAction(fileUri: Uri?) {
        Log.e("pdf", "$fileUri")
        pdfUri = fileUri
        updateErrorState(fileError = fileUri == null)
        // TODO Firebase store will be added here
    }

    private fun selectTaxiType(type: TaxiType) {
        selectedTaxiType = type
        updateErrorState(selectedTypeError = false)
    }


    private  fun sendInfoToDatabase() {

    }


}