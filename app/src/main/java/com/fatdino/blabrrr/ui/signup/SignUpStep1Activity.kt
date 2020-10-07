package com.fatdino.blabrrr.ui.signup

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.configs.Configs
import com.fatdino.blabrrr.databinding.ActivitySignupStep1Binding
import com.fatdino.blabrrr.injection.component.DaggerViewModelComponent
import com.fatdino.blabrrr.injection.module.ServiceModule
import com.fatdino.blabrrr.injection.module.StorageModule
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_signup_step1.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.io.File


class SignUpStep1Activity : BaseActivity() {

    lateinit var viewModel: SignUpStep1ViewModel
    lateinit var easyImage: EasyImage

    val CAMERA_PERMISSION = 232

    override fun setupViews() {
        viewModel = mViewModel as SignUpStep1ViewModel
        val binding: ActivitySignupStep1Binding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup_step1)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val injector =
            DaggerViewModelComponent.builder().serviceModule(ServiceModule()).storageModule(
                StorageModule(this)
            ).build()
        injector.inject(viewModel)

        ibBack.setOnClickListener {
            finish()
        }

        btnContinue.setOnClickListener {
            viewModel.checkUsernameAvailability()
        }

        llAddPicture.setOnClickListener {
            if (checkCameraPermission()) {
                showImagePicker()
            }
        }
    }

    override fun setupObservers() {
        viewModel.callbackIsUsernameAvailable.observe(this, {
            if (it) {
                viewModel.username.value?.let { username ->
                    startActivity(
                        SignUpStep2Activity.generateIntent(
                            this,
                            username,
                            viewModel.imageFile.value
                        )
                    )
                }
            } else {
                showSimpleDialog(null, getString(R.string.sus1_err_username_exists))
            }
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(SignUpStep1ViewModel::class.java)
    }

    fun showImagePicker() {
        easyImage = EasyImage.Builder(this).build()
        easyImage.openChooser(this)
    }

    fun checkCameraPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA
                    ),
                    CAMERA_PERMISSION
                )
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                showImagePicker()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                result.uri?.let {
                    it.path?.let { path ->
                        val file = File(path)
                        viewModel.imageFile.value = file
                    }
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }

        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    var mediaFile = imageFiles[0]
                    val file = mediaFile.file

                    CropImage.activity(Uri.fromFile(file))
                        .setRequestedSize(Configs.MAX_IMAGE_SIZE, Configs.MAX_IMAGE_SIZE)
                        .start(this@SignUpStep1Activity)
                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    //Some error handling
                    error.printStackTrace()
                }

                override fun onCanceled(source: MediaSource) {
                    //Not necessary to remove any files manually anymore
                }
            })
    }
}