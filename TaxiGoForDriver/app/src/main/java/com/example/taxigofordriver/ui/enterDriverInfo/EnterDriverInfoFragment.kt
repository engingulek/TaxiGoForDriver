package com.example.taxigofordriver.ui.enterDriverInfo
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.taxigofordriver.R
import com.example.taxigofordriver.databinding.FragmentEnterDriverInfoBinding
import com.example.taxigofordriver.ui.confirmCode.ConfirmCodeContract
import com.example.taxigofordriver.utils.toFragment


class EnterDriverInfoFragment : Fragment() {
    private lateinit var design: FragmentEnterDriverInfoBinding
    private lateinit var viewModel : EnterInfoDriverViewModelInterface
    private val PICK_IMAGE_REQUEST = 1
    private val PICK_FILE_REQUEST = 1

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        handleImageResult(result.resultCode, result.data)
    }
    private val pickFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        handleFileResult(result.resultCode, result.data)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_enter_driver_info, container, false)
        viewModel.errorState.observe(viewLifecycleOwner){
            design.errorState = it
        }
        selectActions()
        texFieldAction()
        return design.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : EnterInfoDriverViewModelInterface by viewModels<EnterInfoDriverViewModel>()
        viewModel = tempViewModel
    }

    private fun selectActions() {
        design.selectImageBttn.setOnClickListener {
            selectImage()
        }

        design.selectedCriminalRecordBttn.setOnClickListener{
            selectFile()

        }
    }

    private fun texFieldAction() {
        design.nameEditText.addTextChangedListener { text ->
            viewModel.onAction(EnterDriverInfoContract.UiAction.onChangedTextField(TextFieldTyepe.NAME,text.toString()))
            viewModel.errorState.observe(viewLifecycleOwner){
                it.nameError?.let { a ->
                    design.nameTextInput.error = getString(a)
                } ?: run{
                    design.nameTextInput.error = null
                }
            }
        }

        design.surnameEditText.addTextChangedListener { text ->
            viewModel.onAction(EnterDriverInfoContract.UiAction.onChangedTextField(TextFieldTyepe.SURNAME,text.toString()))
            viewModel.errorState.observe(viewLifecycleOwner){
                it.surname?.let { a ->
                    design.surnameEditText.error = getString(a)
                } ?: run{
                    design.surnameEditText.error = null
                }
            }
        }

        design.cartLiPlateEditText.addTextChangedListener { text ->
            viewModel.onAction(EnterDriverInfoContract.UiAction.onChangedTextField(TextFieldTyepe.CARPLATE,text.toString()))
        }

        design.radioSedan.setOnClickListener{
            viewModel.onAction(EnterDriverInfoContract.UiAction.onTappedTaxiType(TaxiType.SEDAN))
        }

        design.radioTrasporter.setOnClickListener{
            viewModel.onAction(EnterDriverInfoContract.UiAction.onTappedTaxiType(TaxiType.TRANSPORTER))
        }

        design.sendInfoBttn.setOnClickListener {
            viewModel.onAction(EnterDriverInfoContract.UiAction.onTappedSendInfo)
            viewModel.errorState.observe(viewLifecycleOwner) {
                if (it.sendInfoButtonState) {
                    val nav = EnterDriverInfoFragmentDirections.toHomeFragment()
                    Navigation.toFragment(requireView(),nav)
                }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        pickImageLauncher.launch(intent)
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf"))
        }
        pickFileLauncher.launch(intent)
    }

    private fun handleImageResult(resultCode: Int, data: Intent?) {
        val imageUri: Uri? = data?.data
        if (resultCode == Activity.RESULT_OK) {
            design.driverImage.setImageURI(imageUri)
            viewModel.onAction(EnterDriverInfoContract.UiAction.onTappedSelectImage(imageUri))
        } else {
            viewModel.onAction(EnterDriverInfoContract.UiAction.onTappedSelectImage(null))
        }
    }

    private fun handleFileResult(resultCode: Int, data: Intent?) {
        val fileUri: Uri? = data?.data
        Log.e("File Selection", "Seçilen Dosya URI: $fileUri")
        if (resultCode == Activity.RESULT_OK) {
            viewModel.onAction(EnterDriverInfoContract.UiAction.onTappedSelectFile(fileUri))
        } else {
            viewModel.onAction(EnterDriverInfoContract.UiAction.onTappedSelectFile(null))
        }
    }
}