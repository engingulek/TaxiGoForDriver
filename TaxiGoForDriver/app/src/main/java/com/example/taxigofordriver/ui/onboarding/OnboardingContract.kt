package com.example.taxigofordriver.ui.onboarding

import com.example.taxigofordriver.R

object OnboardingContract {

    data class  UiState (
        var banner:Int = R.string.emptyDefault,
        var subanner : Int = R.string.emptyDefault,
        var startButton : Int = R.string.emptyDefault,
        var onboardingImage:ImageResource = ImageResource(
            R.drawable.ic_launcher_background,
            R.string.emptyDefault)
    )


    sealed interface UiAction {
        data object onClickedStartBttn:UiAction
    }

    class ImageResource(
        var image:Int,
        var imageDescription:Int
    )

}