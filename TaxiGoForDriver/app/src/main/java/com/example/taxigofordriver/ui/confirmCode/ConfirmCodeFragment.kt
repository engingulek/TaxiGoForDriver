package com.example.taxigofordriver.ui.confirmCode

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.taxigofordriver.R
import com.example.taxigofordriver.databinding.FragmentConfirmCodeBinding
import com.example.taxigofordriver.databinding.FragmentOnboardingBinding
import com.example.taxigofordriver.utils.toFragment

class ConfirmCodeFragment : Fragment() {
    private lateinit var design : FragmentConfirmCodeBinding
    private lateinit var viewModel:ConfirmCodeViewModelInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_confirm_code, container, false)


        val bundle: ConfirmCodeFragmentArgs by navArgs()
        val phoneNumber = bundle.phone
        viewModel.getPhoneNumber(phoneNumber)
        viewModel.uiState.observe(viewLifecycleOwner) {
            design.uiState = it
        }

        design.confirmCodeTxt.addTextChangedListener { text ->
            viewModel.onAction(ConfirmCodeContract.UiAction.onChangedEditText(text.toString()))
        }

        design.confirmBttn.setOnClickListener {
            viewModel.onAction(ConfirmCodeContract.UiAction.onTappedSendCodeBttn)
            viewModel.uiState.observe(viewLifecycleOwner) {
                if(it.buttonState) {
                    val nav = ConfirmCodeFragmentDirections.toEnterDriverInfo()
                    Navigation.toFragment(requireView(),nav)
                }
            }
        }
        return design.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : ConfirmCodeViewModelInterface by viewModels<ConfirmCodeViewModel>()
        viewModel = tempViewModel
    }

}