package com.divy.practicalkoin.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.divy.practicalkoin.adapters.QuestionAdapter
import com.divy.practicalkoin.databinding.ActivityMainBinding
import com.divy.practicalkoin.utility.setSafeOnClickListener
import com.divy.practicalkoin.viewModels.RoomViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val roomViewModel: RoomViewModel by viewModel()

    private lateinit var adapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
    }

    private fun initData() {
        initClickListener()
        binding.apply {
            adapter = QuestionAdapter()
            rvQuestions.adapter = adapter
        }
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.ivAdd.setSafeOnClickListener {
                adapter.addItem("Name")
            }
            toolbar.ivBack.setSafeOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnSubmit.setSafeOnClickListener {

            }
        }
    }
}