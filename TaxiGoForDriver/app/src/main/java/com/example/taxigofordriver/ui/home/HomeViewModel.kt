package com.example.taxigofordriver.ui.home

import android.os.Handler
import android.os.Looper
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
    private val handler = Handler(Looper.getMainLooper())

    init {
        getWork()
    }

    override fun onAction(action: HomeContract.UIAction) {
        when(action) {
            is HomeContract.UIAction.onTappedStateSwiftch ->onTappedStateAction(action.state)
            HomeContract.UIAction.onDestroy -> onDestroy()
        }
    }

    private fun  onTappedStateAction(state:Boolean) {
        driverState = if (state) TaxiDriverState.OPEN else TaxiDriverState.CLOSE
        _uiState.value = _uiState.value?.copy(
            driverState = state
        )
    }

   private fun getWork(){
       handler.postDelayed({
           _uiState.value = _uiState.value?.copy(
               workState = true
           )
           Log.e("test","test")
       }, 10_000)

   }
    private fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
    }
}