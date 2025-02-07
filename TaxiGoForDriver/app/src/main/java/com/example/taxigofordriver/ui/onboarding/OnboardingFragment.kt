package com.example.taxigofordriver.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.taxigofordriver.R
import com.example.taxigofordriver.databinding.FragmentOnboardingBinding
import androidx.fragment.app.viewModels


class OnboardingFragment : Fragment() {
    private lateinit var design:FragmentOnboardingBinding
    private lateinit var viewModel:OnboardingViewModelInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_onboarding, container, false)

        viewModel.uiState.observe(viewLifecycleOwner) {
            design.uiState = it
        }
        uiActions()
        return  design.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : OnboardingViewModelInterface by viewModels<OnboardingViewModel>()
        viewModel = tempViewModel
    }


    private fun uiActions(){
        design.startBttn.setOnClickListener {
            viewModel.onAction(OnboardingContract.UiAction.onClickedStartBttn)
        }
    }




}