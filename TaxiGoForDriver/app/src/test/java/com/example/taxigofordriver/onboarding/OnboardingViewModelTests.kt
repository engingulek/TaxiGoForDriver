package com.example.taxigofordriver.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taxigofordriver.CoroutineTestRule
import com.example.taxigofordriver.R
import com.example.taxigofordriver.getOrAwaitValue
import com.example.taxigofordriver.ui.onboarding.OnboardingViewModel
import com.example.taxigofordriver.ui.onboarding.OnboardingViewModelInterface
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OnboardingViewModelTests {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel:OnboardingViewModelInterface

    @get:Rule
    var coroutineRule = CoroutineTestRule()
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = OnboardingViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when open onboarding screen ,return uiState not error `()  {
        val uiState = viewModel.uiState.getOrAwaitValue()


        // Test assertion
        assertEquals(
            "The title is not correct. ",
            R.string.banner,
            uiState.banner)
    }
}