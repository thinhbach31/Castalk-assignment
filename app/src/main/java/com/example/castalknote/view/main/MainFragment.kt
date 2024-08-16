package com.example.castalknote.view.main

import androidx.fragment.app.viewModels
import com.example.castalknote.base.BaseFragment
import com.example.castalknote.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()
    override fun observeData() {
    }

    override fun requestData() {
    }

    override fun initUIComponents() {
    }
}
