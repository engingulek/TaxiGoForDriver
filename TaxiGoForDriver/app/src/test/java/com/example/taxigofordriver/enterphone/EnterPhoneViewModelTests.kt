package com.example.taxigofordriver.enterphone

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taxigofordriver.CoroutineTestRule
import com.example.taxigofordriver.R
import com.example.taxigofordriver.getOrAwaitValue
import com.example.taxigofordriver.ui.enterphone.EnterPhoneContract
import com.example.taxigofordriver.ui.enterphone.EnterPhoneViewModel
import com.example.taxigofordriver.ui.enterphone.EnterPhoneViewModelInterface
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
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
class EnterPhoneViewModelTests {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel:EnterPhoneViewModelInterface

    @get:Rule
    var coroutineRule = CoroutineTestRule()
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = EnterPhoneViewModel()
    }


    @After
    fun tearDown() {
    }


    @Test
    fun `when open enterPhone screen ,return uiState not error `()  {
        val uiState = viewModel.uiState.getOrAwaitValue()

        // Test assertion
        assertEquals(
            "The title is not correct. ",
            R.string.enter_phone,
            uiState.title)


        assertEquals(
            "The buttonTitle is not correct. ",
            R.string.coutiune,
            uiState.buttonTitle)

        assertFalse("errorState is not correct",uiState.errorState)
        assertFalse("buttonState is not correct",uiState.buttonState)

        assertEquals(
            "The errorMessage is not correct. ",
            R.string.emptyDefault,
            uiState.errorMessage)

    }


    @Test
    fun `when  phone first zero,return error message `()  {

        viewModel.onAction(EnterPhoneContract.UiAction.enterPhoneAction("05"))
        // Test assertion
        val uiState = viewModel.uiState.getOrAwaitValue()

        assertTrue("errorState is not correct",uiState.errorState)
        assertFalse("buttonState is not correct",uiState.buttonState)

        assertEquals(
            "The errorMessage is not correct. ",
            R.string.error_zero,
            uiState.errorMessage)
    }

    @Test
    fun `when  phone  contains string,return error message `()  {

        viewModel.onAction(EnterPhoneContract.UiAction.enterPhoneAction("5f"))
        // Test assertion
        val uiState = viewModel.uiState.getOrAwaitValue()

        assertTrue("errorState is not correct",uiState.errorState)
        assertFalse("buttonState is not correct",uiState.buttonState)

        assertEquals(
            "The errorMessage is not correct. ",
            R.string.digit_error,
            uiState.errorMessage)
    }


    @Test
    fun `when  phone   string,return not error `()  {

        viewModel.onAction(EnterPhoneContract.UiAction.enterPhoneAction("5345658496"))
        // Test assertion
        val uiState = viewModel.uiState.getOrAwaitValue()

        assertFalse("errorState is not correct",uiState.errorState)
        assertTrue("buttonState is not correct",uiState.buttonState)

        assertEquals(
            "The errorMessage is not correct. ",
            R.string.emptyDefault,
            uiState.errorMessage)
    }
}