package com.divy.practicalkoin.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.divy.practicalkoin.databinding.SelectCameraGalleryDialogBinding
import com.divy.practicalkoin.utility.setSafeOnClickListener

class SelectCameraGalleryDialog : BaseBottomSheetDialogFragment() {
    override fun isTitleAvailable() = false

    private lateinit var binding: SelectCameraGalleryDialogBinding

    private lateinit var onSelect: (type: String) -> Unit

    companion object {
        fun showDialog(
            fragmentManager: FragmentManager,
            onSelect: (type: String) -> Unit
        ) {
            val bottomSheet = SelectCameraGalleryDialog()
            bottomSheet.onSelect = onSelect
            bottomSheet.show(fragmentManager, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SelectCameraGalleryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        binding.apply {
            cardCamera.setSafeOnClickListener {
                dismiss()
                onSelect.invoke("camera")
            }

            cardGallery.setSafeOnClickListener {
                dismiss()
                onSelect.invoke("gallery")
            }
        }
    }
}
