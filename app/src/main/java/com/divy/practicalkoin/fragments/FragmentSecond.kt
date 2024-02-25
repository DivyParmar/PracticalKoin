package com.divy.practicalkoin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.divy.practicalkoin.databinding.FragmentSecondBinding
import com.divy.practicalkoin.viewModels.RoomViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentSecond : Fragment() {

    lateinit var binding: FragmentSecondBinding

    private val roomViewModel: RoomViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    private fun initData() {
        binding.apply {

        }
    }
}