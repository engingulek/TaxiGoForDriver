package com.example.taxigofordriver.ui.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taxigofordriver.R

interface OnboardingViewModelInterface {
    var uiState : LiveData<OnboardingContract.UiState>

    fun onAction(action:OnboardingContract.UiAction)
}


class OnboardingViewModel : ViewModel(),OnboardingViewModelInterface {
    private var _uiState = MutableLiveData(OnboardingContract.UiState())
    override var uiState : LiveData<OnboardingContract.UiState> = _uiState

    init {
        setUiState()
    }


    private  fun setUiState() {
        _uiState.value = _uiState.value?.copy(
            banner = R.string.banner,
            subanner = R.string.subbanner,
            startButton = R.string.start,
            onboardingImage = OnboardingContract.ImageResource(
                R.drawable.taxi_driver,
                R.string.image_onboarding
            )
        )
    }


    override fun onAction(action: OnboardingContract.UiAction) {
        when(action) {
            OnboardingContract.UiAction.onClickedStartBttn -> startBttnOnClicked()
        }
    }

    private fun startBttnOnClicked() {
        Log.e("clicked","clicked")
    }
}