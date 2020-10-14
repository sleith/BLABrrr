package com.fatdino.blabrrr.ui.post

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.configs.Configs
import com.fatdino.blabrrr.databinding.ActivityPostBinding
import com.fatdino.blabrrr.injection.component.AppComponent
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_post.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.io.File

class PostActivity : BaseActivity() {

    lateinit var viewModel: PostActivityViewModel
    lateinit var easyImage: EasyImage

    val CAMERA_PERMISSION = 232

    override fun setupViews() {
        viewModel = mViewModel as PostActivityViewModel
        val binding: ActivityPostBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_post)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        ibImage.setOnClickListener {
            if (checkCameraPermission()) {
                showImagePicker()
            }
        }

        btnPost.setOnClickListener {
            viewModel.doPost()
        }
    }

    override fun setupObservers() {
        viewModel.callbackPost.observe(this, {
            it?.let { _ ->
                Toast.makeText(this, getString(R.string.post_success), Toast.LENGTH_LONG).show()
                setResult(RESULT_OK)
                finish()
            }
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(PostActivityViewModel::class.java)
    }

    override fun injectAppComponent(appComponent: AppComponent) {
        appComponent.inject(viewModel)
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
                        .start(this@PostActivity)
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