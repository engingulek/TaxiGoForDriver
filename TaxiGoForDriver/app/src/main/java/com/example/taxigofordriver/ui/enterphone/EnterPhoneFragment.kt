package com.example.taxigofordriver.ui.enterphone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.taxigofordriver.R
import com.example.taxigofordriver.databinding.FragmentEnterPhoneBinding
import com.example.taxigofordriver.ui.onboarding.OnboardingContract
import com.example.taxigofordriver.utils.toFragment


class EnterPhoneFragment : Fragment() {
    private lateinit var design:FragmentEnterPhoneBinding
    private lateinit var viewModel:EnterPhoneViewModelInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_enter_phone, container, false)
        viewModel.uiState.observe(viewLifecycleOwner) {
            design.uiState  = it
        }


        design.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                viewModel.onAction(EnterPhoneContract.UiAction.enterPhoneAction(text))
            }
        })


        design.countiuneBttn.setOnClickListener {
           val nav = EnterPhoneFragmentDirections.toConfirmCode(viewModel.phoneNumber)
            Navigation.toFragment(requireView(),nav)
        }


        return  design.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:EnterPhoneViewModelInterface by viewModels<EnterPhoneViewModel>()
        viewModel = tempViewModel
    }
}