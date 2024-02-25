package com.divy.practicalkoin.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.divy.practicalkoin.databinding.ActivityPickMediaBinding
import com.divy.practicalkoin.dialogs.SelectCameraGalleryDialog
import com.divy.practicalkoin.utility.cropImage
import com.divy.practicalkoin.utility.selectImageFromCamera
import com.divy.practicalkoin.utility.selectImageFromGallery
import com.divy.practicalkoin.utility.setSafeOnClickListener
import com.bumptech.glide.Glide
import com.yalantis.ucrop.UCrop
import java.io.File


class PickMediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickMediaBinding
    private lateinit var currentPhotoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            btnPickImage.setSafeOnClickListener {
                SelectCameraGalleryDialog.showDialog(supportFragmentManager) { selected ->
                    if (selected.equals("camera",true)){
                        selectImageFromCamera(cameraImageLauncher) {
                            currentPhotoFile = it
                        }
                    }else{
                        selectImageFromGallery(galleryImageLauncher)
                    }
                }
            }
        }
    }

    private var cropLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    // Handle the cropped image URI here
                    Glide.with(this).load(resultUri).into(binding.image)
                } else {
                    // Handle the error
                    val cropError = UCrop.getError(result.data!!)
                }
            }
        }

    private val cameraImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri = result.data?.data
                if (selectedImageUri == null) {
                    // Image was captured using the camera
                    cropImage(Uri.fromFile(currentPhotoFile), cropLauncher)
                }
            }
        }

    private val galleryImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                cropImage(uri, cropLauncher)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
}